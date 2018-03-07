package com.aitec.sitesport.profile.ui

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.support.design.widget.AppBarLayout
import android.view.View
import android.os.Build
import android.util.TypedValue
import com.aitec.sitesport.R

/**
* Created by Yavac on 5/3/2018.
*/
class ViewBehavior(var context: Context?, attrs: AttributeSet?) : CoordinatorLayout.Behavior<HeaderView>(context, attrs) {

    companion object {
        val MAX_SCALE = 0.5f
    }

    private var mStartMarginLeft: Int = 0
    private var mEndMarginLeft: Int = 0
    private var mMarginRight: Int = 0
    private var mStartMarginBottom: Int = 0
    private var isHide: Boolean = false

    override fun layoutDependsOn(parent: CoordinatorLayout, child: HeaderView, dependency: View): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: HeaderView?, dependency: View?): Boolean {
        shouldInitProperties(child, dependency)

        val maxScroll = (dependency as AppBarLayout).totalScrollRange
        val percentage = Math.abs(dependency.y) / maxScroll.toFloat()

        // Set scale for the title
        var size = (1 - percentage) * MAX_SCALE + 1
        val auxSize = size

        if (size > 1.35f) size = 1.35f
        child!!.setScaleXTitle(size)
        child.setScaleYTitle(size)

        if (size > 1.15f) size = 1.15f
        child.setScaleXRatingBar(size)
        child.setScaleYRatingBar(size)

        child.setMarginTitle(auxSize)

        // Set position for the header view
        var childPosition = (dependency.height + dependency.y
                - child.height
                - (getToolbarHeight() - child.height) * percentage / 2)

        childPosition = childPosition - mStartMarginBottom * (1f - percentage)
        val lp = child.layoutParams as CoordinatorLayout.LayoutParams

        //1.2313597f

        if (Math.abs(dependency.getY()) >= maxScroll / 2) {
            val layoutPercentage = (Math.abs(dependency.getY()) - maxScroll / 2) / Math.abs(maxScroll / 2)
            lp.leftMargin = (layoutPercentage * mEndMarginLeft).toInt() + mStartMarginLeft
            //child.setTextSize(getTranslationOffset(mTitleStartSize, mTitleEndSize, layoutPercentage))
        } else {
            lp.leftMargin = mStartMarginLeft
        }
        lp.rightMargin = mMarginRight
        child.layoutParams = lp
        child.y = childPosition



        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            if (isHide && percentage < 1) {
                child.visibility = View.VISIBLE
                isHide = false
            } else if (!isHide && percentage == 1f) {
                child.visibility = View.GONE
                isHide = true
            }
        }
        return true
    }

    private fun shouldInitProperties(child: HeaderView?, dependency: View?) {

        if (mStartMarginLeft == 0) {
            mStartMarginLeft = context!!.resources.getDimensionPixelOffset(R.dimen.header_view_start_margin_left)
        }

        if (mEndMarginLeft == 0) {
            mEndMarginLeft = context!!.resources.getDimensionPixelOffset(R.dimen.header_view_end_margin_left)
        }

        if (mStartMarginBottom == 0) {
            mStartMarginBottom = context!!.resources.getDimensionPixelOffset(R.dimen.header_view_start_margin_bottom)
        }

        if (mMarginRight == 0) {
            mMarginRight = context!!.resources.getDimensionPixelOffset(R.dimen.header_view_end_margin_right)
        }

    }

    private fun getToolbarHeight(): Int {
        var result = 0
        val tv = TypedValue()
        if (context!!.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            result = TypedValue.complexToDimensionPixelSize(tv.data, context!!.resources.displayMetrics)
        }
        return result
    }

}