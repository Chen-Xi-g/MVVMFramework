package com.alvin.mvvm_framework.data

import com.alvin.mvvm_framework.model.entity.QQMVDetailEntity
import com.alvin.mvvm_framework.model.entity.QQMVListEntity
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * <h3> 作用类描述：接口</h3>
 *
 * @Package :        com.alvin.mvvm_framework.data
 * @Date :           2022/1/8
 * @author 高国峰
 */
interface AppService {

    /**
     * 获取qqMV 列表
     *
     * @param msg 搜索内容
     */
    @GET("/qqmv/")
    suspend fun searchQQMV(@Query("msg") msg: String): List<QQMVListEntity>

    /**
     * 获取MV详情
     */
    @GET("/qqmv/")
    suspend fun detailMV(@Query("msg") msg: String, @Query("n") id: String): QQMVDetailEntity

}