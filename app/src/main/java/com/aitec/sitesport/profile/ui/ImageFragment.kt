package com.aitec.sitesport.profile.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.aitec.sitesport.R
import com.aitec.sitesport.util.GlideApp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.fragment_image_profile.*
import java.net.URL

class ImageFragment : Fragment() {

    private var url: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        url = arguments!!.getString("url")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadImage(url!!,imgProfile)
    }



    private fun loadImage(url: String, imageView: ImageView){
        Log.e("ImageFragment", "url = " + url)
        GlideApp.with(this)
                .load(URL(url).toString())
                .placeholder(resources.getDrawable(R.mipmap.ic_launcher))
                //.fitCenter()
                .centerCrop()
                .error(resources.getDrawable(R.mipmap.ic_launcher))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(imageView)
    }

    companion object {
        fun newInstance(url: String): ImageFragment {
            val fragment = ImageFragment()
            val args = Bundle()
            args.putString("url", url)
            fragment.arguments = args
            return fragment
        }
    }

}