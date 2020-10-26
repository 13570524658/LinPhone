package com.zhengyuesoftware.linphone.kotlin.window.ui.main_module.main_window.view.fragment

import com.zhengyuesoftware.linphone.R
import com.zhengyuesoftware.linphone.kotlin.window.base.viewmodel.fragment.BaseViewModelFragment
import com.zhengyuesoftware.linphone.kotlin.window.ui.login_module.login_window.view.fragment.LoginFragment
import com.zhengyuesoftware.linphone.kotlin.window.ui.login_module.login_window.viewmodel.fragment.LoginFragmentViewModel
import com.zhengyuesoftware.linphone.kotlin.window.ui.main_module.main_window.viewmodel.fragment.MainFragmentViewModel

/**
 * 创建日期：2020/10/26 on 18:17
 * github地址:https://github.com/13570524658
 * 作者: Administrator
 * 描述:
 */
class MainFragment : BaseViewModelFragment<MainFragmentViewModel>() {
    private val TAG = LoginFragment::class.java.simpleName

    override fun providerVMClass(): Class<MainFragmentViewModel>? =
        MainFragmentViewModel::class.java

    override fun getLayoutId(): Int {
        return R.layout.fragment_main
    }

    override fun initView() {

    }

    override fun initData() {

    }

    override fun initOnClick() {
    }
}