package com.aitec.sitesport.profile.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.ViewGroup
import com.aitec.sitesport.entities.enterprise.Foto
import android.widget.RelativeLayout
import android.support.v4.view.ViewPager
import android.util.Log
import android.widget.FrameLayout


class ViewPagerAdapter(fm: FragmentManager, var images: List<Foto>) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        Log.e("getItem = ", position.toString())
        return ImageFragment.newInstance(images[position].imagen)
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }


    override fun getCount(): Int {
        return images.size
    }

}