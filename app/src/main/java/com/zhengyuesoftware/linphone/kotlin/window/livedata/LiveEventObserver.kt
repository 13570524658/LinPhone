package com.zhengyuesoftware.linphone.kotlin.window.livedata

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.lifecycle.*

/**
 * 创建日期：2020/10/26 on 17:14
 * github地址:https://github.com/13570524658
 * 作者: Administrator
 * 描述:
 */
class LiveEventObserver<T>(liveData: LiveData<T>?, owner: LifecycleOwner?, observer: Observer<in T>?) : LifecycleObserver,
    Observer<T> {
    private var mLiveData: LiveData<T>?
    private var mOwner: LifecycleOwner?
    private var mObserver: Observer<in T>?
    private val mPendingData: MutableList<T> = ArrayList()

    /**
     * 在生命周期结束前的任何时候都可能会调用
     */
    override fun onChanged(@Nullable t: T) {
        if (isActive) {
            // 如果是激活状态，就直接更新
            mObserver!!.onChanged(t)
        } else {
            // 非激活状态先把数据存起来
            mPendingData.add(t)
        }
    }

    /**
     * @return 是否是激活状态，即 onStart 之后到 onPause 之前
     */
    private val isActive: Boolean
        private get() = mOwner!!.lifecycle.currentState
            .isAtLeast(Lifecycle.State.STARTED)

    /**
     * onStart 之后就是激活状态了，如果之前存的有数据，就发送出去
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    private fun onEvent(owner: LifecycleOwner, event: Lifecycle.Event) {
        if (owner !== mOwner) {
            return
        }
        if (event === Lifecycle.Event.ON_START || event === Lifecycle.Event.ON_RESUME) {
            for (i in mPendingData.indices) {
                mObserver!!.onChanged(mPendingData[i])
            }
            mPendingData.clear()
        }
    }

    /**
     * onDestroy 时解除各方的观察和绑定，并清空数据
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestroy() {
        mLiveData!!.removeObserver(this)
        mLiveData = null
        mOwner!!.lifecycle.removeObserver(this)
        mOwner = null
        mPendingData.clear()
        mObserver = null
    }

    companion object {
        fun <T> bind(@NonNull liveData: LiveData<T>?,
                     @NonNull owner: LifecycleOwner,
                     @NonNull observer: Observer<in T>?) {
            if (owner.lifecycle.currentState === Lifecycle.State.DESTROYED) {
                return
            }
            LiveEventObserver<Any>(liveData, owner, observer)
        }

        private fun <T> LiveEventObserver(liveData: T?, owner: T, observer: T?) {

        }
    }

    init {
        mLiveData = liveData
        mOwner = owner
        mObserver = observer
        mOwner!!.lifecycle.addObserver(this)
        mLiveData!!.observeForever(this)
    }
}