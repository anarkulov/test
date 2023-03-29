package com.erzhan.test.core.ui

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.erzhan.test.R
import com.erzhan.test.extentions.observeFresh
import com.erzhan.test.extentions.visible

abstract class BaseActivity<out VM : BaseViewModel, VB : ViewBinding>: AppCompatActivity() {

    protected abstract val viewModel: VM
    protected lateinit var viewBinding: VB

    protected abstract fun inflateViewBinding(inflater: LayoutInflater): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = inflateViewBinding(LayoutInflater.from(this))
        setContentView(viewBinding.root)

        initViewModel()
        initView()
        initListeners()
    }

    override fun attachBaseContext(newBase: Context?) {
        applyOverrideConfiguration(Configuration(newBase?.resources?.configuration).apply {
            fontScale = 1.0f
        })
        super.attachBaseContext(newBase)
    }

    open fun initViewModel() {
        viewModel.loading.observeFresh(this) {
            findViewById<ProgressBar>(R.id.loading).visible = it
        }
    }
    open fun initView() {}
    open fun initListeners() {}

    fun showLoading(value: Boolean) {
        viewModel.loading.postValue(value)
    }
}