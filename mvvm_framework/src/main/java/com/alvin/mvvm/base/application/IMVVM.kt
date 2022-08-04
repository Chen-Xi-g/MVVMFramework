package com.alvin.mvvm.base.application

import android.app.Application
import com.alvin.mvvm.help.*
import com.alvin.mvvm.manager.LifecycleCallback
import com.alvin.mvvm.utils.LogUtil
import me.jessyan.autosize.AutoSizeConfig

/**
 * <h3> 作用类描述：初始化Application操作</h3>
 *
 * @Package :        com.alvin.mvvm.base.application
 * @Date :           2022/4/3
 * @author 高国峰
 */
interface IMVVM {

    /**
     * 初始化框架
     *
     * @param application Application
     * @param iSettingBaseActivity ISettingBaseActivity 自定义Activity样式
     * @param iSettingBaseFragment ISettingBaseFragment 自定义Fragment样式
     * @param isDebug Boolean 当前是否为debug模式
     */
    fun initMVVM(
        application: Application,
        iSettingBaseActivity: ISettingBaseActivity = DefaultSettingActivity(),
        iSettingBaseFragment: ISettingBaseFragment = DefaultSettingFragment(),
        isDebug: Boolean = false
    ) {
        // 屏幕适配初始化
        AutoSizeConfig.getInstance().setCustomFragment(true)
            .setExcludeFontScale(true)
            .unitsManager
            .setSupportDP(true).isSupportSP = true
        // 注册Activity生命监听
        application.registerActivityLifecycleCallbacks(LifecycleCallback())
        initUISetting(iSettingBaseActivity, iSettingBaseFragment)
        // 不是Debug的时候关闭日志
        if (!isDebug) {
            LogUtil.closeLog()
        }
    }

    /**
     * 初始化 Activity 与 Fragment 相关操作
     *
     * @param iSettingBaseActivity ISettingBaseActivity 基础Activity设置
     * @param iSettingBaseFragment ISettingBaseFragment 基础Fragment设置
     */
    fun initUISetting(
        iSettingBaseActivity: ISettingBaseActivity,
        iSettingBaseFragment: ISettingBaseFragment
    ) {
        GlobalMVVMBuilder.initSetting(iSettingBaseActivity, iSettingBaseFragment)
    }

}