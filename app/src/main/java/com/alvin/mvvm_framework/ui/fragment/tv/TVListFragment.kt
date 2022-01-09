package com.alvin.mvvm_framework.ui.fragment.tv

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatTextView
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.alvin.mvvm.base.fragment.BaseListFragment
import com.alvin.mvvm_framework.R
import com.alvin.mvvm_framework.databinding.FragmentTvListBinding
import com.alvin.mvvm_framework.model.entity.QQMVListEntity
import com.alvin.mvvm_framework.ui.fragment.tv.adapter.TVListAdapter
import com.alvin.mvvm_framework.viewmodel.fragment.tv.TVListViewModel
import com.blankj.utilcode.util.KeyboardUtils
import com.chad.library.adapter.base.BaseQuickAdapter


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
        dataBinding.viewModel = viewModel
        ibBarBack.visibility = View.GONE
        setTitleName("TV")
        // 显示加载内容
        showLoadingLayout()
        initAdapter()
        dataBinding.editMsg.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                KeyboardUtils.hideSoftInput(activity)
                rootRefresh.autoRefresh()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    override fun lazyLoadData() {
        // TODO 默认搜索内容
        dataBinding.editMsg.setText("漂移")
        loadData()
    }

    override fun loadData() {
        viewModel.searchQQMV(dataBinding.editMsg.text.toString())
    }

    /**
     * 本地测试数据
     */
    private fun initAdapter() {
        // 设置 Adapter 动画
        adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.AlphaIn)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as QQMVListEntity
            val bundle = Bundle()
            bundle.putString("id", item.id)
            bundle.putString("msg", dataBinding.editMsg.text.toString())
            bundle.putString("song", item.song)
            Navigation.findNavController(requireView())
                .navigate(R.id.action_mainFragment_to_TVDetailFragment,bundle)
        }
    }

    override fun reload() {
        super.reload()
        hideErrorLayout()
    }

    override fun noNetClickId(): Int {
        return R.id.btnRetry
    }

    override fun onFailed(errorMsg: String?) {
        super.onFailed(errorMsg)
        showErrorLayout()
        errorLayout.findViewById<AppCompatTextView>(R.id.tvErrorContent).text = errorMsg
    }

    override fun isErrorToast(): Boolean {
        return false
    }


    override fun registerObserver() {
        viewModel.qqMVList.observe(this) {
            rootRefresh.finish(it.toMutableList(), adapter)
        }
    }
}