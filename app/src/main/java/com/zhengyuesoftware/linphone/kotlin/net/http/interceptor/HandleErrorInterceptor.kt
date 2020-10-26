package com.zhengyuesoftware.linphone.kotlin.net.http.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONObject

/**
 * 创建日期：2020/10/26 on 15:39
 * github地址:https://github.com/13570524658
 * 作者: Administrator
 * 描述:
 */
class HandleErrorInterceptor : ResponseBodyInterceptor(), Interceptor {

    override fun intercept(response: Response, url: String, body: String): Response {
        var jsonObject: JSONObject? = null
        try {
            jsonObject = JSONObject(body)
        } catch (e: Exception) {
            e.printStackTrace()
        }
//        if (jsonObject != null) {
//            if (jsonObject.optInt("code", -1) ==0&& jsonObject.has("msg")) {
////                throw Exception(jsonObject.getString("msg"))
//                if (url.contains(Uri.login)||url.contains(Uri.getLoginStatus)) { // 当请求的是登录接口才处理
//                    if (jsonObject.getString("msg") != null) {
//                        jsonObject.put("data", JSONObject())
//                        jsonObject.put("msg", jsonObject.getString("msg"))
//                    }
//                }
//            }
//
//            if (url.contains(Uri.sms)) { // 当请求的是短信接口才处理
//                if (jsonObject.getString("msg") != null) {
//                    jsonObject.put("data", JSONObject())
//                    jsonObject.put("msg", jsonObject.getString("msg"))
//                }
//            }
//        }
        val contentType = response.body!!.contentType()
        val responseBody: ResponseBody = ResponseBody.create(contentType,jsonObject.toString())
        return response.newBuilder().body(responseBody).build()
    }

}