package com.incoders.withu.util


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.incoders.withu.ui.fragment.AudiosFragment
import com.incoders.withu.ui.fragment.RecorderFragment

class ViewPagerAdapter(fragment: FragmentActivity): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2    // total tabs
    }

    //setting fragments according to its index
    override fun createFragment(position: Int): Fragment {
        return if(position ==0) RecorderFragment()
        else AudiosFragment()
    }
}