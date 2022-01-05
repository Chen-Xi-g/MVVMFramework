package com.alvin.mvvm.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.alvin.mvvm.R
import com.alvin.mvvm.databinding.FragmentBaseBinding
import com.gyf.immersionbar.ktx.immersionBar

/**
 * <h3> 作用类描述：对布局进行初始化设置</h3>
 *
 * @Package :        com.alvin.mvvm.base.fragment
 * @Date :           2021/12/24-9:42
 * @author Alvin
 */
abstract class BaseContentViewFragment : BaseDialogFragment() {
    /**
     * 获取左侧Icon View
     */
    private var _ibBarBack: AppCompatImageButton? = null
    val ibBarBack get() = _ibBarBack!!

    /**
     * 获取标题View
     *
     */
    private var _tvBarTitle: AppCompatTextView? = null
    val tvBarTitle get() = _tvBarTitle!!

    /**
     * 获取右侧Menu文字View
     */
    private var _tvBarRight: AppCompatTextView? = null
    val tvBarRight get() = _tvBarRight!!

    /**
     * 获取右侧Menu按钮View
     */
    private var _ibBarRight: AppCompatImageButton? = null
    val ibBarRight get() = _ibBarRight!!

    /**
     * 获取加载失败布局
     */
    private var _errorLayout: FrameLayout? = null
    val errorLayout get() = _errorLayout!!

    /**
     * 获取第一次加载内容布局
     */
    private var _loadingLayout: FrameLayout? = null
    val loadingLayout get() = _loadingLayout!!

    /**
     * 获取正常内容布局
     */
    private var _contentLayout: FrameLayout? = null
    val contentLayout get() = _contentLayout!!

    /**
     * 获取标题布局
     *
     */
    private var _titleLayout: FrameLayout? = null
    val titleLayout get() = _titleLayout!!

    private lateinit var contentView: FragmentBaseBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        contentView = DataBindingUtil.inflate(inflater, R.layout.fragment_base, container, false)
        contentView.lifecycleOwner = this
        _contentLayout = contentView.baseContentLayout
        initLayoutBinding()
        return contentView.root
    }

    private fun initLayoutBinding() {
        loadTitleView()
        loadBaseLoadingView()
        loadNoNetView()
    }

    /**
     * 添加错误布局
     */
    private fun loadNoNetView() {
        // 清楚原有错误布局
        contentView.baseNoNetLayout.removeAllViews()

        // 添加失败布局
        val putNoNetView =
            putNoNetView<ViewDataBinding>(layoutInflater, contentView.baseNoNetLayout)

        // 失败布局为空，不显示
        if (putNoNetView == null || !noNetLayoutView()) {
            contentView.baseNoNetLayout.visibility = View.GONE
            return
        }

        _errorLayout = contentView.root.findViewById(R.id.baseNoNetLayout)

        // 失败布局点击后重新加载
        putNoNetView.root.findViewById<View>(noNetClickId())?.setOnClickListener {
            reload()
        }
    }

    /**
     * 添加正在加载布局
     */
    private fun loadBaseLoadingView() {
        // 清除原来的Title布局
        contentView.baseLoadingLayout.removeAllViews()

        // 通过DataBinding设置正在加载布局
        val putLoadingView =
            putLoadingView<ViewDataBinding>(layoutInflater, contentView.baseLoadingLayout)

        // 不需要正在加载布局 隐藏
        if (putLoadingView == null || !loadingLayoutView()) {
            contentView.baseLoadingLayout.visibility = View.GONE
            return
        }

        _loadingLayout = contentView.root.findViewById(R.id.baseLoadingLayout)
    }

    /**
     * 添加标题布局
     */
    private fun loadTitleView() {
        // 清除原来的Title布局
        contentView.baseTitleLayout.removeAllViews()

        // 通过DataBinding设置title布局
        val putTitleView =
            putTitleView<ViewDataBinding>(layoutInflater, contentView.baseTitleLayout)

        // 不需要标题布局，隐藏布局
        if (putTitleView == null || !titleLayoutView()) {
            contentView.baseTitleLayout.visibility = View.GONE
            return
        }

        // 获取布局成功
        contentView.baseTitleLayout.visibility = View.VISIBLE
        // 布局默认设置内边距
        if (isStatusPadding()) {
            setStatusBarPadding(putTitleView.root)
        }
        _titleLayout = contentView.root.findViewById(R.id.baseTitleLayout)

        // 获取返回控件
        _ibBarBack = putTitleView.root.findViewById(R.id.ibBarBack)
        ibBarBack.setImageResource(titleLeftIcon())
        // 设置标题名称
        _tvBarTitle = putTitleView.root.findViewById(R.id.tvBarTitle)
        // 设置右侧文字
        _tvBarRight = putTitleView.root.findViewById(R.id.tvBarRight)
        // 设置右侧按钮
        _ibBarRight = putTitleView.root.findViewById(R.id.ibBarRight)

        // 默认的属性回调
        iSettingBaseFragment.setTitleLayoutView(
            this,
            ibBarBack,
            tvBarTitle,
            tvBarRight,
            ibBarRight
        )
    }

    /**
     * 加载成功内容的布局
     *
     * @param T DataBinding
     * @param resId 布局ID
     * @return
     */
    fun <T : ViewDataBinding> putContentView(@LayoutRes resId: Int): T {
        return DataBindingUtil.inflate(layoutInflater, resId, contentView.baseContentLayout, true)
    }

    /**
     * 设置标题名
     */
    fun setTitleName(title: String) {
        tvBarTitle.text = title
    }

    /**
     * 将一个布局延长状态栏的内间距
     */
    private fun setStatusBarPadding(linear_bar: View) {
        immersionBar {
            titleBar(linear_bar)
            init()
        }
    }


    /**
     * 添加标题布局
     *
     * @param T DataBinding
     * @return
     */
    open fun <T : ViewDataBinding?> putTitleView(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): T? {
        return iSettingBaseFragment.putTitleView(inflater, parent)
    }

    /**
     * 添加正在加载中的布局
     *
     * @param T DataBinding
     * @return
     */
    open fun <T : ViewDataBinding?> putLoadingView(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): T? {
        return iSettingBaseFragment.putLoadingView(inflater, parent)
    }

    /**
     * 添加正在加载中的布局
     *
     * @param T DataBinding
     * @return
     */
    open fun <T : ViewDataBinding?> putNoNetView(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): T? {
        return iSettingBaseFragment.putNoNetView(inflater, parent)
    }

    /**
     * 是否需要设置内边距 默认为true
     *
     * @return 是否需要内边距
     */
    open fun isStatusPadding(): Boolean = iSettingBaseFragment.isStatusPadding()

    /**
     * 获取自定义标题布局 详情见
     *
     * @see com.alvin.mvvm.help.ISettingBaseFragment.putTitleView
     * @return true： 加载布局    false： 不加载布局
     */
    open fun titleLayoutView(): Boolean = true

    /**
     * 获取自定义的第一次加载Loading布局
     *
     * @see com.alvin.mvvm.help.ISettingBaseFragment.putLoadingView
     * @return true： 加载布局    false： 不加载布局
     */
    open fun loadingLayoutView(): Boolean = true

    /**
     * 获取自定义失败布局
     *
     * @see com.alvin.mvvm.help.ISettingBaseFragment.putNoNetView
     * @return true： 加载布局    false： 不加载布局
     */
    open fun noNetLayoutView(): Boolean = true

    /**
     * 设置加载失败需要响应重新加载的ViewID
     *
     * @return ViewID
     */
    @IdRes
    open fun noNetClickId(): Int = iSettingBaseFragment.noNetClickId()

    /**
     * 加载失败后重新加载数据
     *
     */
    open fun reload() {}

    /**
     * 单独设置该Activity的返回按钮样式
     *
     * @return 返回值为0则隐藏控件
     */
    @DrawableRes
    open fun titleLeftIcon(): Int = iSettingBaseFragment.titleLeftIcon()

    /**
     * 显示失败布局，该方法会隐藏内容布局。
     *
     */
    fun showErrorLayout() {
        if (errorLayout.visibility == View.VISIBLE) {
            return
        }
        errorLayout.visibility = View.VISIBLE
        contentLayout.visibility = View.GONE
        loadingLayout.visibility = View.GONE
    }

    /**
     * 隐藏失败布局，该方法会显示内容布局.
     *
     */
    fun hideErrorLayout() {
        if (contentLayout.visibility == View.VISIBLE) {
            return
        }
        errorLayout.visibility = View.GONE
        contentLayout.visibility = View.VISIBLE
        loadingLayout.visibility = View.GONE
    }

    /**
     * 显示第一次加载内容的Loading布局
     */
    fun showLoadingLayout() {
        if (loadingLayout.visibility == View.VISIBLE) {
            return
        }
        contentLayout.visibility = View.GONE
        errorLayout.visibility = View.GONE
        loadingLayout.visibility = View.VISIBLE
    }

    /**
     * 隐藏第一次加载内容的Loading布局
     *
     */
    fun hideLoadingLayout() {
        if (contentLayout.visibility == View.VISIBLE) {
            return
        }
        contentLayout.visibility = View.VISIBLE
        errorLayout.visibility = View.GONE
        loadingLayout.visibility = View.GONE
    }

    /**
     * 网络请求开始之前处理的事情，如显示Dialog等
     *
     * @param isShowFirstLoading 是否显示首次加载Loading
     *                           true: 显示首次加载Loading布局
     *                           false: 不显示首次加载Loading
     * @param dialogText 加载对话框显示的内容。
     *                   如果 `isShowFirstLoading == false` 并且 `dialogText.isNotEmpty() == true` 则显示 加载对话框
     */
    open fun beforeNetwork(isShowFirstLoading: Boolean = false, dialogText: String? = null) {
        if (isShowFirstLoading) {
            showLoadingLayout()
        } else {
            // 加载对话框不为空， 显示加载内容
            if (!dialogText.isNullOrEmpty()) {
                loading(dialogText)
            }
        }
    }

    /**
     * 网络请求结束后处理的事情，如关闭Dialog等
     *
     */
    open fun afterNetwork() {
        hideLoadingLayout()
        dismissLoading()
    }

    /**
     * 网络请求发生异常
     *
     * @param errorMsg 错误信息
     */
    open fun onFailed(errorMsg: String?) {
        hideErrorLayout()
        errorMsg?.let {
            if (iSettingBaseFragment.isErrorToast()) {
                Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
            }
        }
    }
}