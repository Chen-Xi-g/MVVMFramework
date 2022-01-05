package com.alvin.mvvm.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * <h3> 作用类描述：Activity的抽象基类，这个类里面的方法适用于全部activity的需求 </h3>
 *
 * @ProjectName:    MVVM_Framework
 * @Package:        com.alvin.mvvm.base.activity
 * @ClassName:      AbstractActivity
 * @CreateDate:      2021/12/20
 * @Description:    Activity的抽象基类，这个类里面的方法适用于全部activity的需求
 * @author:          Alvin
 */
abstract class AbstractActivity : AppCompatActivity() {

    /**
     * 实现initView来做视图相关的初始化
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 实现obtainData来做数据的初始化
     */
    abstract fun obtainData()
}