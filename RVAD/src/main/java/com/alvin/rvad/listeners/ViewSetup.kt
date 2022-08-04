package com.alvin.rvad.listeners

import android.view.View

/**
 * <h3> 作用类描述：Adds sticky headers capabilities to the {@link RecyclerView.Adapter}. Should return {@code true} for all positions that represent sticky headers.
 * </h3>
 *
 * @Package :        com.alvin.rvad.listeners
 * @Date :           2022/8/1-17:37
 * @author 高国峰
 *
 * 原地址：[https://github.com/Doist/RecyclerViewExtensions/blob/master/StickyHeaders/src/main/java/io/doist/recyclerviewext/sticky_headers/StickyHeaders.java]
 */
interface ViewSetup {
    /**
     * Adjusts any necessary properties of the `holder` that is being used as a sticky header.
     *
     * [.teardownStickyHeaderView] will be called sometime after this method
     * and before any other calls to this method go through.
     */
    fun setupStickyHeaderView(stickyHeader: View?)

    /**
     * Reverts any properties changed in [.setupStickyHeaderView].
     *
     * Called after [.setupStickyHeaderView].
     */
    fun teardownStickyHeaderView(stickyHeader: View?)
}