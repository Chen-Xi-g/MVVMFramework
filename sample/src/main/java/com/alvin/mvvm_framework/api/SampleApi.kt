package com.alvin.mvvm_framework.api

import com.alvin.mvvm_framework.model.ArticleEntity
import com.alvin.mvvm_framework.model.BaseEntity
import com.alvin.mvvm_framework.model.BaseListEntity
import com.alvin.mvvm_network.HttpManager
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * <h3> 作用类描述：样本程序接口</h3>
 *
 * @Package :        com.alvin.mvvm_framework.api
 * @Date :           2022/3/5
 * @author 高国峰
 */
interface SampleApi {

    companion object {
        val service = HttpManager.instance.instanceRetrofit(SampleApi::class.java)
    }

    /**
     * 获取置顶文章
     */
    @GET("/article/top/json")
    suspend fun topArticle(): BaseEntity<List<ArticleEntity>>

    /**
     * 获取文章列表
     */
    @GET("/article/list/{page}/json")
    suspend fun listArticle(
        @Path("page") page: Int,
        @Query("page_size") pageSize: Int
    ): BaseEntity<BaseListEntity<ArticleEntity>>
}