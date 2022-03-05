package com.alvin.mvvm_framework.ui.activity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.alvin.mvvm.base.activity.BaseMVVMActivity
import com.alvin.mvvm_framework.R
import com.alvin.mvvm_framework.databinding.SampleActivityMainBinding
import com.alvin.mvvm_framework.ui.fragment.SampleHomeActivityFragment
import com.alvin.mvvm_framework.view_model.activity.SampleMainViewModel

/**
 * <h3> 作用类描述：Main </h3>
 *
 * @Package :        com.alvin.mvvm_framework.ui.activity
 * @Date :           2022-03-03
 * @author
 *
 */
class SampleMainActivity :
    BaseMVVMActivity<SampleMainViewModel, SampleActivityMainBinding>(R.layout.sample_activity_main) {

    private val fragments by lazy(LazyThreadSafetyMode.NONE) {
        listOf<Fragment>(SampleHomeActivityFragment(), SampleHomeActivityFragment())
    }

    /**
     * ViewPager2 切换监听
     */
    private val vpListener by lazy(LazyThreadSafetyMode.NONE) {
        object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setTitleName(if (position == 0) "Activity样本" else "Fragment样本")
                dataBinding.bottomNavigation.menu.getItem(position).isChecked = true
            }
        }
    }

    /**
     * 在initView中执行View初始化的任务，不要做逻辑的处理
     */
    override fun initView(savedInstanceState: Bundle?) {
        setTitleName("Activity样本")
        ibBarBack.visibility = View.GONE
        initVpBottom()
    }

    /**
     * 在obtainData中做逻辑处理，不要做View初始化
     */
    override fun obtainData() {
    }

    /**
     * 初始化LiveData数据观察者
     */
    override fun registerObserver() {
    }

    private fun initVpBottom() {
        dataBinding.vpContent.adapter = object : FragmentStateAdapter(this) {

            override fun getItemCount(): Int {
                return fragments.size
            }

            override fun createFragment(position: Int): Fragment {
                return fragments[position]
            }
        }
        dataBinding.vpContent.registerOnPageChangeCallback(vpListener)
        dataBinding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menuActivity -> {
                    dataBinding.vpContent.currentItem = 0
                }
                R.id.menuFragment -> {
                    dataBinding.vpContent.currentItem = 1
                }
            }
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dataBinding.vpContent.unregisterOnPageChangeCallback(vpListener)
    }


}