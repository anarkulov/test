package com.erzhan.test.ui

import android.view.LayoutInflater
import androidx.activity.viewModels
import com.erzhan.test.R
import com.erzhan.test.core.ui.BaseActivity
import com.erzhan.test.databinding.ActivityMainBinding

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    override val viewModel: MainViewModel by viewModels()

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }

    override fun initView() {
        super.initView()
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        viewBinding.bottomNavigationView.menu.findItem(R.id.menuHome).isChecked = true

        viewBinding.bottomNavigationView.setOnItemSelectedListener {
            for (i in 0 until viewBinding.bottomNavigationView.menu.size()) {
                val item = viewBinding.bottomNavigationView.menu.getItem(i)
                val iconView = item.icon
                if (item.itemId == it.itemId) {
                    val size = resources.getDimensionPixelSize(R.dimen.bottom_nav_icon_selected_size)
                    iconView?.setBounds(0, 0, size, size)
                } else {
                    val size = resources.getDimensionPixelSize(R.dimen.bottom_nav_icon_unselected_size)
                    iconView?.setBounds(0, 0, size, size)
                }
            }
            true
        }
    }
}