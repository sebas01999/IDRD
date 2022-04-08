package com.example.idrd.presentation.mainprincipal.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class viewAdapter( support: FragmentManager): FragmentPagerAdapter(support,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {


    private val mFragmenList = ArrayList<Fragment>()
    private val mFragmentTittleList = ArrayList<String>()

    override fun getItem(position: Int): Fragment {

        return mFragmenList[position]
    }

    override fun getCount(): Int {
        return mFragmenList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {

        return mFragmentTittleList[position]
    }

    fun addFragmentList(fragment: Fragment, title:String){
        mFragmenList.add(fragment)
        mFragmentTittleList.add(title)

    }

}