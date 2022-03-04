package com.alvin.mvvm.callback

/**
 * <h3> 作用类描述：刷新和加载更多的监听回调</h3>
 *
 * @Package :        com.alvin.mvp.base
 * @Date :           2021/12/13-13:40
 * @author Alvin
 */
interface IRefreshLoadListener {
    /**
     * 返回刷新回调
     *
     */
    fun refresh()

    /**
     * 返回加载更多回调
     *
     */
    fun loadMore()
}