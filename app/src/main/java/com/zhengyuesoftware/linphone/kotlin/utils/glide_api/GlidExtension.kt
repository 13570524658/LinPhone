package com.zhengyuesoftware.linphone.kotlin.utils.glide_api

import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.CheckResult
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.annotation.GlideExtension
import com.bumptech.glide.annotation.GlideOption
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.BaseRequestOptions
import com.bumptech.glide.request.RequestOptions
import com.zhengyuesoftware.linphone.R
import com.zhengyuesoftware.linphone.kotlin.App
import com.zhengyuesoftware.linphone.kotlin.utils.glide_api.GlideOptions.bitmapTransform

/**
 * 创建日期：2020/10/26 on 15:04
 * github地址:https://github.com/13570524658
 * 作者: Administrator
 * 描述:
 */
@GlideExtension
object GlidExtension {
    /**
     * @return  设置全局的错误图片 防止更改时多地方修改
     * @describe 当图片加载失败的时候显示
     */
    @DrawableRes
    private fun getErrorImage(): Int {
        return R.drawable.default_error_image
    }

    /**
     * @return 设置全局的占位图 防止更改时多地方修改
     * @describe 当图片没有加载出来的时候显示
     */
    @DrawableRes
    private fun getPlaceholderImage(): Int {
        return R.drawable.default_placeholder_image
    }

    /**
     * 加载圆形头像
     * DecodeFormat.PREFER_RGB_565 比ARGB_8888格式少一半的内存
     * PNG格式可以保存为透明背景的图片，JPEG就不可以，jpeg是一种有损压缩的图片格式
     */
    @JvmStatic
    @JvmOverloads
    @GlideOption
    @CheckResult
    fun applyCircleAvatarImage(
        options: BaseRequestOptions<*>,
        width: Int,
        height: Int
    ): BaseRequestOptions<*> {
        val roundedCorners = RoundedCorners(360)
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        val override = bitmapTransform(roundedCorners)!!.override(width, height)
        return options
            .centerCrop()
            .placeholder(getPlaceholderImage())
            .error(getErrorImage())
            .apply(override)
            .circleCrop()
            .format(DecodeFormat.PREFER_RGB_565)
            .encodeFormat(Bitmap.CompressFormat.PNG)
    }

    /**
     * 加载圆角头像
     * DecodeFormat.PREFER_RGB_565 比ARGB_8888格式少一半的内存
     * PNG格式可以保存为透明背景的图片，JPEG就不可以，jpeg是一种有损压缩的图片格式
     */
    @JvmStatic
    @JvmOverloads
    @GlideOption
    @CheckResult
    fun applyRoundAvatarImage(
        options: BaseRequestOptions<*>,
        width: Int,
        height: Int
    ): BaseRequestOptions<*> {
        val roundedCorners = RoundedCorners(10)
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        val override = bitmapTransform(roundedCorners)!!.override(width, height)
        return options
            .centerCrop()
            .placeholder(getPlaceholderImage())
            .error(getErrorImage())
            .apply(override)
            .format(DecodeFormat.PREFER_RGB_565)
            .encodeFormat(Bitmap.CompressFormat.PNG)
    }


    /**
     * 加载圆角图片
     * roundedCorners：传入的圆角大小
     * DecodeFormat.PREFER_RGB_565 比ARGB_8888格式少一半的内存
     * PNG格式可以保存为透明背景的图片，JPEG就不可以，jpeg是一种有损压缩的图片格式
     */
    @JvmStatic
    @JvmOverloads
    @GlideOption
    @CheckResult
    fun applyRoundImage(
        options: BaseRequestOptions<*>,
        roundedCorners: Int,
        width: Int,
        height: Int
    ): BaseRequestOptions<*> {
        val roundedCorners = RoundedCorners(roundedCorners)
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        val override = bitmapTransform(roundedCorners)!!.override(width, height)
        return options
            .centerCrop()
            .placeholder(getPlaceholderImage())
            .error(getErrorImage())
            .apply(override)
            .format(DecodeFormat.PREFER_RGB_565)
            .encodeFormat(Bitmap.CompressFormat.PNG)
    }


    /**
     * 加载缩略图
     * roundedCorners：传入的圆角大小
     * DecodeFormat.PREFER_RGB_565 比ARGB_8888格式少一半的内存
     * PNG格式可以保存为透明背景的图片，JPEG就不可以，jpeg是一种有损压缩的图片格式
     */
    @JvmStatic
    @JvmOverloads
    @GlideOption
    @CheckResult
    fun applyThumbnailImage(
        options: BaseRequestOptions<*>,
        roundedCorners: Int,
        width: Int,
        height: Int
    ): BaseRequestOptions<*> {
        val roundedCorners = RoundedCorners(roundedCorners)
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        val override = bitmapTransform(roundedCorners)!!.override(width, height)
        return options
            .centerCrop()
            .placeholder(getPlaceholderImage())
            .error(getErrorImage())
            .apply(override)
            .format(DecodeFormat.PREFER_RGB_565)
            .encodeFormat(Bitmap.CompressFormat.PNG)
    }


    /**
     * @describe 加载高斯模糊大图
     * @param ambiguity 模糊度  eg ：80
     * DecodeFormat.PREFER_RGB_565 比ARGB_8888格式少一半的内存
     * 高斯模糊不能用  !!.override(width, height) 采样率压缩
     */
    @JvmStatic
    @JvmOverloads
    @GlideOption
    @CheckResult
    fun applyTransformImage(
        options: BaseRequestOptions<*>,
        roundedCorners: Int,
        blurTransformationRadius: Int,
        blurTransformationsampling: Int,
        width: Int,
        height: Int
    ): BaseRequestOptions<*> {
        val roundedCorners = RoundedCorners(roundedCorners)
        val multiTransformation = MultiTransformation(
            BlurTransformation(
                App.instance,
                blurTransformationRadius,
                blurTransformationsampling
            ), roundedCorners
        )
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        val override = blurTransformation(multiTransformation)!!.override(width, height)
        return options
            .placeholder(getPlaceholderImage())
            .error(getErrorImage())
            .apply(override)
            .format(DecodeFormat.PREFER_RGB_565)
            .encodeFormat(Bitmap.CompressFormat.PNG)
    }

    /**
     * @describe 设置高斯模糊加载的效果
     * @param transformation
     * @return
     */
    @JvmStatic
    @JvmOverloads
    @CheckResult
    private fun blurTransformation(
        multiTransformation: Transformation<Bitmap>
    ): RequestOptions? {
        return RequestOptions.bitmapTransform(multiTransformation).priority(Priority.NORMAL)
    }


    /**
     * @describe 设置加载的效果
     * @param transformation
     * @Priority 优先级设置
     * @return
     */
    @JvmStatic
    @JvmOverloads
    @CheckResult
    private fun bitmapTransform(transformation: BitmapTransformation): RequestOptions? {
        return RequestOptions.bitmapTransform(transformation).priority(Priority.NORMAL)
    }

    /**
     * @return 返回当前 跳过内存缓存
     * true 不缓存 false 缓存
     */
    @JvmStatic
    @JvmOverloads
    @CheckResult
    private fun isSkipMemoryCache(): Boolean {
        return false
    }

    /**
     * @describe 设置缓存
     * Glide有两种缓存机制，一个是内存缓存，一个是硬盘缓存。
     * 内存缓存的主要作用是防止应用重复将图片数据读取到内存当中，
     * 而硬盘缓存的主要作用是防止应用重复从网络或其他地方重复下载和读取数据
     * @diskCacheStrategy参数
     * DiskCacheStrategy.NONE： 表示不缓存任何内容
     * DiskCacheStrategy.DATA： 表示只缓存原始图片
     * DiskCacheStrategy.RESOURCE： 表示只缓存转换过后的图片
     * DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片
     * DiskCacheStrategy.AUTOMATIC： 表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）
     * @return 这里默认设置全部为禁止缓存
     */
    @JvmStatic
    @JvmOverloads
    @CheckResult
    private fun initRequestOptions(transformation: BitmapTransformation): RequestOptions? {
        return RequestOptions()
            .transform(transformation)
            .skipMemoryCache(isSkipMemoryCache()) //是否允许内存缓存
            .onlyRetrieveFromCache(true) //是否只从缓存加载图片
            .diskCacheStrategy(DiskCacheStrategy.NONE) //禁止磁盘缓存
    }

    @JvmStatic
    @JvmOverloads
    @CheckResult
    private fun initRequestOptions(): RequestOptions? {
        return RequestOptions()
            .skipMemoryCache(isSkipMemoryCache()) //是否允许内存缓存
            .onlyRetrieveFromCache(true) //是否只从缓存加载图片
            .diskCacheStrategy(DiskCacheStrategy.NONE) //禁止磁盘缓存
    }


    /**
     * @describe 清除内存缓存
     */
    @JvmStatic
    @JvmOverloads
    fun clearMemory(context: Context?) {
        Glide.get(context!!).clearMemory()
    }

    /**
     * @describe 清除磁盘缓存
     */
    @JvmStatic
    @JvmOverloads
    fun clearDiskCache(context: Context?) {
        Glide.get(context!!).clearDiskCache()
    }
}