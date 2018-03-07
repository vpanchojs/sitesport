package com.aitec.sitesport.profile

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.app.AppCompatActivity
import com.aitec.sitesport.R
import android.support.design.widget.CoordinatorLayout
import android.view.View
import android.widget.LinearLayout
import android.support.v4.content.ContextCompat
import android.view.WindowManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.support.v7.graphics.Palette
import com.aitec.sitesport.profile.ui.HeaderView
import android.util.DisplayMetrics
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.header_profile.*


class ProfileActivity : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener {

    private var isHideToolbarView = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setupToolBar()
        setupImageProfile()
        setupAppBarSizeDynamic()
        //setupBarsFromColorImageProfile() //cambia el color de statusBar(DarkColor) y toolbar(PrimaryColor) de acuerdo a la imagen de perfil
        setupHeader()
        app_bar_layout_profile.addOnOffsetChangedListener(this)
    }

    private fun setupAppBarSizeDynamic(){
        app_bar_layout_profile.post({
            val heightPx = (img_image_profile.height / 2.2).toInt()
            setAppBarOffset(heightPx)
        })
    }

    private fun setupImageProfile(){
        img_image_profile.setImageBitmap(BitmapFactory.decodeResource(resources, R.drawable.piscina))
        val displayMetrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(displayMetrics)
        img_image_profile.layoutParams.height = displayMetrics.widthPixels
    }

    private fun setupBarsFromColorImageProfile(){
        val bitmap = (img_image_profile.drawable as BitmapDrawable).bitmap
        Palette.from(bitmap).generate { palette ->
            val mutedColor = palette.getMutedColor(ContextCompat.getColor(this, R.color.colorPrimary))
            setupColorToolbar(mutedColor)
            setupColorStatusBar(getDarkColor(mutedColor))
        }
    }

    private fun setupColorStatusBar(darkColor : Int){
        val window = this@ProfileActivity.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = darkColor //DarkColor
    }

    private fun setupColorToolbar(lightColor : Int){
        collapse_toolbar_profile.setContentScrimColor(lightColor) //LigtColor
    }

    private fun getDarkColor(color : Int): Int{
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        hsv[2] *= 0.8f // value component
        return Color.HSVToColor(hsv)
    }

    private fun setupHeader(){
        collapse_toolbar_profile.title = ""
        (toolbar_header_profile as HeaderView).bindTo("Centro XYZ", "24 visitas")
        (float_header_profile as HeaderView).bindTo("Centro XYZ", "24 visitas")
    }

    private fun setupToolBar(){
        setSupportActionBar(toolbar_profile)
        if (supportActionBar != null) {
            supportActionBar!!.title = ""
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }
    }

    private fun setAppBarOffset(offsetPx: Int) {
        val params = app_bar_layout_profile.layoutParams as CoordinatorLayout.LayoutParams
        params.behavior = AppBarLayout.Behavior()
        val behavior = params.behavior as AppBarLayout.Behavior
        behavior.onNestedPreScroll(cordinator_layout_profile, app_bar_layout_profile, collapse_toolbar_profile, 0, offsetPx, intArrayOf(0, 0), 0)
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        val maxScroll = appBarLayout.totalScrollRange
        val percentage = Math.abs(verticalOffset).toFloat() / maxScroll.toFloat()
        if (percentage == 1f && isHideToolbarView) {
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.setMargins(0, 0, 0, 0)
            header_tv_title.layoutParams = params
            toolbar_header_profile.visibility = View.VISIBLE
            isHideToolbarView = !isHideToolbarView
        } else if (percentage < 1f && !isHideToolbarView) {
            toolbar_header_profile.visibility = View.GONE
            isHideToolbarView = !isHideToolbarView
        }
    }

}
