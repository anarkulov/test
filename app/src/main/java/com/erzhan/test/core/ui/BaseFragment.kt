package com.erzhan.test.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.erzhan.test.R
import com.erzhan.test.extentions.observeFresh
import com.erzhan.test.extentions.visible

abstract class BaseFragment<VM: BaseViewModel, VB: ViewBinding> : Fragment() {

    protected abstract val viewModel: VM
    protected lateinit var binding: VB
    private var _view: View? = null //cached view

    private var _navController: NavController? = null

    protected abstract fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): VB
    protected abstract fun bindViewBinding(view: View): VB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(_view == null) {
            binding = inflateViewBinding(inflater, container, savedInstanceState)
            _view = binding.root
        } else {
            binding = bindViewBinding(_view!!)
        }
        return _view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        _navController = navHostFragment.navController

        initViewModel()
        initView()
        initListeners()
    }

    /*Navigation*/
    fun navigate(directions: NavDirections) {
        _navController?.navigate(directions)
    }

    fun navigate(destinationId: Int) {
        _navController?.navigate(destinationId)
    }

    fun currentDestinationId(): Int? {
        return _navController?.currentDestination?.id
    }
    /*Navigation End*/

    open fun initViewModel() {
        viewModel.loading.observeFresh(viewLifecycleOwner) {
            _view?.findViewById<ProgressBar>(R.id.loading)?.visible = it
        }
    }

    open fun initView() {}
    open fun initListeners() {}
}