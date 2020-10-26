package com.zhengyuesoftware.linphone.kotlin.window.ui.start_module.start_window.view.fragment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.zhengyuesoftware.linphone.R
import com.zhengyuesoftware.linphone.kotlin.window.base.viewmodel.fragment.BaseViewModelFragment
import com.zhengyuesoftware.linphone.kotlin.window.ui.start_module.start_window.viewmodel.fragment.StartFragmentViewModel
import kotlinx.android.synthetic.main.activity_start.*
import java.util.*

/**
 * 创建日期：2020/10/26 on 17:07
 * github地址:https://github.com/13570524658
 * 作者: Administrator
 * 描述:
 */
class StartFragment: BaseViewModelFragment<StartFragmentViewModel>() {
    private val TAG = StartFragment::class.java.simpleName
    override fun providerVMClass(): Class<StartFragmentViewModel>? =
        StartFragmentViewModel::class.java

    override fun getLayoutId(): Int {
        return R.layout.fragment_start
    }

    override fun initView() {
        initNotificationManager()
        val task: TimerTask = object : TimerTask() {
            override fun run() {
                NavHostFragment.findNavController(navStartFragment)
                    .navigate(R.id.next_action_LoginFragment)
            }
        }
        val timer = Timer()
        timer.schedule(task, 3000) //3秒后执行TimeTask的run方法
    }

    override fun initData() {

    }

    override fun initOnClick() {

    }

    private fun initNotificationManager() {
        val args = Bundle()
        args.putString("myarg", "服务")
        val deeplink = findNavController().createDeepLink()
            .setDestination(R.id.startFragment)
            .setArguments(args)
            .createPendingIntent()
        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    "deeplink", "Deep Links", NotificationManager.IMPORTANCE_HIGH
                )
            )
        }
        val res: Resources = requireActivity().resources
        val icLauncher = BitmapFactory.decodeResource(res, R.mipmap.ic_launcher)
        val labelName = resources.getString(R.string.label_name)
        val seviceRun = resources.getString(R.string.sevice_run)
        val builder = NotificationCompat.Builder(
            requireActivity(), "deeplink"
        )
            .setContentTitle(labelName)
            .setContentText(seviceRun)
            .setSmallIcon(R.mipmap.ic_launcher_round, 1000)
            .setLargeIcon(icLauncher)
            .setContentIntent(deeplink)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setOngoing(true)
            .setAutoCancel(true)
        notificationManager.notify(0, builder.build())
    }
}