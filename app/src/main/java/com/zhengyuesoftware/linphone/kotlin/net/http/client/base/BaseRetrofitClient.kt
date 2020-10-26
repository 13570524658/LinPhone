package com.zhengyuesoftware.linphone.kotlin.net.http.client.base

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 创建日期：2020/10/26 on 15:37
 * github地址:https://github.com/13570524658
 * 作者: Administrator
 * 描述:
 */
class BaseRetrofitClient {
    /**
     * 获取对应的service
     * @param service service class
     * @param baseUrl basUrl
     * @return service
     */
    fun <S> getService(service: Class<S>,baseUrl: String): S {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(service)
    }
}