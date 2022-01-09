package com.alvin.mvvm.help

import android.app.Activity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView

/**
 * <h3> 作用类描述：默认的Activity设置</h3>
 *
 * @Package :        com.alvin.mvvm.help
 * @Date :           2021/8/31-15:40
 * @author Alvin
 */
class DefaultSettingActivity : ISettingBaseActivity {
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
}