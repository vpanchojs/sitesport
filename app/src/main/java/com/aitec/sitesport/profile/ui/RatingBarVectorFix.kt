package com.aitec.sitesport.profile.ui

import android.content.Context
import android.util.AttributeSet
import android.graphics.drawable.LayerDrawable
import com.aitec.sitesport.R
import android.graphics.drawable.ClipDrawable
import android.view.Gravity
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.graphics.*
import android.support.v7.graphics.drawable.DrawableWrapper
import android.graphics.drawable.shapes.RoundRectShape
import android.graphics.drawable.shapes.Shape
import android.support.v4.view.ViewCompat
import android.opengl.ETC1.getWidth
import android.opengl.ETC1.getHeight
import android.os.Build


/**
 * Created by Yavac on 6/3/2018.
 */
class RatingBarVectorFix @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : android.support.v7.widget.AppCompatRatingBar(context, attrs, defStyleAttr) {

    private var mSampleTile: Bitmap? = null

    init {
        init()
    }

    private fun init() {
        val drawable = tileify(progressDrawable, false) as LayerDrawable
        drawable.findDrawableByLayerId(android.R.id.background).setColorFilter(resources.getColor(R.color.colorStarOff),
                PorterDuff.Mode.SRC_ATOP)
        drawable.findDrawableByLayerId(android.R.id.progress).setColorFilter(resources.getColor(R.color.colorStarOn),
                PorterDuff.Mode.SRC_ATOP)
        progressDrawable = drawable
    }

    @SuppressLint("RestrictedApi")
    private fun tileify(drawable: Drawable, clip: Boolean): Drawable {
        if (drawable is DrawableWrapper) {
            @SuppressLint("RestrictedApi") var inner = (drawable as DrawableWrapper).getWrappedDrawable()
            if (inner != null) {
                inner = tileify(inner, clip)
                (drawable as DrawableWrapper).setWrappedDrawable(inner)
            }
        } else if (drawable is LayerDrawable) {
            val N = drawable.numberOfLayers
            val outDrawables = arrayOfNulls<Drawable>(N)

            for (i in 0 until N) {
                val id = drawable.getId(i)
                outDrawables[i] = tileify(drawable.getDrawable(i),
                        id == android.R.id.progress || id == android.R.id.secondaryProgress)
            }
            val newBg = LayerDrawable(outDrawables)

            for (i in 0 until N) {
                newBg.setId(i, drawable.getId(i))
            }

            return newBg

        } else if (drawable is BitmapDrawable) {
            val tileBitmap = drawable.bitmap
            if (mSampleTile == null) {
                mSampleTile = tileBitmap
            }

            val shapeDrawable = ShapeDrawable(getDrawableShape())
            val bitmapShader = BitmapShader(tileBitmap,
                    Shader.TileMode.REPEAT, Shader.TileMode.CLAMP)
            shapeDrawable.paint.shader = bitmapShader
            shapeDrawable.paint.colorFilter = drawable.paint.colorFilter
            return if (clip)
                ClipDrawable(shapeDrawable, Gravity.LEFT,
                        ClipDrawable.HORIZONTAL)
            else
                shapeDrawable
        } else {
            return tileify(getBitmapDrawableFromVectorDrawable(drawable), clip)
        }

        return drawable
    }

    private fun getBitmapDrawableFromVectorDrawable(drawable: Drawable): BitmapDrawable {
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
        drawable.draw(canvas)
        return BitmapDrawable(resources, bitmap)
    }

    @Synchronized override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (mSampleTile != null) {
            val width = mSampleTile!!.getWidth() * numStars
            setMeasuredDimension(ViewCompat.resolveSizeAndState(width, widthMeasureSpec, 0),
                    measuredHeight)
        }
    }

    private fun getDrawableShape(): Shape {
        val roundedCorners = floatArrayOf(5f, 5f, 5f, 5f, 5f, 5f, 5f, 5f)
        return RoundRectShape(roundedCorners, null, null)
    }

}