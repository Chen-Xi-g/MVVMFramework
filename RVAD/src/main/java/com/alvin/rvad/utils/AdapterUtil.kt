package com.alvin.rvad.utils

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.alvin.rvad.ReuseAdapter
import com.alvin.rvad.layout_manager.StickyGridLayoutManager
import com.alvin.rvad.layout_manager.StickyLinearLayoutManager
import com.alvin.rvad.layout_manager.StickyStaggeredGridLayoutManager

/**
 * <h3> 作用类描述：适配器工具类，扩展函数使用</h3>
 *
 * @Package :        com.alvin.rvad.utils
 * @Date :           2022/7/26
 * @author 高国峰
 */


/**
 * 获取binding适配器
 */
val RecyclerView.reuseAdapter
    get() = adapter as? ReuseAdapter
        ?: throw NullPointerException("RecyclerView has no BindingAdapter")

/**
 * 获取适配器数据集
 */
val RecyclerView.data get() = reuseAdapter.list

/**
 * 添加数据
 *
 * @receiver RecyclerView
 * @param data List<*> 数据集
 * @param index Int 从指定索引添加
 */
fun RecyclerView.addData(data: List<*>, index: Int = -1) {
    reuseAdapter.addData(data, index)
}

/**
 * 添加数据
 *
 * @receiver RecyclerView
 * @param item 数据源
 * @param index Int 从指定索引添加
 */
fun RecyclerView.addData(item: Any, index: Int = -1) {
    reuseAdapter.addData(item, index)
}

/**
 * 删除指定索引数据
 *
 * @receiver RecyclerView
 * @param index Int
 */
fun RecyclerView.removeAt(index: Int) {
    reuseAdapter.removeAt(index)
}

/**
 * 设置数据集
 *
 * @receiver RecyclerView
 * @param data List<*> 数据集
 */
fun RecyclerView.setData(data: List<*>) {
    reuseAdapter.setData(data)
}

/**
 * 设置数据集
 *
 * @receiver RecyclerView
 * @param position Int 数据集索引
 * @param data List<*> 数据集
 */
fun RecyclerView.setData(position: Int, data: Any) {
    reuseAdapter.setData(position, data)
}

/**
 * 快速设置Adapter
 *
 * @receiver RecyclerView
 * @param block [@kotlin.ExtensionFunctionType] Function2<ReuseAdapter, RecyclerView, Unit>
 */
fun RecyclerView.setup(
    block: ReuseAdapter.(RecyclerView) -> Unit
): ReuseAdapter {
    val adapter = ReuseAdapter()
    adapter.block(this)
    this.adapter = adapter
    return adapter
}

/*==============布局管理=================*/

/**
 *
 * @receiver RecyclerView
 * @param orientation Int 滑动方向 [RecyclerView.VERTICAL] or [RecyclerView.HORIZONTAL]
 * @param scrollEnabled Boolean 是否允许滚动
 * @param reverseLayout 是否反转列表
 * @param scrollEnabled 是否允许滚动
 */
fun RecyclerView.linear(
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL,
    reverseLayout: Boolean = false,
    scrollEnabled: Boolean = true,
    stackFromEnd: Boolean = false
): RecyclerView {
    layoutManager = StickyLinearLayoutManager(context, orientation, reverseLayout).apply {
        setScrollEnabled(scrollEnabled)
        this.stackFromEnd = stackFromEnd
    }
    // 避免刷新闪烁
    val animator: RecyclerView.ItemAnimator = itemAnimator ?: return this
    if (animator is SimpleItemAnimator) {
        animator.supportsChangeAnimations = false
    }
    return this
}

/**
 * @receiver RecyclerView
 * @param spanCount 网格跨度数量
 * @param orientation 列表方向
 * @param reverseLayout 是否反转
 * @param scrollEnabled 是否允许滚动
 */
fun RecyclerView.grid(
    spanCount: Int = -1,
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL,
    reverseLayout: Boolean = false,
    scrollEnabled: Boolean = true
): RecyclerView {
    layoutManager = StickyGridLayoutManager(context, spanCount, orientation, reverseLayout).apply {
        setScrollEnabled(scrollEnabled)
    }
    // 避免刷新闪烁
    val animator: RecyclerView.ItemAnimator = itemAnimator ?: return this
    if (animator is SimpleItemAnimator) {
        animator.supportsChangeAnimations = false
    }
    return this
}

/**
 * @receiver RecyclerView
 * @param spanCount 网格跨度数量
 * @param orientation 列表方向
 * @param reverseLayout 是否反转
 * @param scrollEnabled 是否允许滚动
 */
fun RecyclerView.staggered(
    spanCount: Int,
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL,
    reverseLayout: Boolean = false,
    scrollEnabled: Boolean = true
): RecyclerView {
    layoutManager = StickyStaggeredGridLayoutManager(spanCount, orientation).apply {
        setScrollEnabled(scrollEnabled)
        this.reverseLayout = reverseLayout
    }
    // 避免刷新闪烁
    val animator: RecyclerView.ItemAnimator = itemAnimator ?: return this
    if (animator is SimpleItemAnimator) {
        animator.supportsChangeAnimations = false
    }
    return this
}