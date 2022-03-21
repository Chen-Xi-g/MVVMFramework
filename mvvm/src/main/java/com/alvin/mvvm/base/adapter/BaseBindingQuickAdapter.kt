/*
 * Copyright 2022 高国峰
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alvin.mvvm.base.adapter

import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * <h3> 作用类描述：使用DataBinding初始化Adapter</h3>
 *
 * @Package :        com.alvin.mvp.adapter
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
    }
}