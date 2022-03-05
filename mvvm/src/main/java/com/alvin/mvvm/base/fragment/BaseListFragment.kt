package com.alvin.mvvm.base.fragment

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.alvin.mvvm.R
import com.alvin.mvvm.base.view_model.BaseViewModel
import com.chad.library.adapter.base.BaseQuickAdapter
import com.fondesa.recyclerviewdivider.dividerBuilder
import com.fondesa.recyclerviewdivider.staggeredDividerBuilder
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import me.jessyan.autosize.AutoSize

/**
 * <h3> 作用类描述：适用于列表的Fragment</h3>
 *
 * @Package :        com.alvin.mvvm.base.fragment
 * @Date :           2021/12/22-16:15
 * @author Alvin
 */
abstract class BaseListFragment<VM : BaseViewModel, DB : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int
) : BaseMVVMFragment<VM, DB>(layoutRes), OnRefreshLoadMoreListener {
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
    var page: Int = iSettingBaseFragment.defaultPage()

    /**
     * List 每页 数量
     */
    val pageSize: Int = iSettingBaseFragment.defaultPageSize()

    override fun initView(view: View, savedInstanceState: Bundle?) {
        _rootRefresh = view.findViewById(R.id.rootRefresh)
        _recyclerView = view.findViewById(R.id.rootRecycler)
        // 设置刷新属性
        rootRefresh(rootRefresh)
        // 设置刷新监听
        rootRefresh.setOnRefreshLoadMoreListener(this)
    }

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
     * 如果通过 ISettingBaseActivity 统一设置 刷新头/加载底部，它的优先级为：
     *
     * [rootRefresh] > [setRefreshHeader] or [setRefreshFooter] >
     * [com.alvin.mvvm.help.ISettingBaseActivity.setRefreshFooter] or [com.alvin.mvvm.help.ISettingBaseActivity.setRefreshFooter],
     *
     */
    open fun rootRefresh(rootRefresh: SmartRefreshLayout) {
        context?.let {
            // 设置头部和底部
            rootRefresh.setRefreshHeader(setRefreshHeader(it))
            rootRefresh.setRefreshFooter(setRefreshFooter(it))
        }
    }

    /**
     * 单独设置这个Activity的刷新头
     *
     * @param context 上下文
     */
    open fun setRefreshHeader(context: Context): RefreshHeader =
        iSettingBaseFragment.setRefreshHeader(context)

    /**
     * 单独设置这个Activity的加载底部
     *
     * @param context 上下文
     */
    open fun setRefreshFooter(context: Context): RefreshFooter =
        iSettingBaseFragment.setRefreshFooter(context)

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
        page = iSettingBaseFragment.defaultPage()
        loadData()
    }

    /**
     * 刷新时需要加载的数据
     *
     */
    abstract fun loadData()

    /**
     * 刷新结束时调用
     *
     * @param T type of data, 数据类型
     * @param list 返回的数据集合
     * @param adapter 适配器
     * @param pageSize 默认每页加载数量
     * @param footerView
     * @param emptyView
     */
    fun <T> SmartRefreshLayout.finish(
        list: Collection<T>,
        adapter: BaseQuickAdapter<T, *>,
        pageSize: Int = iSettingBaseFragment.defaultPageSize()
    ) {
        if (page > iSettingBaseFragment.defaultPage()) {
            adapter.addData(list)
            finishLoadMore()
        } else {
            adapter.setList(list)
            finishRefresh()
        }
        if (list.size < pageSize) {
            adapter.removeAllFooterView()
            adapter.addFooterView(getFooterView(context, recyclerView))
            setEnableLoadMore(false)
            if (list.isNullOrEmpty()) {
                adapter.setEmptyView(getEmptyView(context, recyclerView))
            }
        } else {
            adapter.removeAllFooterView()
            setEnableLoadMore(true)
        }
    }

    /**
     * Recyclerview 没有更多页数时加载的底布局
     *
     * @param context
     * @param recyclerView
     * @return
     */
    private fun getFooterView(context: Context, recyclerView: RecyclerView): View {
        return LayoutInflater.from(context).inflate(
            footerView(),
            recyclerView.parent as ViewGroup, false
        )
    }

    /**
     * List布局为空时的数据
     *
     * @param context
     * @param recyclerView
     * @return
     */
    private fun getEmptyView(context: Context, recyclerView: RecyclerView): View {
        return LayoutInflater.from(context).inflate(
            emptyView(),
            recyclerView.parent as ViewGroup, false
        )
    }

    /**
     * 添加列表分割线
     *
     * @param size 分割线尺寸 尺寸为DP
     * @param isStaggered 是否为瀑布流
     */
    protected fun addDivider(size: Int, isStaggered: Boolean = false) {
        if (isStaggered) {
            activity.staggeredDividerBuilder()
                .size(size, TypedValue.COMPLEX_UNIT_DIP)
                .asSpace()
                .hideSideDividers()
                .build()
                .addTo(recyclerView)
        } else {
            activity.dividerBuilder()
                .size(size, TypedValue.COMPLEX_UNIT_DIP)
                .asSpace()
                .build()
                .addTo(recyclerView)
        }
    }

    /**
     * 自定义List没有更多数据的底布局
     */
    open fun footerView() = iSettingBaseFragment.footerView()

    /**
     * 自定义List空布局
     */
    open fun emptyView() = iSettingBaseFragment.emptyView()

    override fun afterNetwork() {
        super.afterNetwork()
        if (page == iSettingBaseFragment.defaultPage()) {
            rootRefresh.finishRefresh(true)
        } else {
            rootRefresh.finishLoadMore(true)
        }
    }

    override fun onFailed(errorMsg: String?) {
        super.onFailed(errorMsg)
        // 失败 结束加载和刷新
        if (page == iSettingBaseFragment.defaultPage()) {
            rootRefresh.finishRefresh(false)
        } else {
            rootRefresh.finishLoadMore(false)
        }
    }

    override fun onResume() {
        super.onResume()
        AutoSize.autoConvertDensityOfGlobal(activity)
    }
}