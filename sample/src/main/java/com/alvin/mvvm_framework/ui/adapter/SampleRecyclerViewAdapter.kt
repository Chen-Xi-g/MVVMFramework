package com.alvin.mvvm_framework.ui.adapter

import com.alvin.mvvm.base.adapter.BaseBindingListAdapter
import com.alvin.mvvm_framework.R
import com.alvin.mvvm_framework.databinding.SampleItemArticleBinding
import com.alvin.mvvm_framework.model.ArticleEntity
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * <h3> 作用类描述：列表适配器</h3>
 *
 * @Package :        com.alvin.mvvm_framework.ui.adapter
 * @Date :           2022/3/5
 * @author 高国峰
 */
class SampleRecyclerViewAdapter :
    BaseBindingListAdapter<ArticleEntity, SampleItemArticleBinding>(R.layout.sample_item_article) {
    override fun convert(
        holder: BaseViewHolder,
        item: ArticleEntity,
        dataBinding: SampleItemArticleBinding?
    ) {
        dataBinding?.item = item
    }
}