package com.aitec.sitesport.reservationHistory

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View


class ItemDecorator(private val dividerView: Drawable) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        //if (parent!!.getChildAdapterPosition(view) == 0) return
        outRect!!.top = dividerView.intrinsicHeight
    }

    override fun onDraw(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        val dividerLeft = parent!!.paddingLeft
        val dividerRight = parent.width - parent.paddingRight

        /*for (i in 0 until childCount - 1) {
            val reservation = (parent.adapter as AdapterReservationHistory).getItem(i)

        }*/

        val childCount = parent.childCount
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val dividerTop = child.bottom + params.bottomMargin
            val dividerBottom = dividerTop + dividerView.intrinsicHeight

            dividerView.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
            dividerView.draw(c!!)
        }

    }
}