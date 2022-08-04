/*
 * Copyright 2022 高国峰
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alvin.mvvm.help

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.alvin.mvvm.R
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader

/**
 * <h3> 作用类描述：对BaseFragment进行基础设置</h3>
 *
 * @Package :        com.alvin.mvvm.help
 * @Date :           2021/9/2-17:10
 * @author Alvin
 */
interface ISettingBaseFragment {

    /**
     * 网络请求失败时，是否通过 Toast 显示错误信息
     *
     * @return true 显示； false 不显示
     */
    fun isErrorToast(): Boolean = true

    /**
     * 设置使用屏幕适配的方案.
     *
     * 如果值为NULL，那么会根据横竖屏自动适配
     *
     * @return ture使用宽度适配（默认）. false 使用高度适配
     */
    fun isWidth(): Boolean?

    /**
     * 设置屏幕适配宽 设计图的宽度
     *
     * @return 375（默认）
     */
    fun screenDesignWidth(): Float = 375F

    /**
     * 设置屏幕适配的高 设计图的宽度
     *
     * @return 640（默认）
     */
    fun screenDesignHeight(): Float = 640F


    /**
     * 是否透明状态栏
     *
     * @return true 透明（默认值）. false 不透明
     */
    fun hideStatusBarBackground(): Boolean = true

    /**
     * 状态栏图标是否为黑色
     *
     * @return true白色（默认值）. false黑色
     */
    fun barLight(): Boolean = true

    /**
     * 导航栏颜色
     *
     * @return 默认值为白色，其他色值全局修改
     */
    @ColorRes
    fun navigationBarColor(): Int = R.color.white

    /*             ToolbarActivity              */


    /**
     * 沉浸式状态栏是否需要设置内边距
     *
     * @return true需要设置（默认值）. false不需要设置
     */
    fun isStatusPadding(): Boolean = true


    /**
     * 设置加载失败需要响应重新加载的ViewID
     *
     * @return 默认值 `R.id.clNoNet`
     */
    @IdRes
    fun noNetClickId(): Int = R.id.clNoNet

    /**
     * 加载失败的时候是否显示加载失败布局
     *
     * @return Boolean false 不需要显示(默认值), true 需要显示
     */
    fun isShowNoNetLayout(): Boolean = false

    /**
     * 设置自定义标题布局.
     *
     * 如果需要各种自定义属性，请继承BaseMVPActivity再写一个CustomBaseActivity.
     *
     * ### 在TitleLayout中
     *
     * 默认返回Id为 [androidx.appcompat.widget.AppCompatImageButton] ->`ibBarBack`，进行返回处理.
     *
     * 默认标题Id为 [androidx.appcompat.widget.AppCompatTextView] ->`tvBarTitle` 进行标题设置.
     *
     * 默认右侧Menu文字为 [androidx.appcompat.widget.AppCompatTextView] ->`tvBarRight` 进行右侧Menu设置.
     *
     * 默认右侧Menu Icon为 [androidx.appcompat.widget.AppCompatImageButton] ->`ibBarRight` 进行右侧Icon设置.
     *
     * @see R.layout.mvvm_activity_base_title 默认布局
     * @return 默认值 `ActivityBaseTitleBinding`
     */
    fun <T : ViewDataBinding?> putTitleView(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): T? =
        DataBindingUtil.inflate(inflater, R.layout.mvvm_activity_base_title, parent, true)

    /**
     * 第一次加载布局时显示的Loading
     *
     * @param inflater
     * @return 默认值 `IncludeBaseLoadingBinding`
     */
    fun <T : ViewDataBinding?> putLoadingView(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): T? =
        DataBindingUtil.inflate(inflater, R.layout.mvvm_include_base_loading, parent, true)

    /**
     * 添加加载失败的布局ID
     *
     * @see R.layout.mvvm_activity_base 默认加载失败的布局
     * @return 默认值 `ActivityBaseNonetBinding`
     */
    fun <T : ViewDataBinding?> putNoNetView(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): T? =
        DataBindingUtil.inflate(inflater, R.layout.mvvm_activity_base_nonet, parent, true)

    /**
     * 设置标题默认的返回样式，如果自定义Title，其Id必须为 `ibBarBack`，否则无效.
     *
     * @return 默认值 `R.drawable.ic_back`
     */
    @DrawableRes
    fun titleLeftIcon(): Int = R.drawable.base_ic_action_back

    /**
     * 默认布局下全局设置TitleView的属性
     *
     * @param ibBarBack 左侧返回
     * @param tvBarTitle 标题
     * @param tvBarRight 右侧Menu文字
     * @param ibBarRight 右侧Menu按钮
     */
    fun setTitleLayoutView(
        fragment: Fragment?,
        ibBarBack: AppCompatImageButton?,
        tvBarTitle: AppCompatTextView?,
        tvBarRight: AppCompatTextView?,
        ibBarRight: AppCompatImageButton?
    )

    /**
     * 全局设置刷新头
     *
     * @param context 上下文
     * @return 返回自定义的刷新头 默认为： `ClassicsHeader`
     */
    fun setRefreshHeader(context: Context?): RefreshHeader = ClassicsHeader(context)

    /**
     * 全局设置加载底部
     *
     * @param context 上下文
     * @return 返回自定义的刷新头 默认为： `ClassicsFooter`
     */
    fun setRefreshFooter(context: Context?): RefreshFooter = ClassicsFooter(context)

    /**
     * 设置默认的Page页数
     *
     */
    fun defaultPage(): Int = 1

    /**
     * 每页加载的数量
     *
     */
    fun defaultPageSize(): Int = 10

    /**
     * 加载更多，没有下一页数据时显示的底布局
     *
     * @return
     */
    @LayoutRes
    fun footerView(): Int = R.layout.mvvm_base_adapter_footer

    /**
     * 当列表数据为空时，显示的空数据布局
     *
     * @return
     */
    @LayoutRes
    fun emptyView(): Int = R.layout.mvvm_base_empty_layout
}