package com.resonai.vera.demo.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.resonai.vera.demo.ui.VeraContainerFragment
import com.resonai.vera.demo.ui.FeaturesFragment

class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

  override fun getItemCount(): Int = 2

  override fun createFragment(position: Int): Fragment {
    return when (position) {
      0 -> VeraContainerFragment()
      else -> FeaturesFragment()
    }
  }
}
