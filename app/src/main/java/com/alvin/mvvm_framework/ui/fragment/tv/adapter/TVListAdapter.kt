package com.alvin.mvvm_framework.ui.fragment.tv.adapter

import com.alvin.mvvm.base.adapter.BaseBindingListAdapter
import com.alvin.mvvm_framework.R
import com.alvin.mvvm_framework.databinding.AdapterMusicListBinding
import com.alvin.mvvm_framework.databinding.AdapterTvListBinding
import com.alvin.mvvm_framework.model.entity.QQMVListEntity
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * <h3> 作用类描述：TV列表适配器</h3>
 *
 * @Package :        com.alvin.mvvm_framework.ui.fragment.tv.adapter
 * @Date :           2022/1/8
 * @author 高国峰
 */
class TVListAdapter : BaseBindingListAdapter<QQMVListEntity, AdapterTvListBinding>(R.layout.adapter_tv_list) {
    override fun convert(
        holder: BaseViewHolder,
        item: QQMVListEntity,
        dataBinding: AdapterTvListBinding?
    ) {
        dataBinding?.entity = item
    }
}