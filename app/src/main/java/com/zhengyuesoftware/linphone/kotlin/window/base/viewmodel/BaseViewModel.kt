package com.zhengyuesoftware.linphone.kotlin.window.base.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.lang.Exception

/**
 * 创建日期：2020/10/26 on 15:52
 * github地址:https://github.com/13570524658
 * 作者: Administrator
 * 描述:
 */
open class BaseViewModel : ViewModel(), LifecycleObserver {


    private val mError by lazy { MutableLiveData<Exception>() }

    private val mFinally by lazy { MutableLiveData<Int>() }

    //运行在UI线程的协程
    fun launchUI(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch {
        try {
            withTimeout(5000) {
                block()
            }
        } catch (e: Exception) {
            mError.value = e
        } finally {
            mFinally.value = 200
        }
    }

    /**
     * 请求失败，出现异常
     */
    fun getError(): LiveData<Exception> {
        return mError
    }

    /**
     * 请求完成，在此处做一些关闭操作
     */
    fun getFinally(): LiveData<Int> {
        return mFinally
    }
}