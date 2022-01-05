package com.alvin.mvvm.base.fragment

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatTextView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.alvin.mvvm.R

/**
 * <h3> 作用类描述：显示Dialog的Fragment</h3>
 *
 * @Package :        com.yleanlink.mvp
 * @Date :           2021/9/13-18:10
 * @author Alvin
 */
abstract class BaseDialogFragment : BaseFragment() {

    private var dialog: MaterialDialog? = null

    private var dialogContent: AppCompatTextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            dialog = MaterialDialog(it).loadingDialog()
        }
    }


    /**
     * 显示加载中的Dialog
     *
     */
    fun loading(text: String = getString(R.string.loading)) {
        if (dialog?.isShowing == false) {
            dialogContent?.text = text
            dialog?.show()
        }
    }

    /**
     * 是否关闭Dialog
     *
     * @param isNotDismiss true 关闭; false 不关闭
     */
    fun dismissLoading(isNotDismiss: Boolean = false) {
        if (dialog?.isShowing == true && !isNotDismiss) {
            dialog?.dismiss()
        }
    }


    override fun onStop() {
        super.onStop()
        dismissLoading()
    }


    private fun MaterialDialog.loadingDialog(): MaterialDialog {
        customView(dialogView(), noVerticalPadding = true, dialogWrapContent = true)
        cancelable(false)
        cornerRadius(8F)
        maxWidth(literal = dip2px())
        dialogContent = findViewById(R.id.loadingContent)
        return this
    }

    /**
     * 自定义Dialog布局，文字ID必须为 loadingContent
     *
     */
    @LayoutRes
    open fun dialogView(): Int {
        return R.layout.dialog_loading
    }

    fun dialogMaxWidth(widthPx: Int) {
        dialog?.maxWidth(literal = widthPx)
    }

    /**
     * dp2px
     */
    private fun dip2px(): Int {
        val scale: Float = resources.displayMetrics.density
        return (160 * scale + 0.5f).toInt()
    }
}