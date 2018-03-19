package com.aitec.sitesport.profile.ui.custom

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.LinearLayout
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.header_profile.view.*


@SuppressLint("ViewConstructor")
class HeaderView : LinearLayout{

    @JvmOverloads
    constructor(
            context: Context,
            attrs: AttributeSet? = null,
            defStyleAttr: Int = 0)
            : super(context, attrs, defStyleAttr)

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
            context: Context,
            attrs: AttributeSet?,
            defStyleAttr: Int,
            defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes)

    fun setTitle(title : String){
        header_tv_title.text = title
    }

    fun setSubTitle(subTitle : String){
        //header_tv_sub_title.text = subTitle
    }

    /*fun bindTo(title: String, subTitle: String) {
        hideOrSetText(header_tv_title, title)
        hideOrSetText(header_tv_sub_title, subTitle)
    }

    private fun hideOrSetText(tv: TextView, text: String?) {
        if (text == null || text == "")
            tv.visibility = View.GONE
        else
            tv.text = text
    }*/

    fun setScaleXTitle(scaleXTitle: Float) {
        header_tv_title.scaleX = scaleXTitle
        header_tv_title.pivotX = 0f
    }

    fun setScaleYTitle(scaleYTitle: Float) {
        header_tv_title.scaleY = scaleYTitle
        header_tv_title.pivotY = 30f
    }

    fun setScaleXRatingBar(scaleXTitle: Float) {
        //rtbQualificationSite.scaleX = scaleXTitle
        //rtbQualificationSite.pivotX = 0f
    }

    fun setScaleYRatingBar(scaleYTitle: Float) {
        //rtbQualificationSite.scaleY = scaleYTitle
        //rtbQualificationSite.pivotY = 30f
    }


    fun setMarginTitle(size: Float) {
        var margin = 0f
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        when {
            size == 1.00f -> margin = 0f
            size < 1.05f -> margin = 3f
            size < 1.10f -> margin = 6f
            size < 1.15f -> margin = 9f
            size <= 1.20f -> margin = 12f
            size <= 1.25f -> margin = 15f
            size <= 1.30f -> margin = 18f
            size <= 1.35f -> margin = 21f
            size <= 1.40f -> margin = 24f
            size <= 1.45f -> margin = 27f
            size <= 1.50f -> margin = 30f
        }

        params.setMargins(0, 0, 0, margin.toInt())
        header_tv_title.layoutParams = params
    }

}