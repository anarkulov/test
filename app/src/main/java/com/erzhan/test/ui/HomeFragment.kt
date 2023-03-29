package com.erzhan.test.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.erzhan.test.R
import com.erzhan.test.core.Utils
import com.erzhan.test.core.ui.BaseFragment
import com.erzhan.test.databinding.FragmentHomeBinding
import com.erzhan.test.ui.home.HomeViewModel

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    override val viewModel: HomeViewModel by viewModels()

    override fun initView() {
        super.initView()
        initSpinner()
    }

    override fun initListeners() {
        super.initListeners()

        binding.etAirtimeNumber.setOnEditorActionListener { textView, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                if (Utils.isValidPhoneNumber(textView.text.toString())) {
                    try {
                        binding.etAirtimeNumber.setText(Utils.normalizePhoneNumber(textView.text.toString()))
                        binding.etAirtimeAmount.requestFocus()
                        return@setOnEditorActionListener true
                    } catch (e: IllegalArgumentException) {
                        binding.etAirtimeNumber.error = e.message
                        binding.airtimeButton.isEnabled = false
                        return@setOnEditorActionListener false
                    }
                } else {
                    binding.etAirtimeNumber.error = getString(R.string.invalid_phone_number)
                    binding.airtimeButton.isEnabled = false
                    return@setOnEditorActionListener false
                }
            }
            false
        }

        binding.etAirtimeAmount.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                if (textView.text.toString().isNotEmpty()
                    && textView.text.toString().toInt() >= 5
                ) {
                    Utils.hideKeyboard(requireContext(), binding.root)
                    updateUI()
                    return@setOnEditorActionListener true
                } else {
                    binding.etAirtimeAmount.error = getString(R.string.invalid_amount)
                    binding.airtimeButton.isEnabled = false
                }
            }
            false
        }

    }

    private fun updateUI() {
        binding.airtimeButton.isEnabled =
            Utils.isValidPhoneNumber(binding.etAirtimeNumber.text.toString())
    }

    private fun initSpinner() {
        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            arrayOf(getString(R.string.telkom), getString(R.string.safaricom))
        )
        binding.airtimeSpinner.adapter = spinnerAdapter

        binding.airtimeSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
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

        binding.bannerButtonClose.setOnClickListener {
            binding.infoBanner.visibility = View.GONE
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