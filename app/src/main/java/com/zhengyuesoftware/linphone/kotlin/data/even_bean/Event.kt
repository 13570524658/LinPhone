package com.zhengyuesoftware.linphone.kotlin.data.even_bean

/**
 * 创建日期：2020/10/26 on 16:31
 * github地址:https://github.com/13570524658
 * 作者: Administrator
 * 描述:
 */
class Event<T> {
    var code: Int
    var data: T? = null
        private set

    constructor(code: Int) {
        this.code = code
    }

    constructor(code: Int, data: T) {
        this.code = code
        this.data = data
    }

    fun setData(data: T) {
        this.data = data
    }
}