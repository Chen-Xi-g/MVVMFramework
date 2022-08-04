package com.alvin.rvad.type

/**
 * <h3> 作用类描述：</h3>
 *
 * @Package :        com.alvin.rvad.type
 * @Date :           2022/7/29
 * @author 高国峰
 */
interface ItemExpand {

    // 同级别分组的索引位置
    var itemGroupPosition: Int

    /** 是否需要展开分组 */
    var itemExpand: Boolean

    /** 子列表 */
    var itemChildList: List<Any?>?
}