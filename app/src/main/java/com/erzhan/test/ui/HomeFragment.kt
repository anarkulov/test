package com.erzhan.test.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.erzhan.test.R
import com.erzhan.test.core.ui.BaseFragment
import com.erzhan.test.ui.home.HomeViewModel
import com.erzhan.test.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    override val viewModel: HomeViewModel by viewModels()

    override fun initView() {
        super.initView()
        initSpinner()
    }

    override fun initListeners() {
        super.initListeners()
    }

    private fun initSpinner() {
        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrayOf("Telcom", "Safaricom")
        )
        binding.airtimeSpinner.adapter = spinnerAdapter

        binding.airtimeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                when (position) {
                    0 -> {
                        binding.airtimeIcon.setImageResource(R.drawable.telcom)
                    }
                    1 -> {
                        binding.airtimeIcon.setImageResource(R.drawable.safaricom)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun bindViewBinding(view: View): FragmentHomeBinding {
        return FragmentHomeBinding.bind(view)
    }

}