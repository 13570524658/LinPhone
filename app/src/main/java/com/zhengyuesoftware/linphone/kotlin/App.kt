package com.zhengyuesoftware.linphone.kotlin

import android.app.Application

/**
 * 创建日期：2020/10/26 on 14:48
 * github地址:https://github.com/13570524658
 * 作者: Administrator
 * 描述:
 */
open class App : Application() {
    //因为kotlin中的类定义同时也是构造函数，这个时候是不能进行操作的，
    // 所以kotlin增加了一个新的关键字init用来处理类的初始化问题，init模块中的内容可以直接使用构造函数的参数。
    init {
        instance = this
    }
    //Companion Object中定义的成员类似于Java中的静态成员，因为Kotlin中没有static成员
    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
    }

}