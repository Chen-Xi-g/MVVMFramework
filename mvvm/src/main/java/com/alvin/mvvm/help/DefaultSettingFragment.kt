package com.alvin.mvvm.help

import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

/**
 * <h3> 作用类描述：默认的Fragment设置</h3>
 *
 * @Package :        com.alvin.mvvm.help
 * @Date :           2021/9/2-17:12
 * @author Alvin
 */
class DefaultSettingFragment : ISettingBaseFragment {
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

    override fun isErrorToast(): Boolean {
        return super.isErrorToast()
    }

}