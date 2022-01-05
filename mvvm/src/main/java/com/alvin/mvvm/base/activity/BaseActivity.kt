package com.alvin.mvvm.base.activity

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.ColorRes
import com.alvin.mvvm.help.GlobalMVPBuilder
import com.gyf.immersionbar.ktx.immersionBar
import me.jessyan.autosize.AutoSizeCompat
import me.jessyan.autosize.internal.CustomAdapt

/**
 * <h3> 作用类描述：基础的Activity功能，做相关初始化和适配。</h3>
 *
 * @Package :        com.alvin.mvvm.base.activity
 * @Date :           2021/12/20
 * @author Alvin
 */
abstract class BaseActivity : AbstractActivity(), CustomAdapt {

    /**
     * 获取默认的全局设置.
     */
    val iSettingActivity = GlobalMVPBuilder.iSettingBaseActivity

    /**
     * 当前Activity是否可见.
     */
    var isResume = false
        private set

    /**
     * 当前Activity是否销毁.
     */
    var isDestroy = false
        private set


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 初始化ViewBinding
        initDataBinding()
        // 初始化 titleBar
        initBarColor()
        //由具体的activity实现，做视图相关的初始化
        initView(savedInstanceState)
        //由具体的activity实现，做数据的初始化
        obtainData()
    }

    /**
     * 在OnCreate之前对ViewBinding初始化
     *
     */
    abstract fun initDataBinding()

    /**
     * 初始化titleBar
     *
     */
    private fun initBarColor() {
        immersionBar {
            //设置透明状态栏 默认透明
            if (hideStatusBarBackground()) {
                transparentStatusBar()
            }
            // 设置状态栏字体颜色 默认白色
            statusBarDarkFont(!barLight())
            navigationBarColor(navigationBarColor())
            init()
        }
    }

    /**
     * 设置屏幕透明度
     *
     * @param bgAlpha 0.0~1.0
     */
    protected fun backgroundAlpha(bgAlpha: Float) {
        val lp = window.attributes
        lp.alpha = bgAlpha // 0.0~1.0
        window.attributes = lp
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) ev.let {
            if (isShouldHideInput(currentFocus, it)) {
                val im =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                im.hideSoftInputFromWindow(
                    currentFocus?.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun isShouldHideInput(view: View?, ev: MotionEvent): Boolean {
        if (view is EditText) {
            val l: IntArray = intArrayOf(0, 0)
            view.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom: Int = top + view.getHeight()
            val right: Int = (left
                    + view.getWidth())
            return !(ev.x > left && ev.x < right
                    && ev.y > top && ev.y < bottom)
        }
        return false
    }

    /**
     * 显示隐藏软键盘
     *
     * @see android.view.Window#getCurrentFocus
     * @param view 详情见
     * @param isOpen true 隐藏 false 显示
     */
    private fun softInputHideOrShow(view: View?, isOpen: Boolean) {
        if (view == null) return
        val inputManger =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (isOpen) inputManger.showSoftInput(
            view,
            InputMethodManager.SHOW_FORCED
        ) else inputManger.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onResume() {
        super.onResume()
        isResume = true
    }

    override fun onPause() {
        super.onPause()
        isResume = false
    }

    override fun onDestroy() {
        isDestroy = true
        super.onDestroy()
    }

    /**
     * 内存不足 手动调用GC
     *
     */
    override fun onLowMemory() {
        System.gc()
        super.onLowMemory()
    }

    override fun isBaseOnWidth(): Boolean = if (isWidth() == null) {
        resources.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT
    } else {
        isWidth() ?: true
    }

    override fun getSizeInDp(): Float = if (isWidth() == null) {
        screenDesignWidth().toFloat()
    } else {
        if (isWidth() == true) screenDesignWidth().toFloat() else screenDesignHeight().toFloat()
    }


    /**
     * true 使用宽度适配（默认）.
     *
     * false 使用高度适配
     */
    open fun isWidth(): Boolean? = iSettingActivity.isWidth()


    override fun getResources(): Resources {
        AutoSizeCompat.autoConvertDensity(
            (super.getResources()),
            screenDesignWidth().toFloat(),
            if (isWidth() == null) {
                super.getResources().configuration?.orientation == Configuration.ORIENTATION_PORTRAIT
            } else {
                isWidth() ?: true
            }
        )
        return super.getResources()
    }

    /**
     * 设置屏幕适配宽 设计图的宽度.
     *
     * 375（默认）
     */
    open fun screenDesignWidth(): Int = iSettingActivity.screenDesignWidth()

    /**
     * 设置屏幕适配的高 设计图的宽度.
     *
     * 640（默认）
     */
    open fun screenDesignHeight(): Int = iSettingActivity.screenDesignHeight()

    /**
     * 是否透明状态栏
     *
     * @return true 是全透明 false 不是
     */
    open fun hideStatusBarBackground(): Boolean = iSettingActivity.hideStatusBarBackground()

    /**
     * 状态栏图标是否为亮色
     *
     * @return 默认true(白色)
     */
    open fun barLight(): Boolean = iSettingActivity.barLight()

    /**
     * 导航栏颜色 白色
     *
     * @return
     */
    @ColorRes
    open fun navigationBarColor(): Int = iSettingActivity.navigationBarColor()

    /**
     * 跳转到指定Activity
     *
     * @param clazz Activity
     * @param bundle 跳转时携带的参数
     * @param isFinish 是否需要跳转后关闭Activity
     */
    fun startActivity(clazz: Class<*>, bundle: Bundle? = null, isFinish: Boolean = false) {
        val intent = Intent(this, clazz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
        if (isFinish) {
            finish()
        }
    }
}