package com.alvin.mvvm.base.fragment

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.alvin.mvvm.base.view_model.BaseViewModel

/**
 * <h3> 作用类描述：ViewModelFragment基类，自动把ViewModel注入Fragment和Databind注入进来了. </h3>
 *
 * @Package :        com.alvin.mvvm.base.fragment
 * @Date :           2021/12/22-15:52
 * @author Alvin
 * @param layoutRes 布局ID
 * @param isGoneContent false = 显示内容布局,不显示加载布局
 *                      true = 显示加载布局,网络请求成功后自动显示内容布局
 */
abstract class BaseMVVMFragment<VM : BaseViewModel, DB : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int,
    private val isGoneContent: Boolean = false
) : BaseVMFragment<VM>() {

    lateinit var dataBinding: DB

    override fun initDataBinding() {
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