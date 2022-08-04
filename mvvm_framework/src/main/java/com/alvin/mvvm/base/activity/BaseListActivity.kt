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
package com.alvin.mvvm.base.activity

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.alvin.mvvm.R
import com.alvin.mvvm.base.model.Footer
import com.alvin.mvvm.base.view_model.BaseViewModel
import com.alvin.rvad.ReuseAdapter
import com.fondesa.recyclerviewdivider.dividerBuilder
import com.fondesa.recyclerviewdivider.staggeredDividerBuilder
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import me.jessyan.autosize.AutoSize

/**
 * <h3> 作用类描述：适用于列表的Activity</h3>
 *
 * @Package :        com.alvin.mvvm.base.activity
 * @Date :           2021/12/22-15:56
 * @author Alvin
 */
abstract class BaseListActivity<VM : BaseViewModel, DB : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int,
    private val isGoneContent: Boolean = false
) : BaseMVVMActivity<VM, DB>(layoutRes, isGoneContent), OnRefreshLoadMoreListener {

    /**
     * 获取Refresh控件
     *
     */
    private var _rootRefresh: SmartRefreshLayout? = null
    val rootRefresh get() = _rootRefresh!!

    /**
     * 获取RecyclerView控件
     *
     */
    private var _recyclerView: RecyclerView? = null
    val recyclerView get() = _recyclerView!!

    /**
     * List 页数
     */
    var page: Int = iSettingActivity.defaultPage()

    /**
     * List 每页 数量
     */
    val pageSize: Int = iSettingActivity.defaultPageSize()

    override fun initView(savedInstanceState: Bundle?) {
        _rootRefresh = findViewById(R.id.rootRefresh)
        _recyclerView = findViewById(R.id.rootRecycler)
        rootRefresh(rootRefresh)
        // 设置刷新监听
        rootRefresh.setOnRefreshLoadMoreListener(this)
    }

    /**
     * 刷新时需要加载的数据
     *
     */
    abstract fun loadData()

    /**
     * 加载更多数据
     */
    override fun onLoadMore(refreshLayout: RefreshLayout) {
        page++
        loadData()
    }

    /**
     * 刷新数据
     */
    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = iSettingActivity.defaultPage()
        loadData()
    }

    /**
     * 刷新结束时调用
     *
     * @param T type of data, 数据类型
     * @param list 返回的数据集合
     * @param adapter 适配器
     * @param pageSize 默认每页加载数量
     * @param isLoadMore 是否显示加载更多, 设置为true时， list.size < pageSize 会无效
     */
    fun <T> SmartRefreshLayout.finish(
        list: List<T>,
        adapter: ReuseAdapter,
        pageSize: Int = iSettingActivity.defaultPageSize(),
        isLoadMore: Boolean = false,
    ) {
        if (page > iSettingActivity.defaultPage()) {
            adapter.addData(list)
            finishLoadMore()
        } else {
            adapter.setData(list)
            finishRefresh()
        }
        if (isLoadMore) {
            if (adapter.footerCount > 0) {
                adapter.removeFooter(Footer())
                setEnableLoadMore(true)
            }
        } else {
            if (list.size < pageSize) {
                // 已经没有分页
                adapter.removeFooter(Footer())
                adapter.addType<Footer>(footerView())
                adapter.addFooter(Footer())
                setEnableLoadMore(false)
            } else {
                if (adapter.footerCount > 0) {
                    adapter.removeFooter(Footer())
                    setEnableLoadMore(true)
                }
            }
        }
    }

    /**
     * 添加列表分割线
     *
     * @param size 分割线尺寸 尺寸为DP
     * @param isStaggered 是否为瀑布流
     */
    protected fun addDivider(size: Int, isStaggered: Boolean = false) {
        if (isStaggered) {
            staggeredDividerBuilder()
                .size(size, TypedValue.COMPLEX_UNIT_DIP)
                .asSpace()
                .hideSideDividers()
                .build()
                .addTo(recyclerView)
        } else {
            dividerBuilder()
                .size(size, TypedValue.COMPLEX_UNIT_DIP)
                .asSpace()
                .build()
                .addTo(recyclerView)
        }
    }

    /**
     * 自定义List没有更多数据的底布局
     */
    open fun footerView() = iSettingActivity.footerView()

    /**
     * 设置 SmartRefreshLayout 的属性。
     *
     * ### 默认布局
     *
     * 默认使用[SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout)中的
     *
     * * `com.scwang.smart:refresh-header-classics:2.0.3` 加载刷新头
     *
     * * `com.scwang.smart:refresh-footer-classics:2.0.3` 加载更多底部
     *
     * ### 自定义布局
     *
     * 如果是自定义布局可以通过调用该方法对 SmartRefreshLayout 进行单独的属性设置。
     *
     * 如果通过 ISettingBaseActivity 统一设置 刷新头/加载底部，它的优先级为：</br>
     *
     * [rootRefresh] 大于 [setRefreshHeader] or [setRefreshFooter] 大于
     * [com.alvin.mvvm.help.ISettingBaseActivity.setRefreshFooter] or [com.alvin.mvvm.help.ISettingBaseActivity.setRefreshFooter],
     *
     */
    open fun rootRefresh(rootRefresh: SmartRefreshLayout) {
        // 设置头部和底部
        rootRefresh.setRefreshHeader(setRefreshHeader(this))
        rootRefresh.setRefreshFooter(setRefreshFooter(this))
    }

    /**
     * 单独设置这个Activity的刷新头
     *
     * @param context 上下文
     */
    open fun setRefreshHeader(context: Context): RefreshHeader =
        iSettingActivity.setRefreshHeader(context)

    /**
     * 单独设置这个Activity的加载底部
     *
     * @param context 上下文
     */
    open fun setRefreshFooter(context: Context): RefreshFooter =
        iSettingActivity.setRefreshFooter(context)

    override fun afterNetwork() {
        super.afterNetwork()
        if (page == iSettingActivity.defaultPage()) {
            rootRefresh.finishRefresh(true)
        } else {
            rootRefresh.finishLoadMore(true)
        }
    }

    override fun onFailed(errorMsg: String?) {
        super.onFailed(errorMsg)
        // 失败 结束加载和刷新
        if (page == iSettingActivity.defaultPage()) {
            rootRefresh.finishRefresh(false)
        } else {
            rootRefresh.finishLoadMore(false)
        }
    }

    override fun onResume() {
        super.onResume()
        AutoSize.autoConvertDensityOfGlobal(this)
    }
}