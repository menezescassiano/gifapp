package com.cassianomenezes.gifapp.home.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.cassianomenezes.gifapp.home.view.fragment.FavListFragment
import com.cassianomenezes.gifapp.home.view.fragment.HomeListFragment

class PagerAdapter(manager: FragmentManager, private val tabCount: Int) : FragmentStatePagerAdapter(manager, tabCount) {

    override fun getCount(): Int {
        return tabCount
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> HomeListFragment()
            else -> FavListFragment()
        }
    }
}