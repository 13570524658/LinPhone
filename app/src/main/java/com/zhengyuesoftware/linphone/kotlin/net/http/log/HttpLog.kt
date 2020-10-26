package com.zhengyuesoftware.linphone.kotlin.net.http.log

import android.util.Log
import okhttp3.logging.HttpLoggingInterceptor

/**
 * 创建日期：2020/10/26 on 15:40
 * github地址:https://github.com/13570524658
 * 作者: Administrator
 * 描述:
 */
class HttpLog : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        Log.e("HttpLogInfo", message)
    }
}