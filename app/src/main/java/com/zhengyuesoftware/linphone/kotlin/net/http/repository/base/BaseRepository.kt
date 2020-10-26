package com.zhengyuesoftware.linphone.kotlin.net.http.repository.base

import com.zhengyuesoftware.linphone.kotlin.net.bean.response.ResponseData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * 创建日期：2020/10/26 on 15:36
 * github地址:https://github.com/13570524658
 * 作者: Administrator
 * 描述:处理http返回码
 */
open class BaseRepository {
    suspend fun <T : Any> request(call: suspend () -> ResponseData<T>): ResponseData<T> {
        return withContext(Dispatchers.IO) { call.invoke() }.apply {
            //这儿可以对返回结果errorCode做一些特殊处理，比如token失效等，可以通过抛出异常的方式实现
            //例：当token失效时，后台返回errorCode 为 100，下面代码实现,再到baseActivity通过观察error来处理

            when (code) {
                100 -> throw TokenInvalidException()

                404 -> throw NetworkErrorException()

                500 -> throw ServerException()
            }
        }
    }
    class TokenInvalidException(msg: String = "token invalid") : Exception(msg)
    class NetworkErrorException(msg: String = "network invalid") : Exception(msg)
    class ServerException(msg: String = "server invalid") : Exception(msg)
}