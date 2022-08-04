package com.alvin.rvad

import android.annotation.SuppressLint
import android.util.NoSuchPropertyException
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alvin.rvad.listeners.ViewSetup
import com.alvin.rvad.mode.ICheckedEntity
import com.alvin.rvad.mode.SelectSealed
import com.alvin.rvad.type.ItemExpand
import com.alvin.rvad.type.ItemSticky
import java.util.concurrent.atomic.AtomicLong

/**
 * <h3> 作用类描述：RecyclerView 可重复利用的适配器</h3>
 *
 * @Package :        com.alvin.rvad
 * @Date :           2022/7/24
 * @author 高国峰
 */
@SuppressLint("NotifyDataSetChanged")
open class ReuseAdapter : RecyclerView.Adapter<ReuseAdapter.BaseViewHolder>() {

    var rv: RecyclerView? = null

    /**
     * 模型数据数据
     */
    private var _list: MutableList<Any?> = mutableListOf()
    val list = _list

    /**
     * 回调
     */
    // onBindViewHolder回调
    private var _onBind: (BaseViewHolder.() -> Unit)? = null

    // 点击事件回调
    private var _onItemClick: (BaseViewHolder.(view: View) -> Unit)? =
        null

    // 长按事件回调
    private var _onItemLongClick: (BaseViewHolder.(view: View) -> Unit)? =
        null

    // 单选多选回调
    private var onChecked: ((position: Int, checked: Boolean, isAll: Boolean) -> Unit)? =
        null

    // 子Item点击事件回调集合, key为子Item的id, value为回调
    private val clickListeners = mutableMapOf<Int, (BaseViewHolder.(viewId: Int) -> Unit)>()

    // 子Item长按事件回调集合, key为子Item的id, value为回调
    private val longClickListeners = mutableMapOf<Int, (BaseViewHolder.(viewId: Int) -> Unit)>()

    /**
     * 添加指定类型布局,适配多布局.
     *
     * 实体类.(索引) -> 布局ID
     *  Any.(Int) -> Int
     */
    var typeLayouts = mutableMapOf<Class<*>, Any.(Int) -> Int>()

    /**
     * 添加头布局,适配多布局.
     *
     * 头布局.(索引) -> 布局ID
     *  Any.(Int) -> Int
     */
    var headerList = mutableListOf<Any?>()

    /**
     * 添加尾布局,适配多布局.
     */
    var footerList = mutableListOf<Any?>()

    /**
     * 获取头布局数量
     */
    val headerCount get() = headerList.size

    /**
     * 获取尾布局数量
     */
    val footerCount get() = footerList.size

    /**
     * 是否需要为点击事件设置防抖,默认为true.
     */
    var shakeEnable = true

    /**
     * 已选择条目的Position
     */
    val checkedPosition = mutableListOf<Int>()

    /**
     * 当前选择模式
     * 默认选择模式为 [SelectSealed.None] 什么都不做.
     * 可以项:
     * [SelectSealed.Single] 单选
     * [SelectSealed.Single] 多选
     */
    var selectModel: SelectSealed = SelectSealed.None

    /** 已选择条目数量 */
    val checkedCount: Int get() = checkedPosition.size

    override fun getItemViewType(position: Int): Int {
        val item = getData<Any>(position)
        return typeLayouts[item.javaClass]?.invoke(item, position) ?: throw NoSuchPropertyException(
            "没有找到该类型的布局"
        )
    }

    /**
     * 创建ViewHolder
     *
     * @param parent ViewGroup
     * @param viewType Int 这里返回布局ID
     * @return BaseViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(getData(position))
    }

    override fun getItemCount(): Int {
        return _list.size + headerCount + footerCount
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        rv = recyclerView
    }

    /**
     * [onBindViewHolder] 回调
     *
     * @param onBind [@kotlin.ExtensionFunctionType] Function1<BaseViewHolder, Unit>
     */
    fun onBind(onBind: (BaseViewHolder.() -> Unit)) {
        _onBind = onBind
    }

    /**
     * 为Recyclerview添加ItemView的点击事件回调
     */
    fun onItemClick(onItemClick: (BaseViewHolder.(view: View) -> Unit)) {
        _onItemClick = onItemClick
    }

    /**
     * 为Recyclerview添加ItemView的长按事件回调
     */
    fun onItemLongClick(onItemLongClick: (BaseViewHolder.(view: View) -> Unit)) {
        _onItemLongClick = onItemLongClick
    }


    /**
     * 选择回调
     */
    fun onChecked(block: (position: Int, checked: Boolean, isAll: Boolean) -> Unit) {
        onChecked = block
    }

    /**
     * 监听悬停
     */
    var onStickyAttachListener: ViewSetup? = null

    /**
     * 为Recyclerview添加ItemView的子View的点击事件回调
     */
    fun addOnItemChildClickListener(
        @IdRes vararg id: Int,
        onItemChildClick: (BaseViewHolder.(viewId: Int) -> Unit)
    ) {
        id.forEach {
            clickListeners[it] = onItemChildClick
        }
    }

    /**
     * 为Recyclerview添加ItemView的子View的长按事件回调
     */
    fun addOnItemChildLongClickListener(
        @IdRes vararg id: Int,
        onItemChildLongClick: (BaseViewHolder.(viewId: Int) -> Unit)
    ) {
        id.forEach {
            longClickListeners[it] = onItemChildLongClick
        }
    }

    /**
     * 全选或取消全选
     * 单选模式下不可全选, 但可以取消单选
     * @param checked true为全选, false 取消全部选择
     */
    fun checkedAll(checked: Boolean = true) {
        if (isCheck()) return
        if (checked) {
            // 全选
            if (selectModel is SelectSealed.Single) return
            _list.forEachIndexed { index, t ->
                if (!checkedPosition.contains(index)) {
                    setChecked(index, true)
                }
            }
        } else {
            // 取消全选
            _list.forEachIndexed { index, t ->
                if (checkedPosition.contains(index)) setChecked(index, false)
            }
        }
    }

    /**
     * 设置指定索引选中状态
     *
     * @param position 当前索引
     * @param checked true: 选中,  false: 取消选中
     */
    fun setChecked(position: Int, checked: Boolean) {
        // 避免冗余操作
        if ((checkedPosition.contains(position) && checked) ||
            (!checked && !checkedPosition.contains(position)) || isCheck()
        ) return

        // 实体类没有实现 ICheckedEntity 接口,直接return
        val item = getData<Any>(position)
        if (item !is ICheckedEntity) return

        if (checked) checkedPosition.add(position)
        else checkedPosition.remove(position)

        if (selectModel is SelectSealed.Single && checked && checkedPosition.size > 1) {
            // 当前模式为单选, 使用递归取消选择
            setChecked(checkedPosition[0], false)
        }
        // 修改选择状态
        item.isSelected = checked
        // 选择回调
        onChecked?.invoke(position, checked, isCheckedAll())
        notifyItemChanged(position)
    }

    /**
     * 切换指定索引选中状态
     */
    fun checkedSwitch(position: Int) {
        if (isCheck()) return
        if (checkedPosition.contains(position)) {
            setChecked(position, false)
        } else {
            setChecked(position, true)
        }
    }

    /**
     * 判断是否需要选中
     *
     * @return true: 需要选中, false: 不需要选中
     */
    private fun isCheck() = onChecked != null && selectModel !is SelectSealed.None

    /**
     * 添加头布局
     *
     * 头布局本质是就是多布局, 所以也需要像[addType]添加.
     *
     * @param header 头布局实体类
     * @param position 添加到第几个头布局, 如果为负数或大于头布局数量，则添加到最后一个位置。
     * @param isScrollTop 是否滑动到顶部
     */
    fun addHeader(header: Any, position: Int = -1, isScrollTop: Boolean = false) {
        if (position == -1 || position >= headerCount) {
            headerList.add(header)
            notifyItemInserted(headerCount)
        } else {
            headerList.add(position, header)
            notifyItemInserted(position)
        }
        if (isScrollTop) {
            scrollRv(0)
        }
    }

    /**
     * 设置旧的头布局为新的头布局
     *
     * @param oldHeader 旧头布局
     * @param newHeader 新头布局
     * @param isScrollTop 是否滑动到顶部
     */
    fun setHeader(oldHeader: Any, newHeader: Any, isScrollTop: Boolean = false) {
        headerList[headerList.indexOf(oldHeader)] = newHeader
        notifyItemChanged(headerList.indexOf(oldHeader))
        if (isScrollTop) {
            scrollRv(0)
        }
    }

    /**
     * 设置指定头布局索引为新的头布局
     * @param position 头布局索引
     * @param newHeader 新头布局
     * @param isScrollTop 是否滑动到顶部
     */
    fun setHeader(position: Int, newHeader: Any, isScrollTop: Boolean = false) {
        headerList[position] = newHeader
        notifyItemChanged(position)
        if (isScrollTop) {
            scrollRv(0)
        }
    }

    /**
     * 通过指定类型删除头布局
     */
    fun removeHeader(type: Any) {
        if (headerList.contains(type)) {
            headerList.forEachIndexed { index, any ->
                if (any == type) {
                    headerList.removeAt(index)
                    notifyItemRemoved(index)
                }
            }
        }
    }

    /**
     * 通过指定索引删除头布局
     */
    fun removeHeader(position: Int) {
        if (position >= headerCount) return
        headerList.removeAt(position)
        notifyItemRemoved(position)
    }

    /**
     * 删除所有头布局
     */
    fun removeHeaderAll() {
        if (headerCount > 0) {
            headerList.clear()
            notifyItemRangeRemoved(0, headerCount)
        }
    }

    /**
     * 判断指定索引是否为头布局
     *
     * @param position Int 索引
     * @return Boolean true: 是头布局, false: 不是头布局
     */
    fun isHeader(position: Int): Boolean = (position < headerCount && headerCount > 0)

    /**
     * 添加尾布局
     *
     * 尾布局本质是就是多布局, 所以也需要像[addType]添加.
     *
     * @param footer 尾布局实体类
     * @param position 添加到第几个尾布局, 如果为负数或大于尾布局数量，则添加到最后一个位置。
     * @param isScrollBottom 是否滑动到底部
     */
    fun addFooter(footer: Any, position: Int = -1, isScrollBottom: Boolean = false) {
        if (position == -1 || position >= footerCount) {
            footerList.add(footer)
            notifyItemInserted(itemCount)
        } else {
            footerList.add(position, footer)
            notifyItemInserted(itemCount)
        }
        if (isScrollBottom) {
            scrollRv(itemCount - 1)
        }
    }

    /**
     * 替换指定类型尾布局
     *
     * @param oldFooter 旧的尾部局
     * @param newFooter 新的尾部局
     * @param isScrollBottom 是否滑动到底部
     */
    fun setFooter(oldFooter: Any, newFooter: Any, isScrollBottom: Boolean = false) {
        footerList[footerList.indexOf(oldFooter)] = newFooter
        notifyItemChanged(itemCount)
        if (isScrollBottom) {
            scrollRv(itemCount - 1)
        }
    }

    /**
     * 通过指定类型删除尾布局
     *
     * @param type Any 类型
     */
    fun removeFooter(type: Any) {
        if (footerList.contains(type)) {
            footerList.forEachIndexed { index, any ->
                if (any == type) {
                    footerList.removeAt(index)
                    notifyItemRemoved(headerCount + _list.size + index)
                }
            }
        }
    }

    /**
     * 通过指定索引删除尾布局
     *
     * @param position Int 索引
     */
    fun removeFooter(position: Int) {
        if (position >= footerCount) return
        footerList.removeAt(position)
        notifyItemRemoved(headerCount + _list.size + position)
    }

    /**
     * 删除所有尾布局
     */
    fun removeFooterAll() {
        if (footerCount > 0) {
            footerList.clear()
            notifyItemRangeRemoved(headerCount + _list.size, footerCount)
        }
    }

    /**
     * 判断指定索引是否为尾布局
     *
     * @param position Int 索引
     * @return Boolean true: 是尾布局, false: 不是尾布局
     */
    fun isFooter(position: Int): Boolean =
        (position >= headerCount + _list.size && footerCount > 0 && position < itemCount)

    /**
     * RecyclerView滑动到指定位置
     */
    fun scrollRv(lastIndex: Int) {
        rv?.let { rv ->
            rv.scrollToPosition(lastIndex)
            (rv.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(
                lastIndex,
                0
            )
        }
    }

    /**
     * 添加指定类型布局, 这里的泛型类型需要和Model类型一致, 否则无法找到向右布局
     *
     * @param layoutRes Int
     */
    inline fun <reified T> addType(@LayoutRes layoutRes: Int) {
        typeLayouts[T::class.java] = { layoutRes }
    }

    /**
     * 添加指定类型布局, 这里的泛型类型需要和Model类型一致, 否则无法找到向右布局
     *
     * 实体类.(索引) 返回 布局ID
     */
    inline fun <reified T> addType(noinline block: T.(Int) -> Int) {
        typeLayouts[T::class.java] = block as Any.(Int) -> Int
    }

    /**
     * 根据索引获取模型数据
     */
    fun <T> getData(index: Int): T {
        return if (isHeader(index)) {
            headerList[index] as T
        } else if (isFooter(index)) {
            footerList[index - headerCount - _list.size] as T
        } else {
            _list[index - headerCount] as T
        }
    }

    /**
     * 根据指定索引返回数据模型,如果没有则返回null
     * @param index Int
     * @return T
     */
    fun <T> getDataOrNull(index: Int): T? {
        return if (isHeader(index)) {
            headerList[index] as? T
        } else if (isFooter(index)) {
            footerList[index - headerCount - _list.size] as? T
        } else {
            _list.getOrNull(index - headerCount) as? T
        }
    }

    /**
     * 设置数据
     *
     * @param list List<*> 数据源
     */
    fun setData(list: List<Any?>?) {
        _list.clear()
        if (!list.isNullOrEmpty()) {
            _list.addAll(list)
        }
        notifyDataSetChanged()
    }

    /**
     * 更新指定索引数据
     *
     * @param index Int 索引
     * @param item 数据源
     */
    fun setData(index: Int, item: Any) {
        if (index < _list.size) {
            _list[index] = item
            notifyItemChanged(index)
            isRefresh(0)
        }
    }

    /**
     * 添加数据
     * @param data List<Any?>?
     * @param index Int 从指定索引添加数据
     */
    fun addData(data: List<Any?>, index: Int = -1) {
        if (index < _list.size) {
            if (index == -1) {
                _list.addAll(data)
                notifyItemRangeInserted(_list.size - data.size, data.size)
            } else {
                _list.addAll(index, data)
                notifyItemRangeInserted(index, data.size)
            }
            isRefresh(data.size)
        }
    }

    /**
     * 添加数据
     * @param item Any 数据源
     * @param index Int 从指定索引添加数据, 值为-1时在指定索引添加, 默认在最后添加.
     */
    fun addData(item: Any?, index: Int = -1) {
        if (index < _list.size) {
            if (index == -1) {
                _list.add(item)
                notifyItemInserted(_list.size - 1)
            } else {
                _list.add(index, item)
                notifyItemInserted(index)
            }
            isRefresh(0)
        }
    }

    /**
     * 删除指定索引数据
     *
     * @param index Int 索引
     */
    fun removeAt(index: Int) {
        if (index < _list.size) {
            _list.removeAt(index)
            notifyItemRemoved(index)
            isRefresh(0)
            notifyItemRangeChanged(index, _list.size - index)
        }
    }

    /**
     * 是否全选
     */
    fun isCheckedAll(): Boolean = checkedCount == _list.size

    /**
     * 通过position判断是否启用吸顶
     */
    fun isSticky(position: Int): Boolean {
        val item = getDataOrNull<Any>(position)
        if (item is ItemSticky) {
            return item.isSticky
        }
        return false
    }

    /**
     * 数据与指定size相同时刷新适配器
     *
     * @param size 新数据数量
     */
    private fun isRefresh(size: Int) {
        if (_list.size == size) {
            notifyDataSetChanged()
        }
    }

    inner class BaseViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        lateinit var _item: Any private set

        /**
         * 使用DataBinding后通过此方法获取ViewDataBinding
         *
         * @return DB?
         */
        fun <DB : ViewDataBinding> getBinding(): DB? {
            return if (isHeader(adapterPosition) || isFooter(adapterPosition)) {
                null
            } else {
                DataBindingUtil.bind(itemView)
            }
        }

        /**
         * 获取头布局的ViewDataBinding
         * @return DB? 如果不是头布局,则返回null
         */
        fun <DB : ViewDataBinding> getHeaderBinding(): DB? {
            return if (isHeader(adapterPosition)) {
                DataBindingUtil.bind(itemView)
            } else {
                null
            }
        }

        /**
         * 获取底布局的ViewDataBinding
         * @return DB? 如果不是底布局,则返回null
         */
        fun <DB : ViewDataBinding> getFooterBinding(): DB? {
            return if (isFooter(adapterPosition)) {
                DataBindingUtil.bind(itemView)
            } else {
                null
            }
        }

        internal fun onBind(item: Any) {
            _item = item
            _onBind?.invoke(this)
            // 点击事件
            _onItemClick?.let { itemClick ->
                if (shakeEnable) {
                    itemView.setOnClickListener(ShakeClickListener {
                        itemClick.invoke(this@BaseViewHolder, this)
                    })
                } else {
                    itemView.setOnClickListener {
                        itemClick.invoke(this@BaseViewHolder, it)
                    }
                }
            }
            // 长按事件
            _onItemLongClick?.let { longClick ->
                itemView.setOnLongClickListener {
                    longClick.invoke(this@BaseViewHolder, it)
                    true
                }
            }
            // 子Item点击事件
            for (clickListener in clickListeners) {
                val view = itemView.findViewById<View>(clickListener.key) ?: continue
                if (shakeEnable) {
                    view.setOnClickListener(ShakeClickListener {
                        clickListener.value.invoke(this@BaseViewHolder, clickListener.key)
                    })
                } else {
                    view.setOnClickListener {
                        clickListener.value.invoke(this@BaseViewHolder, clickListener.key)
                    }
                }
            }
            // 子Item长按事件
            for (longClickListener in longClickListeners) {
                val view = itemView.findViewById<View>(longClickListener.key) ?: continue
                view.setOnLongClickListener {
                    longClickListener.value.invoke(this@BaseViewHolder, longClickListener.key)
                    true
                }
            }
        }

        /**
         * 获取当前类型
         * @return Any
         */
        fun getType() = _item

        /**
         * 获取当前类型数据
         */
        inline fun <reified T> getItem() = _item as T

        /**
         * 获取当前类型数据, 如果找不到则返回null
         */
        inline fun <reified T> getItemOrNull() = _item as? T

        /**
         * 通过ID查找View
         * @param id Int ID
         * @return (V..V?)
         */
        fun <V : View> findView(@IdRes id: Int) = itemView.findViewById<V>(id)

        /**
         * 展开数据
         */
        fun expand(scrollTop: Boolean = false) {
            (_item as? ItemExpand)?.let {
                // 当前已展开或者执行动画中,无需进行操作
                val childList = it.itemChildList
                if (it.itemExpand || adapterPosition == -1 || childList.isNullOrEmpty()) return
                it.itemExpand = true
                // 展开, 添加子数据到指定索引
                _list.addAll(adapterPosition + 1, childList)
                notifyItemChanged(adapterPosition)
                notifyItemRangeInserted(adapterPosition + 1, childList.size)
                notifyItemRangeChanged(adapterPosition + 1, _list.size)
                if (scrollTop) {
                    rv?.let { rv ->
                        rv.scrollToPosition(adapterPosition)
                        (rv.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(
                            adapterPosition,
                            0
                        )
                    }
                }
            }
        }

        /**
         * 收起数据
         */
        fun collapse() {
            (_item as? ItemExpand)?.let {
                collapseAll(it)
            }
        }

        /**
         * 使用递归折叠全部数据
         */
        private fun collapseAll(item: ItemExpand) {
            val childList = item.itemChildList
            if (!item.itemExpand || adapterPosition == -1 || childList.isNullOrEmpty()) return
            item.itemChildList?.forEach {
                if (it is ItemExpand) {
                    collapseAll(it)
                }
            }
            item.itemExpand = false
            // 收起, 删除子数据
            _list.removeAll(childList)
            notifyItemChanged(adapterPosition)
            notifyItemRangeRemoved(adapterPosition + 1, childList.size)
            notifyItemRangeChanged(adapterPosition + 1, _list.size)
        }

        /**
         * 展开或收起当前Item
         */
        fun expandOrCollapse(scrollTop: Boolean = false) {
            (_item as? ItemExpand)?.let {
                if (it.itemExpand) {
                    collapse()
                } else {
                    expand(scrollTop)
                }
            }
        }
    }

    /**
     * 点击事件防抖
     *
     * @property internal
     * @property block
     */
    private class ShakeClickListener(
        private val internal: Int = 500,
        private val block: View.() -> Unit
    ) : View.OnClickListener {

        private val lastClickTime = AtomicLong(0)

        override fun onClick(v: View) {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime.get() > internal) {
                lastClickTime.set(currentTime)
                block.invoke(v)
            }
        }
    }

}