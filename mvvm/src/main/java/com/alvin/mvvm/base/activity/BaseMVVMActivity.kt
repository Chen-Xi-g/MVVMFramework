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
 * @property layoutRes 布局ID
 */
abstract class BaseMVVMActivity<VM : BaseViewModel, DB : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int
) : BaseVMActivity<VM>() {
    lateinit var dataBinding: DB

    override fun initDataBinding() {
        super.initDataBinding()
        dataBinding = putContentView(layoutRes)
        dataBinding.lifecycleOwner = this
    }
}