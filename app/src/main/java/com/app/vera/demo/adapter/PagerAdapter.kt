package com.app.vera.demo.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.vera.demo.ui.VeraContainerFragment
import com.app.vera.demo.ui.FeaturesFragment

class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

  override fun getItemCount(): Int = 2

  override fun createFragment(position: Int): Fragment {
    return when (position) {
      0 -> VeraContainerFragment()
      else -> FeaturesFragment()
    }
  }
}
