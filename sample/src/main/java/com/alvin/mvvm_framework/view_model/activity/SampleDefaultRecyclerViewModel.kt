package com.alvin.mvvm_framework.view_model.activity

import com.alvin.mvvm.base.livedata.EventLiveData
import com.alvin.mvvm.base.view_model.BaseViewModel
import com.alvin.mvvm_framework.api.SampleApi
import com.alvin.mvvm_framework.base.http.request
import com.alvin.mvvm_framework.model.ArticleEntity
import com.alvin.mvvm_framework.model.BaseEntity
import com.alvin.mvvm_framework.model.BaseListEntity
import com.blankj.utilcode.util.ToastUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * <h3> 作用类描述：[com.alvin.mvvm_framework.ui.activity]的ViewModel</h3>
 *
 * ViewModel内部已经和生命周期绑定，无需关心与View的生命周期绑定问题。
 * 如果需要释放资源，请重写`onCleared()`释放。
 *
 * @Package :        com.alvin.mvvm_framework.view_model.activity
 * @Date :           2022-03-05
 * @author
 */
class SampleDefaultRecyclerViewModel : BaseViewModel() {

    /**
     * 获取文章数据
     */
    private val _articleListData by lazy(LazyThreadSafetyMode.NONE) {
        EventLiveData<List<ArticleEntity>>()
    }
    val articleListData: EventLiveData<List<ArticleEntity>> = _articleListData

    /**
     * 加载列表数据
     */
    fun getArticleListData(page: Int, pageSize: Int) {
        request(
            {
                filterArticleList(page, pageSize)
            }, {
                it?.let {
                    _articleListData.postValue(it.datas)
                }
            }, {
                // 额外的异常操作
                ToastUtils.showLong(it.errorLog)
            }
        )
    }

    /**
     * 加载自定义列表数据
     */
    fun getArticleListData(page: Int, pageSize: Int, loadMsg: String?) {
        request(
            {
                filterArticleList(page, pageSize)
            }, {
                it?.let {
                    _articleListData.postValue(it.datas)
                }
            }, loadingMessage = loadMsg // 不显示加载中布局, 显示加载框加载数据
        )
    }

    /**
     * 合并请求内容
     *
     * @return BaseEntity<List<ArticleEntity>>
     */
    private suspend fun filterArticleList(
        page: Int,
        pageSize: Int
    ): BaseEntity<BaseListEntity<ArticleEntity>> {
        return withContext(Dispatchers.IO) {
            // 模拟耗时网络请求
            delay(2000)
            val listArticle = async { SampleApi.service.listArticle(page, pageSize) }
            // 第一页数据, 合并置顶文章
            if (page == 0) {
                val topArticle = async { SampleApi.service.topArticle() }
                val topList = topArticle.await().data
                if (!topList.isNullOrEmpty()) {
                    listArticle.await().data?.datas?.addAll(0, topList)
                }
            }
            listArticle.await()
        }
    }

}