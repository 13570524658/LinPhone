package com.zhengyuesoftware.linphone.kotlin.utils.app_api

import android.app.Activity
import com.zhengyuesoftware.linphone.kotlin.App
import java.util.*

/**
 * 创建日期：2020/10/26 on 15:52
 * github地址:https://github.com/13570524658
 * 作者: Administrator
 * 描述:
 */
class ExitAPPUtil() : App() {
    private val activityList: MutableList<Activity?> = LinkedList<Activity?>()

    // 添加Activity到容器中
    fun addActivity(activity: Activity?) {
        activityList.add(activity)
    }

    // 遍历所有Activity并finish
    fun exit() {
        for (activity in activityList) {
            activity!!.finish()
        }
        System.exit(
            0
        )
    }

    companion object {
        // 单例模式中获取唯一的ExitAPPUtils实例
        var instance: ExitAPPUtil? = null
            get() {
                if (null
                    == field) {
                    field = ExitAPPUtil()
                }
                return field
            }
            private set
    }

}