package com.swasi.utility.home

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.swasi.utility.R
import com.swasi.utility.adapters.TabPagerAdapter
import com.swasi.utility.contact.ContactFragment
import com.swasi.utility.database.SwasiContactRoomDatabase
import com.swasi.utility.databinding.FragmentHomeBinding
import com.swasi.utility.flashLight.UtilityFragment
import com.swasi.utility.utils.ContactManager
import com.swasi.utility.video.VideoFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Created by Sibaprasad Mohanty on 22/07/21.
 * Spm Limited
 * sp.dobest@gmail.com
 */

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    val tabTitleArray = arrayOf(
        "Contacts",
        "Flash Light",
        "Youtube"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )
        val view: View = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {

        val app_preferences = PreferenceManager.getDefaultSharedPreferences(activity)
        val counter = app_preferences.getInt("isInstalled", 0)
//        if (counter > 0) {
//            GlobalScope.launch {
//                SwasiContactRoomDatabase.getDatabase(requireContext()).contactDao()
//                    .insertAll(ContactManager.getContactList(requireContext()))
//            }
//        }

        val adapter = TabPagerAdapter(activity as FragmentActivity)
        adapter.setTabbedFragment(arrayOf(ContactFragment(), UtilityFragment(), VideoFragment()))
        binding.tvShowViewPager.adapter = adapter
        binding.tvShowViewPager.currentItem = 0

        TabLayoutMediator(binding.tabLayoutTvSHow, binding.tvShowViewPager) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()
    }
}