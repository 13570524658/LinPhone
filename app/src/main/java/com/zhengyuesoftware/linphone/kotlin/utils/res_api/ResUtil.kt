package com.zhengyuesoftware.linphone.kotlin.utils.res_api

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

/**
 * 创建日期：2020/10/26 on 16:36
 * github地址:https://github.com/13570524658
 * 作者: Administrator
 * 描述: 资源文件转换工具类
 */
class ResUtil {
    fun resIdFindString(context: Context, resId: Int): String {
        return context.resources.getString(resId)
    }

    fun resIdFindStringArray(context: Context, resId: Int): Array<String> {
        return context.resources.getStringArray(resId)
    }

    fun resIdFindDrawable(context: Context, resId: Int): Drawable {
        return ContextCompat.getDrawable(context,resId)!!
    }

    fun resIdFindColor(context: Context, resId: Int): Int {
        return ContextCompat.getColor(context,resId)
    }
}