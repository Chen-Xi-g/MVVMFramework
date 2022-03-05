package com.alvin.mvvm_framework.ui.activity

import android.content.Context
import android.os.Bundle
import com.alvin.mvvm.base.activity.BaseListActivity
import com.alvin.mvvm_framework.R
import com.alvin.mvvm_framework.databinding.SampleActivityRecyclerBinding
import com.alvin.mvvm_framework.model.ArticleEntity
import com.alvin.mvvm_framework.ui.adapter.SampleRecyclerViewAdapter
import com.alvin.mvvm_framework.view_model.activity.SampleDefaultRecyclerViewModel
import com.blankj.utilcode.util.ToastUtils
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout

/**
 * <h3> 作用类描述：使用框架自带函数自定义 列表布局</h3>
 *
 * @Package :        com.alvin.mvvm_framework.ui.activity
 * @Date :           2022/3/5
 * @author 高国峰
 */
class SampleCustomRecyclerActivity :
    BaseListActivity<SampleDefaultRecyclerViewModel, SampleActivityRecyclerBinding>(
        R.layout.sample_activity_recycler,
        false // 进入布局不需要显示正在加载布局, 默认值为false, 可以不写
    ) {

    /**
     * 列表适配器
     */
    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        SampleRecyclerViewAdapter()
    }

    /**
     * 加载数据时的 Dialog 提示内容
     */
    private var loadMsg: String? = "正在加载数据"

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        setTitleName("BaseListActivity样本")
        // 自定义刷新监听逻辑, 该方法要在触发刷新和加载回调之前调用
        initAdapter()
    }

    /**
     * 在obtainData中做逻辑处理，不要做View初始化
     */
    override fun obtainData() {
        loadData()
    }

    /**
     * 初始化LiveData数据观察者
     */
    override fun registerObserver() {
        viewModel.articleListData.observe(this) {
            // rootRefresh调用finish, 传入集合与适配器, 内部自动完善分页逻辑
            rootRefresh.finish(it, adapter)
        }
    }

    /**
     * 加载List刷新和加载更多的数据
     *
     * 数据请求成功后调用 `rootRefresh.finish(list,adapter)` 结束刷新，
     * 内部在下拉刷新或上拉加载时调用loadData
     * 可以根据需求, 在initView中调用 `refreshLoadMoreListener` 定义刷新监听
     */
    override fun loadData() {
        viewModel.getArticleListData(page, pageSize, loadMsg)
    }

    /**
     * 初始化适配器
     */
    private fun initAdapter() {
        // 添加分割线
        addDivider(12)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as ArticleEntity
            ToastUtils.showShort(item.title)
        }
    }

    /**
     * @return RefreshHeader 单独为页面设置刷新头
     */
    override fun setRefreshHeader(context: Context): RefreshHeader {
        return ClassicsHeader(context)
    }

    /**
     * @return RefreshFooter 单独为页面设置加载底
     */
    override fun setRefreshFooter(context: Context): RefreshFooter {
        return ClassicsFooter(context)
    }

    /**
     * 重写下拉刷新逻辑
     */
    override fun onRefresh(refreshLayout: RefreshLayout) {
        // 在原有逻辑上增加相关业务操作,不需要注释 super.onRefresh(refreshLayout)
        // 如果注释 super.onLoadMore(refreshLayout) , 需要对 page 进行分页处理
//        super.onRefresh(refreshLayout)
        loadMsg = null
        page = 1
        ToastUtils.showShort("下拉刷新$page")
        loadData()
    }

    /**
     * 重写加载更多逻辑
     */
    override fun onLoadMore(refreshLayout: RefreshLayout) {
        // 在原有逻辑上增加相关业务操作,不需要注释 super.onLoadMore(refreshLayout).
        // 如果注释 super.onLoadMore(refreshLayout) , 需要对 page 进行分页处理
//        super.onLoadMore(refreshLayout)
        loadMsg = null
        page++
        ToastUtils.showShort("上拉加载$page")
        loadData()
    }

    /**
     * @return Int 返回当前列表自定义空数据布局, 当调用 rootRefresh.finish 会自加载.
     */
    override fun emptyView(): Int {
        return R.layout.sample_list_custom_empty_layout
    }

    /**
     * @return Int 返回当前列表自定义的底布局, 当调用 rootRefresh.finish 会自加载.
     */
    override fun footerView(): Int {
        return R.layout.sample_list_custom_footer_layout
    }

}