package com.alvin.mvvm_framework.ui.fragment.tv

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.alvin.mvvm.base.fragment.BaseListFragment
import com.alvin.mvvm_framework.R
import com.alvin.mvvm_framework.databinding.FragmentTvListBinding
import com.alvin.mvvm_framework.model.entity.QQMVListEntity
import com.alvin.mvvm_framework.ui.fragment.tv.adapter.TVListAdapter
import com.alvin.mvvm_framework.viewmodel.fragment.tv.TVListViewModel


/**
 * <h3> 作用类描述：TV列表</h3>
 *
 * @Package :        com.alvin.mvvm_framework.ui.fragment.tv
 * @Date :           2022/1/8
 * @author 高国峰
 */
class TVListFragment :
    BaseListFragment<TVListViewModel, FragmentTvListBinding>(R.layout.fragment_tv_list) {

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        TVListAdapter()
    }

    override fun obtainData() {
        ibBarBack.visibility = View.GONE
        setTitleName("TV")
        dataBinding.viewModel = viewModel
        initAdapter()
        dataBinding.editMsg.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                loadData()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    override fun lazyLoadData() {
    }

    override fun loadData() {
        viewModel.searchQQMV(dataBinding.editMsg.text.toString())
    }

    /**
     * 本地测试数据
     */
    private fun initAdapter() {
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as QQMVListEntity
            val bundle = Bundle()
            bundle.putString("id", item.id)
            bundle.putString("msg", dataBinding.editMsg.text.toString())
            Navigation.findNavController(requireView())
                .navigate(R.id.action_mainFragment_to_TVDetailFragment,bundle)
        }
    }


    override fun registerObserver() {
        viewModel.qqMVList.observe(this) {
            rootRefresh.finish(it.toMutableList(), adapter)
        }
    }
}