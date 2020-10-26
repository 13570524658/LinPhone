package com.zhengyuesoftware.linphone.kotlin.window.base.viewmodel.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModelProvider
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView
import com.zhengyuesoftware.linphone.R
import com.zhengyuesoftware.linphone.kotlin.net.http.repository.base.BaseRepository
import com.zhengyuesoftware.linphone.kotlin.utils.res_api.ResUtil
import com.zhengyuesoftware.linphone.kotlin.window.base.activity.BaseActivity
import com.zhengyuesoftware.linphone.kotlin.window.base.viewmodel.BaseViewModel
import es.dmoral.toasty.Toasty
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.TimeoutCancellationException
import java.lang.Exception
import java.util.concurrent.TimeUnit

/**
 * 创建日期：2020/10/26 on 15:53
 * github地址:https://github.com/13570524658
 * 作者: Administrator
 * 描述:
 */
abstract class BaseViewModelActivity<VM : BaseViewModel> : BaseActivity() {
    private val TAG = BaseViewModelActivity::class.java.simpleName
    private lateinit var mViewModel: VM
    private var mloadingPopup: LoadingPopupView? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        initVM()
        super.onCreate(savedInstanceState)
        startObserve()
    }

    private fun initVM() {
        providerVMClass()?.let {
            mViewModel = ViewModelProvider(this).get(it)
            lifecycle.addObserver(mViewModel)
        }
    }

    open fun providerVMClass(): Class<VM>? = null

    private fun startObserve() {
        //处理一些通用异常，比如网络超时等
        mViewModel.run {
            getError().observe(this@BaseViewModelActivity, Observer {
                requestError(it)
            })
            getFinally().observe(this@BaseViewModelActivity, Observer {
                requestFinally(it)
            })
        }
    }

    open fun requestFinally(it: Int?) {

    }

    open fun requestError(it: Exception?) {
        //处理一些已知异常
        it?.run {
            when (it) {
                is TimeoutCancellationException -> {
                    Toasty.info(this@BaseViewModelActivity,"请求超时").show()
                }
                is BaseRepository.TokenInvalidException -> {
                    Toasty.info(this@BaseViewModelActivity,"登录失效").show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(mViewModel)
    }

    fun showLoadingPopup(): LoadingPopupView {
        mloadingPopup = XPopup.Builder(this@BaseViewModelActivity)
            .hasBlurBg(true) // 是否有高斯模糊的背景，默认为false
            .isDestroyOnDismiss(false)//是否在消失的时候销毁资源，默认false。如果你的弹窗对象只使用一次，非常推荐设置这个，可以杜绝内存泄漏。如果会使用多次，千万不要设置
            .asLoading(ResUtil().resIdFindString(this@BaseViewModelActivity,R.string.loding))
            .show()
                as LoadingPopupView
        return mloadingPopup as LoadingPopupView
    }

    fun dissmisLoadingPopup(){
        showLoadingPopup().dismiss()
    }

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