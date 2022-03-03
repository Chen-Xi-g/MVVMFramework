package com.alvin.mvvm_framework.model.ui.activity

import android.os.Bundle
import com.alvin.mvvm.base.activity.BaseMVVMActivity
import com.alvin.mvvm_framework.R
import com.alvin.mvvm_framework.databinding.SampleActivityMainBinding

import com.alvin.mvvm_framework.model.view_model.activity.SampleMainViewModel

/**
 * <h3> 作用类描述：Main </h3>
 *
 * @Package :        com.alvin.mvvm_framework.model.ui.activity
 * @Date :           2022-03-03
 * @author
 *
 */
class SampleMainActivity :
    BaseMVVMActivity<SampleMainViewModel, SampleActivityMainBinding>(R.layout.sample_activity_main) {

    /**
     * 在initView中执行View初始化的任务，不要做逻辑的处理
     */
    override fun initView(savedInstanceState: Bundle?) {
        // 如果当前页面需要自动接受路由传递的参数，取消下面注释的代码
//        ARouter.getInstance().inject(this)
    }

    /**
     * 在obtainData中做逻辑处理，不要做View初始化
     */
    override fun obtainData() {
    }

    /**
     * 初始化LiveData数据观察者
     */
    override fun registerObserver() {
    }


}