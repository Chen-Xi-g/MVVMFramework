package com.alvin.rvad.mode

/**
 * <h3> 作用类描述：单选 or 多选 的实体类接口,返回当前选择状态 </h3>
 *
 * @Package :        com.alvin.rvad.mode
 * @Date :           2022/7/24
 * @author 高国峰
 */
interface ICheckedEntity {

    /**
     * 是否已选择
     */
    var isSelected: Boolean

}