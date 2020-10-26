package com.zhengyuesoftware.linphone.kotlin.window.ui.login_module.login_window.view.fragment

import com.zhengyuesoftware.linphone.R
import com.zhengyuesoftware.linphone.kotlin.window.base.viewmodel.fragment.BaseViewModelFragment
import com.zhengyuesoftware.linphone.kotlin.window.ui.login_module.login_window.viewmodel.fragment.LoginFragmentViewModel

/**
 * 创建日期：2020/10/26 on 18:08
 * github地址:https://github.com/13570524658
 * 作者: Administrator
 * 描述:
 */
class LoginFragment: BaseViewModelFragment<LoginFragmentViewModel>() {
    private val TAG = LoginFragment::class.java.simpleName

    override fun providerVMClass(): Class<LoginFragmentViewModel>? =
        LoginFragmentViewModel::class.java

    override fun getLayoutId(): Int {
        return R.layout.fragment_login
    }

    override fun initView() {

    }

    override fun initData() {

    }

    override fun initOnClick() {

    }
}