package com.alvin.mvvm_framework.model.entity

/**
 * <h3> 作用类描述：QQMV 列表</h3>
 *
 * @Package :        com.alvin.mvvm_framework.model.entity
 * @Date :           2022/1/8
 * @author 高国峰
 *
 * @property id 序号
 * @property song MV名称
 * @property singer 歌手
 * @property value 类型
 */
data class QQMVListEntity(
    val id: String,
    val song: String,
    val singer: String,
    val value: String
)