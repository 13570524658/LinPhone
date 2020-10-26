package com.zhengyuesoftware.linphone.kotlin.net.bean.response

/**
 * 创建日期：2020/10/26 on 15:35
 * github地址:https://github.com/13570524658
 * 作者: Administrator
 * 描述:
 */
class ResponseData<out T> {
    val code = 0
    val msg: String? = null
    val data: T? = null
}