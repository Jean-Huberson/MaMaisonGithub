package com.freehome.mamaison.activities.menuTab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.freehome.mamaison.R
import com.google.android.material.tabs.TabLayout

class TabFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val inflatelayout = inflater.inflate(R.layout.tab_fragment, null)
        tablayout = inflatelayout.findViewById<View>(R.id.tabs) as TabLayout
        viewpager = inflatelayout.findViewById<View>(R.id.viewpager) as ViewPager

        viewpager.adapter = TabPagerAdapter(childFragmentManager)
        tablayout.setupWithViewPager(viewpager)

        return  inflatelayout
    }

    internal inner class TabPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm){
        override fun getItem(position: Int): Fragment {
            return when(position){
                0->{
                    FragmentOngletOne()
                }

                1->{
                    FragmentOngletTwo()
                }
                else->{
                    return FragmentOngletThree()
                }
            }

        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when(position){
                0->{
                    "Ventes"
                }

                1->{
                    "Locations"
                }
                else->{
                    return "Residences"
                }
            }
        }

        override fun getCount(): Int {
            return page_count_out
        }

    }


    companion object {
        lateinit var tablayout:TabLayout
        lateinit var viewpager:ViewPager
        val page_count_out:Int = 3

    }
}