package com.alvin.mvvm.base.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.annotation.ColorRes
import com.alvin.mvvm.help.GlobalMVVMBuilder
import com.gyf.immersionbar.ktx.immersionBar
import me.jessyan.autosize.internal.CustomAdapt

/**
 * <h3> 作用类描述：基础的Fragment功能，做相关初始化和适配。</h3>
 *
 * @Package :        com.alvin.mvvm.base.fragment
 * @Date :           2021/12/24-17:39
 * @author Alvin
 */
abstract class BaseFragment : AbstractFragment(), CustomAdapt {

    /**
     * 获取默认的全局设置.
     */
    val iSettingBaseFragment = GlobalMVVMBuilder.iSettingBaseFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 初始化 titleBar
        initBarColor()
    }

    /**
     * 初始化titleBar
     *
     */
    private fun initBarColor() {
        immersionBar {
            //设置透明状态栏 默认透明
            if (hideStatusBarBackground()) {
                transparentStatusBar()
            }
            // 设置状态栏字体颜色 默认白色
            statusBarDarkFont(!barLight())
            navigationBarColor(navigationBarColor())
            init()
        }
    }

    /**
     * 内存不足 手动调用GC
     *
     */
    override fun onLowMemory() {
        System.gc()
        super.onLowMemory()
    }


    override fun isBaseOnWidth(): Boolean = if (isWidth() == null) {
        resources.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT
    } else {
        isWidth() ?: true
    }

    override fun getSizeInDp(): Float = if (isWidth() == null) {
        screenDesignWidth().toFloat()
    } else {
        if (isWidth() == true) screenDesignWidth().toFloat() else screenDesignHeight().toFloat()
    }


    /**
     * true 使用宽度适配（默认）.
     *
     * false 使用高度适配
     */
    open fun isWidth(): Boolean? = iSettingBaseFragment.isWidth()

    /**
     * 设置屏幕适配宽 设计图的宽度.
     *
     * 375（默认）
     */
    open fun screenDesignWidth(): Int = iSettingBaseFragment.screenDesignWidth().toInt()

    /**
     * 设置屏幕适配的高 设计图的宽度.
     *
     * 640（默认）
     */
    open fun screenDesignHeight(): Int = iSettingBaseFragment.screenDesignHeight().toInt()

    /**
     * 是否透明状态栏
     *
     * @return true 是全透明 false 不是
     */
    open fun hideStatusBarBackground(): Boolean = iSettingBaseFragment.hideStatusBarBackground()


    /**
     * 状态栏图标是否为亮色
     *
     * @return 默认true(白色)
     */
    open fun barLight(): Boolean = iSettingBaseFragment.barLight()

    /**
     * 导航栏颜色 白色
     *
     * @return
     */
    @ColorRes
    open fun navigationBarColor(): Int = iSettingBaseFragment.navigationBarColor()

}