package com.alvin.mvvm_framework.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.alvin.mvvm.base.fragment.BaseMVVMFragment
import com.alvin.mvvm_framework.R
import com.alvin.mvvm_framework.databinding.FragmentMainBinding
import com.alvin.mvvm_framework.ui.fragment.music.MusicListFragment
import com.alvin.mvvm_framework.ui.fragment.tv.TVListFragment
import com.alvin.mvvm_framework.viewmodel.fragment.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * <h3> 作用类描述：Main</h3>
 *
 * @Package :        com.alvin.mvvm_framework.ui.fragment
 * @Date :           2022/1/9
 * @author 高国峰
 */
class MainFragment : BaseMVVMFragment<MainViewModel, FragmentMainBinding>(R.layout.fragment_main){
    override fun initView(view: View, savedInstanceState: Bundle?) {
        dataBinding.vp2Content.initViewPager()
    }

    override fun obtainData() {
    }

    override fun lazyLoadData() {
    }

    override fun registerObserver() {
    }


    /**
     * 初始化ViewPager2
     */
    private fun ViewPager2.initViewPager() {
        // 是否可以滑动
//        isUserInputEnabled = true
        // 设置缓存页数
//        offscreenPageLimit = 2
        adapter = object : FragmentStateAdapter(this@MainFragment) {
            override fun getItemCount(): Int {
                return 2
            }

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    1 -> MusicListFragment()
                    else -> TVListFragment()
                }
            }
        }

        /**
         * ViewPager2 切换监听
         */
        registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                dataBinding.bnMenu.selectedItemId = dataBinding.bnMenu.menu[position].itemId
            }
        })

        /**
         * 初始化Bottom
         */
        fun BottomNavigationView.initBottom() {
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.menu_tv -> currentItem = 0
                    R.id.menu_music -> currentItem = 1
                }
                return@setOnNavigationItemSelectedListener true
            }
        }

        /**
         * 初始化Bottom菜单
         */
        dataBinding.bnMenu.initBottom()
    }

    override fun titleLayoutView(): Boolean {
        return false
    }
}