package com.alvin.mvvm_framework.ui.fragment.music.adapter

import com.alvin.mvvm.base.adapter.BaseBindingListAdapter
import com.alvin.mvvm_framework.R
import com.alvin.mvvm_framework.databinding.AdapterMusicListBinding
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * <h3> 作用类描述：Music列表适配器</h3>
 *
 * @Package :        com.alvin.mvvm_framework.ui.fragment.music.adapter
 * @Date :           2022/1/7
 * @author 高国峰
 */
class MusicListAdapter: BaseBindingListAdapter<String, AdapterMusicListBinding>(R.layout.adapter_music_list) {
    override fun convert(
        holder: BaseViewHolder,
        item: String,
        dataBinding: AdapterMusicListBinding?
    ) {
        dataBinding?.data = item
    }

}