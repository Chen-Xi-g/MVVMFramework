package com.alvin.mvvm_framework.view_model.activity

import android.view.View
import com.alvin.mvvm.base.view_model.BaseViewModel
import com.alvin.mvvm_framework.base.intent
import com.alvin.mvvm_framework.ui.activity.SampleCustomRecyclerActivity
import com.alvin.mvvm_framework.ui.activity.SampleDefaultRecyclerActivity
import com.alvin.mvvm_framework.ui.activity.SampleStatusLayoutActivity

/**
 * <h3> 作用类描述：Main 处理点击事件的ViewModel</h3>
 *
 * @Package :        com.alvin.mvvm_framework.view_model.activity
 * @Date :           2022/3/4
 * @author 高国峰
 */
class SampleMainClickViewModel : BaseViewModel() {

    /**
     * 控制状态栏的Activity
     *
     * @param view View
     */
    fun clickStartStatusBarActivity(view: View) {
        val content = view.context
        content.startActivity(content.intent(SampleStatusLayoutActivity::class.java))
    }

    /**
     * 跳转到列表Activity
     *
     * @param view View
     */
    fun clickStartRecyclerViewActivity(view: View) {
        val content = view.context
        content.startActivity(content.intent(SampleDefaultRecyclerActivity::class.java))
    }

    /**
     * 跳转到列表Activity
     *
     * @param view View
     */
    fun clickStartCustomRecyclerViewActivity(view: View) {
        val content = view.context
        content.startActivity(content.intent(SampleCustomRecyclerActivity::class.java))
    }

}