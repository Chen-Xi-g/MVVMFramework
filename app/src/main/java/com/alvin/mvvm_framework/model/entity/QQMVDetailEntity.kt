package com.alvin.mvvm_framework.model.entity

/**
 * <h3> 作用类描述：QQMV 详情</h3>
 *
 * @Package :        com.alvin.mvvm_framework.model.entity
 * @Date :           2022/1/8
 * @author 高国峰
 *
 * @property img 封面图
 * @property link MV链接
 * @property mvtitle MV标题
 * @property singer 歌手
 */
data class QQMVDetailEntity(
    val img: String,
    val link: String,
    val mvtitle: String,
    val singer: String
)