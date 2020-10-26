package com.zhengyuesoftware.linphone.kotlin.window.base.viewmodel.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModelProvider
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView
import com.zhengyuesoftware.linphone.R
import com.zhengyuesoftware.linphone.kotlin.data.even_bean.Event
import com.zhengyuesoftware.linphone.kotlin.utils.res_api.ResUtil
import com.zhengyuesoftware.linphone.kotlin.window.base.fragment.BaseFragment
import com.zhengyuesoftware.linphone.kotlin.window.base.viewmodel.BaseViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.TimeUnit

/**
 * 创建日期：2020/10/26 on 15:54
 * github地址:https://github.com/13570524658
 * 作者: Administrator
 * 描述:
 */
abstract class  BaseViewModelFragment<VM : BaseViewModel> : BaseFragment()  {
    private val TAG = BaseViewModelFragment::class.java.simpleName
    private val fragmentName = javaClass.simpleName
    protected lateinit var mViewModel:VM
    private var mloadingPopup: LoadingPopupView? =null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initVM()
        initView()
        initData()
        initOnClick()
        startObserve()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initVM() {
        providerVMClass()?.let {
            mViewModel = ViewModelProvider(this).get(it)
            lifecycle.addObserver(mViewModel)
        }
    }

    open fun providerVMClass(): Class<VM>? = null
    open fun startObserve() {}

    /**
     * 必须实现的方法
     */
    abstract fun getLayoutId():Int

    abstract fun initView()

    abstract fun initData()

    abstract fun initOnClick()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        if (this::mViewModel.isInitialized)
            lifecycle.removeObserver(mViewModel)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
    }
    fun showLoadingPopup(): LoadingPopupView {
        mloadingPopup = XPopup.Builder(requireActivity())
            .hasBlurBg(true) // 是否有高斯模糊的背景，默认为false
            .isDestroyOnDismiss(false)//是否在消失的时候销毁资源，默认false。如果你的弹窗对象只使用一次，非常推荐设置这个，可以杜绝内存泄漏。如果会使用多次，千万不要设置
            .asLoading(ResUtil().resIdFindString(requireActivity(), R.string.loding))
            .show()
                as LoadingPopupView
        return mloadingPopup as LoadingPopupView
    }

    fun dissmisLoadingPopup(){
        showLoadingPopup().dismiss()
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onNomalEvent(event: Event<*>?) {
        event?.let { receiveEvent(it) }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    open fun onStickyEvent(event: Event<*>?) {
        event?.let { receiveStickyEvent(it) }
    }

    /**
     * 接收到分发到事件
     *
     * @param event 事件
     */
    protected open fun receiveEvent(event: Event<*>?) {}

    /**
     * 接受到分发的粘性事件
     *
     * @param event 粘性事件
     */
    protected open fun receiveStickyEvent(event: Event<*>?) {}

    //kotlin防抖
    @JvmOverloads
    fun View.setOnShakeProofClickListener(windowDuration: Long = 1000,
                                          unit: TimeUnit = TimeUnit.MILLISECONDS,
                                          listener: (view: View) -> Unit): Disposable {
        return Observable.create(ObservableOnSubscribe<View> { emitter ->
            setOnClickListener {
                if (!emitter.isDisposed) {
                    emitter.onNext(it)
                }
            }
        }).throttleFirst(windowDuration, unit)
            .subscribe { listener(it) }
    }

    val autoDisposable by lazy {
        AutoDisposable().apply { bind(lifecycle) }
    }

    fun Disposable.addTo(autoDisposable: AutoDisposable) {
        autoDisposable.add(this)
    }

    class AutoDisposable : LifecycleObserver {
        lateinit var compositeDisposable: CompositeDisposable

        fun bind(lifecycle: Lifecycle) {
            lifecycle.addObserver(this)
            compositeDisposable = CompositeDisposable()
        }

        fun add(disposable: Disposable) {
            if (::compositeDisposable.isInitialized) {
                compositeDisposable.add(disposable)
            } else {
                throw NotImplementedError("must bind AutoDisposable to a Lifecycle first")
            }
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            compositeDisposable.clear()
        }
    }
}