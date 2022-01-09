package com.alvin.mvvm_framework.setting

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.alvin.mvvm.help.ISettingBaseActivity
import com.alvin.mvvm_framework.R

/**
 * <h3> 作用类描述：全局Activity设置</h3>
 *
 * @Package :        com.alvin.mvvm_framework.setting
 * @Date :           2022/1/7
 * @author 高国峰
 */
class ActivitySetting :  ISettingBaseActivity{
    override fun isWidth(): Boolean? {
        return null
    }

    override fun setTitleLayoutView(
        activity: Activity,
        ibBarBack: AppCompatImageButton?,
        tvBarTitle: AppCompatTextView?,
        tvBarRight: AppCompatTextView?,
        ibBarRight: AppCompatImageButton?
    ) {
        ibBarBack?.setOnClickListener {
            activity.finish()
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
}