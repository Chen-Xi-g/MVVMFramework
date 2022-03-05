package com.alvin.mvvm_framework.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.alvin.mvvm.base.fragment.BaseMVVMFragment
import com.alvin.mvvm_framework.R
import com.alvin.mvvm_framework.databinding.SampleFragmentHomeActivityBinding
import com.alvin.mvvm_framework.view_model.activity.SampleMainClickViewModel
import com.alvin.mvvm_framework.view_model.fragment.SampleHomeActivityViewModel

/**
 * <h3> 作用类描述：演示Activity功能的Fragment容器</h3>
 *
 * @Package :        com.alvin.mvvm_framework.ui.fragment
 * @Date :           2022-03-05
 * @author
 */
class SampleHomeActivityFragment :
    BaseMVVMFragment<SampleHomeActivityViewModel, SampleFragmentHomeActivityBinding>(R.layout.sample_fragment_home_activity) {

    /**
     * 管理点击事件的 ViewModel
     */
    private val clickViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this)[SampleMainClickViewModel::class.java]
    }

    /**
     * 在initView中执行View初始化的任务，不要做逻辑的处理
     */
    override fun initView(view: View, savedInstanceState: Bundle?) {
        dataBinding.clickViewModel = clickViewModel
    }

    /**
     * Fragment懒加载数据
     */
    override fun lazyLoadData() {
    }

    /**
     * 初始化LiveData数据观察者
     */
    override fun registerObserver() {
    }

    /**
     * 在obtainData中做逻辑处理，不要做View初始化
     */
    override fun obtainData() {
    }

    /**
     * @return Boolean false 不显示标题布局
     */
    override fun titleLayoutView(): Boolean {
        return false
    }

}