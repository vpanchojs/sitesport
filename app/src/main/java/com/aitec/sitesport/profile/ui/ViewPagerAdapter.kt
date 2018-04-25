package com.aitec.sitesport.profile.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.aitec.sitesport.entities.enterprise.Foto

class ViewPagerAdapter(fm: FragmentManager, val images: List<Foto>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return ImageFragment.newInstance(images[position].imagen)
    }

    override fun getCount(): Int {
        return images.size
    }

}