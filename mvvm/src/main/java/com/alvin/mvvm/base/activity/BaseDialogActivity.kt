package com.alvin.mvvm.base.activity

import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatTextView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.alvin.mvvm.R

/**
 * <h3> 作用类描述：显示Dialog弹窗的Activity</h3>
 *
 * @Package :        com.alvin.mvvm.base.activity
 * @Date :           2021/12/20
 * @author Alvin
 */
abstract class BaseDialogActivity : BaseActivity() {
    private val dialog by lazy {
        MaterialDialog(this).loadingDialog()
    }

    private var dialogContent: AppCompatTextView? = null

    /**
     * 显示加载框的Dialog
     *
     * @param content loading 显示的内容
     */
    fun loading(content: String = getString(R.string.loading)) {
        if (!dialog.isShowing) {
            dialogContent?.text = content
            dialog.show()
        }
    }

    /**
     * 是否关闭Dialog
     *
     * @param isNotDismiss true 关闭; false 不关闭
     */
    fun dismissLoading(isNotDismiss: Boolean = false) {
        if (dialog.isShowing && !isNotDismiss) {
            dialog.dismiss()
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
     * 自定义Dialog布局，文字ID必须为 loading_text
     *
     */
    @LayoutRes
    open fun dialogView(): Int {
        return R.layout.dialog_loading
    }

    fun dialogMaxWidth(widthPx: Int) {
        dialog.maxWidth(literal = widthPx)
    }

    /**
     * dp2px
     */
    private fun dip2px(): Int {
        val scale: Float = resources.displayMetrics.density
        return (160 * scale + 0.5f).toInt()
    }
}