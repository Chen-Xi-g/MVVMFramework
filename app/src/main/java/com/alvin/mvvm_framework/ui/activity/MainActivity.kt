package com.alvin.mvvm_framework.ui.activity

import android.os.Bundle
import com.alvin.mvvm.base.activity.BaseMVVMActivity
import com.alvin.mvvm_framework.R
import com.alvin.mvvm_framework.databinding.ActivityMainBinding
import com.alvin.mvvm_framework.viewmodel.activity.MainViewModel

class MainActivity : BaseMVVMActivity<MainViewModel, ActivityMainBinding>(R.layout.activity_main) {
    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun obtainData() {
    }

    override fun registerObserver() {
    }

    override fun titleLayoutView(): Boolean {
        return false
    }

}
