package com.alvin.mvvm_framework.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alvin.mvvm.help.ISettingBaseFragment
import com.alvin.mvvm_framework.R

/**
 * <h3> 作用类描述：设置全局Fragment</h3>
 *
 * @Package :        com.alvin.mvvm_framework.setting
 * @Date :           2022/1/7
 * @author 高国峰
 */
class FragmentSetting : ISettingBaseFragment {
    override fun isWidth(): Boolean? {
        return null
    }

    override fun setTitleLayoutView(
        fragment: Fragment?,
        ibBarBack: AppCompatImageButton?,
        tvBarTitle: AppCompatTextView?,
        tvBarRight: AppCompatTextView?,
        ibBarRight: AppCompatImageButton?
    ) {
        ibBarBack?.setOnClickListener {
            if (fragment?.findNavController()?.popBackStack() == false) {
                fragment.activity?.finish()
            }
        }
    }

    override fun barLight(): Boolean {
        return false
    }

    override fun <T : ViewDataBinding?> putTitleView(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): T? {
        return DataBindingUtil.inflate(inflater, R.layout.global_title,parent,true)
    }

    override fun <T : ViewDataBinding?> putLoadingView(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): T? {
        return DataBindingUtil.inflate(inflater, R.layout.global_loading,parent,true)
    }

    override fun <T : ViewDataBinding?> putNoNetView(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): T? {
        return DataBindingUtil.inflate(inflater, R.layout.global_error,parent,true)
    }
}