package com.zhengyuesoftware.linphone.kotlin.window.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.zhengyuesoftware.linphone.kotlin.window.base.activity.BaseActivity
import es.dmoral.toasty.Toasty
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

/**
 * 创建日期：2020/10/26 on 16:29
 * github地址:https://github.com/13570524658
 * 作者: Administrator
 * 描述:
 */
abstract class BaseFragment :Fragment(), EasyPermissions.PermissionCallbacks {
    companion object{
        const val PERMISSION_CODE = 0X01
    }

    protected fun showToastInfo(msg: String){
        Toasty.info(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }

    protected fun showToastError(msg: String){
        Toasty.error(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }

    protected fun showToastSuccess(msg: String){
        Toasty.success(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }

    //请求一些必须要的权限
    protected fun requestPermission(permission: Array<String>) {
        if (EasyPermissions.hasPermissions(requireActivity(), *permission)) {
            //具备权限 直接进行操作
            onPermissionSuccess()
        } else {
            //权限拒绝 申请权限
            EasyPermissions.requestPermissions(this, "为了正常使用，需要获取以下权限",
                PERMISSION_CODE, *permission); }
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
}