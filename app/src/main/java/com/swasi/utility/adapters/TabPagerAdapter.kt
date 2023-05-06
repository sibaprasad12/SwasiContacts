package com.swasi.utility.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabPagerAdapter(fragmentManager: FragmentActivity) :
    FragmentStateAdapter(fragmentManager) {

    private val arrayListFragment: ArrayList<Fragment> = ArrayList()

    fun setTabbedFragment(argsFragment: Array<Fragment>) {
        arrayListFragment.addAll(argsFragment)
    }

    override fun getItemCount(): Int = arrayListFragment.size

    override fun createFragment(position: Int): Fragment = arrayListFragment[position]
}