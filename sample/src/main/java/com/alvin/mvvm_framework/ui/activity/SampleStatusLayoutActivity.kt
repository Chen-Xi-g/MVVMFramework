package com.alvin.mvvm_framework.ui.activity

import android.os.Bundle
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.alvin.mvvm.base.activity.BaseMVVMActivity
import com.alvin.mvvm_framework.R
import com.alvin.mvvm_framework.databinding.SampleActivityStatusLayoutBinding
import com.alvin.mvvm_framework.view_model.activity.SampleStatusLayoutViewModel
import kotlinx.coroutines.*

/**
 * <h3> 作用类描述：多状态布局</h3>
 *
 * @Package :        com.alvin.mvvm_framework.ui.activity
 * @Date :           2022-03-04
 * @author
 *
 */
class SampleStatusLayoutActivity :
    BaseMVVMActivity<SampleStatusLayoutViewModel, SampleActivityStatusLayoutBinding>(R.layout.sample_activity_status_layout) {

    /**
     * 显示状态布局
     */
    private val statusDialog by lazy(LazyThreadSafetyMode.NONE) {
        MaterialDialog(this)
    }

    private val items by lazy(LazyThreadSafetyMode.NONE) {
        listOf("成功布局", "加载中布局", "失败布局", "显示加载Dialog")
    }

    /**
     * 在initView中执行View初始化的任务，不要做逻辑的处理
     */
    override fun initView(savedInstanceState: Bundle?) {
        setTitleName("多状态布局")
        ibBarRight.visibility = View.VISIBLE
        ibBarRight.setOnClickListener {
            statusDialog.show {
                val singleChoice = listItemsSingleChoice(
                    items = items,
                    initialSelection = 0
                ) { dialog, index, text ->
                    when (index) {
                        0 -> {
                            // 隐藏加载失败布局,显示成功布局
                            hideErrorLayout()
                        }
                        1 -> {
                            // 显示加载中布局
                            showLoadingLayout()
                        }
                        2 -> {
                            // 显示加载失败布局
                            showErrorLayout()
                        }
                        3 -> {
                            val scope = CoroutineScope(Job() + Dispatchers.Main)
                            scope.launch {
                                // 模拟加载操作
                                loading("正在加载")
                                delay(2000)
                                dismissLoading(false)
                            }
                        }
                    }
                    dialog.dismiss()
                }
            }
        }
    }

    /**
     * 失败布局, 点击重新加载
     */
    override fun reload() {
        super.reload()
        showLoadingLayout()
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