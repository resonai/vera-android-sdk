package com.app.vera.demo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.vera.databinding.FragmentContainerBinding
import com.app.vera.demo.adapter.PagerAdapter
import com.app.vera.demo.common.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator

class ContainerFragment : BaseFragment() {

    private var binding: FragmentContainerBinding? = null
    private val pagerAdapter: PagerAdapter by lazy { PagerAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContainerBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservables(onSendMessage = { deepLinkUrl, shouldRestart ->
            binding?.viewPager?.setCurrentItem(0, true)
        })

        setupPager()
    }

    private fun setupPager() = binding?.apply {
        viewPager.adapter = pagerAdapter
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Vera"
                1 -> tab.text = "Features"
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
