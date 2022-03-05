package com.alvin.mvvm_framework.base.setting

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.alvin.mvvm.help.ISettingBaseActivity
import com.alvin.mvvm_framework.R
import com.blankj.utilcode.util.ClickUtils
import com.scwang.smart.refresh.footer.BallPulseFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader

/**
 * <h3> 作用类描述：</h3>
 *
 * @Package :        com.alvin.mvvm_framework.base.setting
 * @Date :           2022/3/4
 * @author 高国峰
 */
class BaseActivitySetting : ISettingBaseActivity {

    /**
     * @return Boolean 显示Toast
     */
    override fun isErrorToast(): Boolean {
        return true
    }

    /**
     * @return Boolean 自动宽高适配
     */
    override fun isWidth(): Boolean? {
        return null
    }

    /**
     * @return Float 设计图宽度 375DP
     */
    override fun screenDesignWidth(): Float {
        return 375F
    }

    /**
     * @return Float 设计图高度 640DP
     */
    override fun screenDesignHeight(): Float {
        return 640F
    }

    /**
     * @return Boolean 显示为透明状态栏
     */
    override fun hideStatusBarBackground(): Boolean {
        return true
    }

    /**
     * @return Boolean 暗色状态栏图标
     */
    override fun barLight(): Boolean {
        return true
    }

    /**
     * @return Int 导航栏颜色
     */
    override fun navigationBarColor(): Int {
        return R.color.white
    }

    /**
     * @return Boolean 设置沉浸式状态栏内边框
     */
    override fun isStatusPadding(): Boolean {
        return true
    }

    /**
     * @return Int 加载失败时，点击重试的 View ID
     */
    override fun noNetClickId(): Int {
        return R.id.tvReClick
    }

    /**
     * @return Boolean 加载失败时，显示失败布局
     */
    override fun isShowNoNetLayout(): Boolean {
        return false
    }

    /**
     * @return T? 自定义标题布局
     */
    override fun <T : ViewDataBinding?> putTitleView(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): T? {
        return DataBindingUtil.inflate(inflater, R.layout.sample_title_layout, parent, true)
    }

    /**
     * @return T? 自定义加载中布局
     */
    override fun <T : ViewDataBinding?> putLoadingView(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): T? {
        return DataBindingUtil.inflate(inflater, R.layout.sample_loading_layout, parent, true)
    }

    /**
     * @return T? 自定义加载失败布局
     */
    override fun <T : ViewDataBinding?> putNoNetView(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): T? {
        return DataBindingUtil.inflate(inflater, R.layout.sample_no_net_layout, parent, true)
    }

    /**
     * @return Int 返回按钮样式
     */
    override fun titleLeftIcon(): Int {
        return R.drawable.sample_ic_action_back
    }

    /**
     * 标题控件全局操作
     * @param activity Activity
     * @param ibBarBack AppCompatImageButton?
     * @param tvBarTitle AppCompatTextView?
     * @param tvBarRight AppCompatTextView?
     * @param ibBarRight AppCompatImageButton?
     */
    override fun setTitleLayoutView(
        activity: Activity,
        ibBarBack: AppCompatImageButton?,
        tvBarTitle: AppCompatTextView?,
        tvBarRight: AppCompatTextView?,
        ibBarRight: AppCompatImageButton?
    ) {
        // 点击事件防抖
        ClickUtils.applySingleDebouncing(ibBarBack) {
            // 关闭 activity
//            activity.setResult(Activity.RESULT_CANCELED)  // 可选操作,关闭Activity时,传递Result
            activity.finish()
        }
    }

    /**
     * @return RefreshHeader 设置下拉刷新头样式
     */
    override fun setRefreshHeader(context: Context?): RefreshHeader {
        return MaterialHeader(context)
    }

    /**
     * @return RefreshFooter 设置加载更多样式
     */
    override fun setRefreshFooter(context: Context?): RefreshFooter {
        return BallPulseFooter(context)
    }

    /**
     * @return Int List 分页第一页默认值
     */
    override fun defaultPage(): Int {
        return 0
    }

    /**
     * @return Int List 分页每页数量
     */
    override fun defaultPageSize(): Int {
        return 20
    }

    /**
     * @return Int 分页无数据显示的底部局
     */
    override fun footerView(): Int {
        return R.layout.sample_list_footer_layout
    }

    /**
     * @return Int 分页无数据显示的布局内容
     */
    override fun emptyView(): Int {
        return R.layout.sample_list_empty_layout
    }
}