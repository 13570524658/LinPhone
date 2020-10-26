package com.zhengyuesoftware.linphone.kotlin.window.base.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.gyf.immersionbar.ImmersionBar
import com.zhengyuesoftware.linphone.R
import com.zhengyuesoftware.linphone.kotlin.utils.app_api.ExitAPPUtil
import com.zhengyuesoftware.linphone.kotlin.window.ui.login_module.login_window.view.fragment.LoginFragment
import es.dmoral.toasty.Toasty
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

/**
 * 创建日期：2020/10/26 on 15:51
 * github地址:https://github.com/13570524658
 * 作者: Administrator
 * 描述:
 */
abstract class BaseActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    companion object{
        const val PERMISSION_CODE = 0X01
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initView()
        initData()
        initOnclick()
        //        //设置共同沉浸式样式
        ImmersionBar.with(this)
            .statusBarColor(R.color.colorPrimary)     //状态栏颜色，不写默认透明色
            .navigationBarColor(R.color.colorPrimary) //导航栏颜色，不写默认黑色
            .fullScreen(true)      //有导航栏的情况下，activity全屏显示，也就是activity最下面被导航栏覆盖，不写默认非全屏
            .init()
        ExitAPPUtil().addActivity(this)
    }


    abstract fun getLayoutId(): Int
    abstract fun initView()
    abstract fun initData()
    abstract fun initOnclick()

    protected fun startActivity(z: Class<*>){
        startActivity(Intent(applicationContext, z))
    }


    protected fun showToastInfo(msg: String){
        Toasty.info(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }

    protected fun showToastError(msg: String){
        Toasty.error(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }

    protected fun showToastSuccess(msg: String){
        Toasty.success(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }


    //请求一些必须要的权限
    protected fun requestPermission(permission: Array<String>) {
        if (EasyPermissions.hasPermissions(this, *permission)) {
            //具备权限 直接进行操作
            onPermissionSuccess()
        } else {
            //权限拒绝 申请权限
            EasyPermissions.requestPermissions(this, "为了正常使用，需要获取以下权限", PERMISSION_CODE, *permission); }
    }

    //权限申请相关
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // 将结果转发到EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    //权限获取成功
    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        onPermissionSuccess()
    }

    //权限获取被拒绝
    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        /**
         * 若是在权限弹窗中，用户勾选了'NEVER ASK AGAIN.'或者'不在提示'，且拒绝权限。
         * 这时候，需要跳转到设置界面去，让用户手动开启。
         */
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            //拒绝了权限，而且选择了不在提醒，需要去手动设置了
            AppSettingsDialog.Builder(this).build().show()
        }
        //拒绝了权限，重新申请
        else{
            onPermissionFail()
        }
    }

    /**
     * 权限申请成功执行方法
     */
    protected open fun onPermissionSuccess(){

    }
    /**
     * 权限申请失败
     */
    protected open fun onPermissionFail(){

    }

    /**
     * 扩展函数getFragment可以获得当前显示的fragment盏中的fragment对象，例如有一个login的fragment，只需在activity的onBackPressed处理退出
     */
    @Suppress("UNCHECKED_CAST")
    fun <F : Fragment> AppCompatActivity.getFragment(fragmentClass: Class<F>): F? {
        val navHostFragment = this.supportFragmentManager.fragments.first() as NavHostFragment
        navHostFragment.childFragmentManager.fragments.forEach {
            if (fragmentClass.isAssignableFrom(it.javaClass)) {
                return it as F
            }
        }

        return null
    }
    override fun onBackPressed() {
        //判断当前是哪个fragment
        val fragment = getFragment(LoginFragment::class.java)
        if (fragment != null){
            finish()
        }else{
            super.onBackPressed()
        }
    }
}