package com.zhengyuesoftware.linphone.kotlin.service

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.os.Bundle
import android.widget.RemoteViews
import androidx.navigation.NavDeepLinkBuilder
import com.zhengyuesoftware.linphone.R

/**
 * 创建日期：2020/10/26 on 17:58
 * github地址:https://github.com/13570524658
 * 作者: Administrator
 * 描述:AppWidgetProvider是android中提供的用于实现桌面小工具的类，其本质是一个广播，即BroadcastReceiver
 */
class DeepLinkAppWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        val remoteViews = RemoteViews(
            context.packageName,
            R.layout.deep_link_appwidget
        )

        val args = Bundle()
        args.putString("myarg", "From Widget")
        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.start_navigation)
            .setDestination(R.id.startFragment)
            .setArguments(args)
            .createPendingIntent()

        remoteViews.setOnClickPendingIntent(R.id.deepLinkButton, pendingIntent)
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews)
    }
}