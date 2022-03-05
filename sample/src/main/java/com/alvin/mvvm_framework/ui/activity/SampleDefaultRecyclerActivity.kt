package com.alvin.mvvm_framework.ui.activity

import android.os.Bundle
import com.alvin.mvvm.base.activity.BaseListActivity
import com.alvin.mvvm_framework.R
import com.alvin.mvvm_framework.databinding.SampleActivityRecyclerBinding
import com.alvin.mvvm_framework.model.ArticleEntity
import com.alvin.mvvm_framework.ui.adapter.SampleRecyclerViewAdapter
import com.alvin.mvvm_framework.view_model.activity.SampleDefaultRecyclerViewModel
import com.blankj.utilcode.util.ToastUtils

/**
 * <h3> 作用类描述：使用框架默认 列表布局</h3>
 *
 * @Package :        com.alvin.mvvm_framework.ui.activity
 * @Date :           2022-03-05
 * @author
 *
 */
class SampleDefaultRecyclerActivity :
    BaseListActivity<SampleDefaultRecyclerViewModel, SampleActivityRecyclerBinding>(
        R.layout.sample_activity_recycler,
        true // 进入布局默认显示加载中
    ) {

    /**
     * 列表适配器
     */
    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        SampleRecyclerViewAdapter()
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        setTitleName("BaseListActivity样本")
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
        viewModel.getArticleListData(page, pageSize)
    }

    /**
     * 初始化适配器
     */
    private fun initAdapter() {
        // 添加分割线
        addDivider(8)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as ArticleEntity
            ToastUtils.showShort(item.title)
        }
    }

}