package com.zhengyuesoftware.linphone.kotlin.window.ui.start_module.start_window.view.activity

import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.zhengyuesoftware.linphone.R
import com.zhengyuesoftware.linphone.kotlin.window.base.viewmodel.activity.BaseViewModelActivity
import com.zhengyuesoftware.linphone.kotlin.window.ui.start_module.start_window.viewmodel.activity.StartActivityViewModel

/**
 * 创建日期：2020/10/26 on 15:49
 * github地址:https://github.com/13570524658
 * 作者: Administrator
 * 描述:
 */
class StartActivity : BaseViewModelActivity<StartActivityViewModel>() {
    private val TAG = StartActivity::class.java.simpleName
    override fun providerVMClass(): Class<StartActivityViewModel>? = StartActivityViewModel::class.java
    private lateinit var mAppBarConfiguration: AppBarConfiguration

    override fun getLayoutId(): Int {
        return R.layout.activity_start
    }

    override fun initView() {
    }
    override fun initData() {
    }
    override fun initOnclick() {
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.navStartFragment).navigateUp(mAppBarConfiguration)
    }
}