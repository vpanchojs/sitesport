package com.aitec.sitesport.profile.ui

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.LinearLayout
import android.support.v4.view.ViewCompat.setPivotY
import android.support.v4.view.ViewCompat.setScaleY
import android.support.v4.view.ViewCompat.setPivotX
import android.support.v4.view.ViewCompat.setScaleX
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_profile.*
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

    fun bindTo(title: String) {
        bindTo(title, "")
    }

    fun bindTo(title: String, subTitle: String) {
        hideOrSetText(header_tv_nameview_title, title)
        hideOrSetText(header_view_sub_title, subTitle)
    }

    private fun hideOrSetText(tv: TextView, text: String?) {
        if (text == null || text == "")
            tv.visibility = View.GONE
        else
            tv.text = text
    }

    fun setScaleXTitle(scaleXTitle: Float) {
        header_tv_nameview_title.setScaleX(scaleXTitle)
        header_tv_nameview_title.setPivotX(0f)
    }

    fun setScaleYTitle(scaleYTitle: Float) {
        header_tv_nameview_title.setScaleY(scaleYTitle)
        header_tv_nameview_title.setPivotY(30f)
    }

    fun setScaleXratingBar(scaleXTitle: Float) {
        rtbProductRating.setScaleX(scaleXTitle)
        rtbProductRating.setPivotX(0f)
    }

    fun setScaleYratingBar(scaleYTitle: Float) {
        rtbProductRating.setScaleY(scaleYTitle)
        rtbProductRating.setPivotY(30f)
    }


    fun setMarginTitle(size: Float) {
        var margin = 0f
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        if (size == 1.00f) {
            margin = 0f
        } else if (size < 1.05f) {
            margin = 3f
        } else if (size < 1.10f) {
            margin = 6f
        } else if (size < 1.15f) {
            margin = 9f
        } else if (size <= 1.20f) {
            margin = 12f
        } else if (size <= 1.25f) {
            margin = 15f
        } else if (size <= 1.30f) {
            margin = 18f
        } else if (size <= 1.35f) {
            margin = 21f
        } else if (size <= 1.40f) {
            margin = 24f
        } else if (size <= 1.45f) {
            margin = 27f
        } else if (size <= 1.50f) {
            margin = 30f
        }

        params.setMargins(0, 0, 0, margin.toInt())
        header_tv_nameview_title.setLayoutParams(params)
    }

}