package com.app.vera.demo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.vera.databinding.FragmentFeaturesBinding
import com.app.vera.demo.common.BaseFragment

class FeaturesFragment : BaseFragment() {

    private var binding: FragmentFeaturesBinding? = null

    private var deepLinkUrl =
        "https://vera.resonai.com/#/play/azrieli-hashalom-tlv/com.resonai.navigation/%7B%22id%22%3A%22660%22%7D"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeaturesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
    }

    private fun bindView() = binding?.apply {
        button1.setOnClickListener {
            viewModel.sendMessage(deepLinkUrl, true)
        }

        button2.setOnClickListener {
            viewModel.sendMessage(deepLinkUrl)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
