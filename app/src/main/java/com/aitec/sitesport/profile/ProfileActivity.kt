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
import android.os.Build
import android.graphics.BitmapFactory
import android.graphics.Color
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.graphics.Palette
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.aitec.sitesport.profile.ui.HeaderView


class ProfileActivity : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener {

    private var isHideToolbarView = false
    private lateinit var toolbar : Toolbar
    private lateinit var appBarLayout : AppBarLayout
    private lateinit var coordinator : CoordinatorLayout
    private lateinit var imageProfile : ImageView
    private lateinit var collapsibleToolbar : CollapsingToolbarLayout
    private lateinit var headerToolbar : HeaderView
    private lateinit var floatHeaderToolbar : HeaderView
    private lateinit var headerTitle : TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setupToolBar()

        appBarLayout = findViewById<View>(R.id.app_bar_layout_profile) as AppBarLayout
        coordinator = findViewById<View>(R.id.cordinator_layout_profile) as CoordinatorLayout
        imageProfile = findViewById<View>(R.id.toolbar_image_profile) as ImageView
        collapsibleToolbar = findViewById<View>(R.id.collapse_toolbar_profile) as CollapsingToolbarLayout
        headerToolbar = findViewById<View>(R.id.toolbar_header_profile) as HeaderView
        floatHeaderToolbar = findViewById<View>(R.id.float_header_profile) as HeaderView
        headerTitle = findViewById<View>(R.id.header_tv_nameview_title) as TextView



        val dWidth = windowManager.defaultDisplay

        appBarLayout.post(Runnable {
            val heightPx = (imageProfile.getHeight() / 2.2).toInt()
            setAppBarOffset(heightPx)
        })

        imageProfile.getLayoutParams().height = dWidth.width
        val bitmap = BitmapFactory.decodeResource(resources,
                R.drawable.baloncesto)
        Palette.from(bitmap).generate(object : Palette.PaletteAsyncListener {
            override fun onGenerated(palette: Palette) {

                val mutedColor = palette.getMutedColor(resources.getColor(R.color.colorPrimary))

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    val hsv = FloatArray(3)
                    var color = mutedColor
                    Color.colorToHSV(color, hsv)
                    hsv[2] *= 0.8f // value component
                    color = Color.HSVToColor(hsv)


                    collapsibleToolbar.setContentScrimColor(mutedColor) //LigtColor
                    val window = this@ProfileActivity.getWindow()
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.setStatusBarColor(color) //DarkColor
                } else {
                    collapsibleToolbar.setContentScrimColor(ContextCompat.getColor(this@ProfileActivity, R.color.colorPrimaryDark))
                }

            }
        })


        // TITTLE AND SUBTITTLE
        collapsibleToolbar.title = " "
        headerToolbar .bindTo("Centro XYZ", "24 visitas")
        floatHeaderToolbar.bindTo("Centro XYZ", "24 visitas")
        appBarLayout.addOnOffsetChangedListener(this)
    }

    private fun setupToolBar(){
        toolbar = findViewById<View>(R.id.toolbar_profile) as Toolbar

        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.title = ""
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }
    }

    private fun setAppBarOffset(offsetPx: Int) {
        val params = appBarLayout.getLayoutParams() as CoordinatorLayout.LayoutParams
        params.behavior = AppBarLayout.Behavior()
        val behavior = params.behavior as AppBarLayout.Behavior
        behavior.onNestedPreScroll(coordinator, appBarLayout, collapsibleToolbar, 0, offsetPx, intArrayOf(0, 0), 0)

    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {

        val maxScroll = appBarLayout.getTotalScrollRange()
        val percentage = Math.abs(verticalOffset).toFloat() / maxScroll.toFloat()

        if (percentage == 1f && isHideToolbarView) {
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.setMargins(0, 0, 0, 0)
            headerTitle.setLayoutParams(params)
            headerToolbar.setVisibility(View.VISIBLE)
            isHideToolbarView = !isHideToolbarView

        } else if (percentage < 1f && !isHideToolbarView) {
            headerToolbar.setVisibility(View.GONE)
            isHideToolbarView = !isHideToolbarView
        }
    }

}
