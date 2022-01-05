package com.alvin.mvvm.help

/**
 * <h3> 作用类描述：对MVP框架进行全局设置 </h3>
 * @ProjectName:    MVVM_Framework
 * @Package:        com.alvin.mvvm.help
 * @ClassName:      ActivityHolder
 * @CreateDate:      2021/8/29 13:51
 * @Description:    通过Build模式全局设置Activity属性
 * @author Alvin
 */
object GlobalMVPBuilder {
    /**
     * 加载默认的 Activity 设置
     *
     */
    var iSettingBaseActivity: ISettingBaseActivity = DefaultSettingActivity()

    /**
     * 加载默认的 Fragment 设置
     *
     */
    var iSettingBaseFragment: ISettingBaseFragment = DefaultSettingFragment()

    /**
     * 全局初始化
     *
     * @param iSettingBaseActivity 自定义对全局Activity的设置
     */
    fun initSetting(
        iSettingBaseActivity: ISettingBaseActivity = DefaultSettingActivity(),
        iSettingBaseFragment: ISettingBaseFragment = DefaultSettingFragment()
    ) {
        GlobalMVPBuilder.iSettingBaseActivity = iSettingBaseActivity
        GlobalMVPBuilder.iSettingBaseFragment = iSettingBaseFragment
    }
}