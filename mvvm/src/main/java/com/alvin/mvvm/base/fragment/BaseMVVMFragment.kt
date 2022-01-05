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
 */
abstract class BaseMVVMFragment<VM : BaseViewModel, DB : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int
) : BaseVMFragment<VM>() {

    lateinit var dataBinding: DB

    override fun initDataBinding() {
        dataBinding = putContentView(layoutRes)
        dataBinding.lifecycleOwner = this
    }
}