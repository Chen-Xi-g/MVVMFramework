package com.alvin.mvvm.base.adapter

import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * <h3> 作用类描述：使用DataBinding初始化Adapter</h3>
 *
 * @Package :        com.yleanlink.mvp.adapter
 * @Date :           2021/9/14-14:57
 * @author Alvin
 */
abstract class BaseBindingListAdapter<T, DB : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int
) : BaseQuickAdapter<T, BaseViewHolder>(layoutResId) {

    abstract fun convert(holder: BaseViewHolder, item: T, dataBinding: DB?)

    override fun convert(holder: BaseViewHolder, item: T) {
        val binding = DataBindingUtil.bind<DB>(holder.itemView)
        convert(holder, item, binding)
        binding?.executePendingBindings()
    }
}