package com.alvin.mvvm.base.activity

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.alvin.mvvm.base.view_model.BaseViewModel

/**
 * <h3> 作用类描述：所有Activity最终需要继承的MVVM类</h3>
 *
 * @Package :        com.alvin.mvvm.base.activity
 * @Date :           2021/12/18
 * @author Alvin
 *
 * @property VM 继承 BaseViewModel 的 ViewModel
 * @property DB 自动生成的 ViewDataBinding
 * @param layoutRes 布局ID
 * @param isGoneContent false = 显示内容布局,不显示加载布局
 *                      true = 显示加载布局,网络请求成功后自动显示内容布局
 */
abstract class BaseMVVMActivity<VM : BaseViewModel, DB : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int,
    private val isGoneContent: Boolean = false
) : BaseVMActivity<VM>() {
    lateinit var dataBinding: DB

    override fun initDataBinding() {
        super.initDataBinding()
        // 加载内容布局是否显示
        if (isGoneContent) {
            showLoadingLayout()
        } else {
            hideLoadingLayout()
        }
        dataBinding = putContentView(layoutRes)
        dataBinding.lifecycleOwner = this
    }

}