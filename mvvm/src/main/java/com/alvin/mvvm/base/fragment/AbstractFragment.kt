package com.alvin.mvvm.base.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

/**
 * <h3> 作用类描述：</h3>
 *
 * @Package :        com.alvin.mvvm.base.fragment
 * @Date :           2021/12/22-15:41
 * @author Alvin
 */
abstract class AbstractFragment : Fragment() {
    /**
     * 实现initView来做视图相关的初始化
     */
    abstract fun initView(view: View, savedInstanceState: Bundle?)

    /**
     * 实现obtainData来做数据的初始化
     */
    abstract fun obtainData()

    /**
     * 懒加载数据
     *
     */
    abstract fun lazyLoadData()
}