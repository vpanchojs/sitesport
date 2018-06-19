package com.aitec.sitesport.profileEnterprise.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import com.aitec.sitesport.entities.enterprise.Foto
import android.util.Log
import com.aitec.sitesport.profileEnterprise.ui.dialog.ImageFragment


class ImageAdapter(fm: FragmentManager, var images: List<String>) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        Log.e("getItem = ", position.toString())
        return ImageFragment.newInstance(images[position])
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }


    override fun getCount(): Int {
        return images.size
    }

}