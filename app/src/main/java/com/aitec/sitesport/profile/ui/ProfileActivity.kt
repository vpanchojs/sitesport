package com.aitec.sitesport.profile.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.ComponentName
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.app.AppCompatActivity
import com.aitec.sitesport.R
import android.support.design.widget.CollapsingToolbarLayout.LayoutParams
import android.view.View
import android.widget.LinearLayout
import android.support.v4.content.ContextCompat
import android.view.WindowManager
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.support.v7.graphics.Palette
import android.util.DisplayMetrics
import com.mapbox.mapboxsdk.annotations.IconFactory
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.content_profile.*
import kotlinx.android.synthetic.main.header_profile.*
import android.widget.Toast
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.support.annotation.NonNull
import android.support.annotation.RequiresApi
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.ActivityCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.aitec.sitesport.MyApplication
import com.aitec.sitesport.entities.enterprise.Enterprise
import com.aitec.sitesport.entities.enterprise.Fotos
import com.aitec.sitesport.entities.enterprise.Precio
import com.aitec.sitesport.entities.TableTime
import com.aitec.sitesport.entities.enterprise.Hora
import com.aitec.sitesport.profile.ProfilePresenter
import com.aitec.sitesport.profile.ui.dialog.RateNightFragment
import com.aitec.sitesport.util.BaseActivitys
import com.aitec.sitesport.util.GlideApp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.item_entrepise.*
import java.net.URL
import javax.inject.Inject


class ProfileActivity : AppCompatActivity(), ProfileView{


    companion object {
        const val SHARE = ""
        const val TAG = "ProfileActivity"
        const val EMOTICON_HAPPY = 0x1F60A
        const val ENTERPRISE = "enterprise"
        const val REQUEST_CODE_CALL_PHONE_PERMISSIONS = 123
    }

    @Inject
    lateinit var profilePresenter: ProfilePresenter
    private var enterprise: Enterprise? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setupInjection()
        onCreateMapBox(savedInstanceState)

        this.enterprise = intent.getParcelableExtra(ENTERPRISE)
        /*
            enterprise extras bundle
            lateinit var pk: String
            lateinit var nombres: String
            lateinit var address: Address
            lateinit var urldetalle: String
            lateinit var foto_perfil: String
         */

        setupToolBar()
        //setupImageProfile()
        //setupAppBarSizeDynamic()
        //setupMapBox(savedInstanceState)
        //setupBusinessHours()
        profilePresenter.getProfile(enterprise!!.urldetalle)
    }

    private fun setupInjection() {
        val app: MyApplication = this.application as MyApplication
        val profileComponent = app.getProfileComponent(this)
        profileComponent.inject(this)
    }


    private fun isScrollAppBar(boolean: Boolean) {
        ViewCompat.setNestedScrollingEnabled(nsvEnterprise, boolean)
        /*val params = app_bar_layout_profile.layoutParams as CoordinatorLayout.LayoutParams
        if (params.behavior == null)
            params.behavior = AppBarLayout.Behavior()
        val behaviour = params.behavior as AppBarLayout.Behavior
        behaviour.setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
            override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                return boolean
            }
        })*/
    }

    /*override fun showTextInfoLoading() {
        tvMsg.visibility = View.VISIBLE
        btnReloadEnterprise.visibility = View.VISIBLE
    }

    override fun hideTextInfoLoading() {
        tvMsg.visibility = View.GONE
        btnReloadEnterprise.visibility = View.GONE
    }

    override fun setTextInfoLoading(msg: String) {
        tvMsg.text = msg
    }

    override fun setStateEnterprise(state: String?) {
        tvStateEnterprise.text = state
    }

    override fun setPriceDayStandar(priceDay: String?) {
        tvPriceDayStandar.text = priceDay
    }

    override fun setPriceNightStandar(priceNight: String?) {
        tvPriceNightStandar.text = priceNight
    }

    override fun setEnterprise(enterprise: Enterprise) {
        this.enterprise!!.descripcion = enterprise.descripcion
        this.enterprise!!.abierto = enterprise.abierto
        this.enterprise!!.fotos = enterprise.fotos
        this.enterprise!!.telefonos = enterprise.telefonos
        this.enterprise!!.red_social = enterprise.red_social
        //this.enterprise!!.categoria = enterprise.categoria
        this.enterprise!!.precio = enterprise.precio
        //this.enterprise!!.horario = enterprise.horario
        this.enterprise!!.hora = enterprise.hora
    }

    override fun setImageProfile(urls: List<Fotos>?) {
        if (urls != null && urls.isNotEmpty() && !(urls[0].imagen.isEmpty())) {
            GlideApp.with(this)
                    .load(URL(urls[0].imagen).toString())
                    .placeholder(img_image_profile.drawable)
                    //.fitCenter()
                    .centerCrop()
                    .error(img_image_profile.drawable)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(img_image_profile)
            //setupImageProfile()
        }
    }

    */

/*
    private fun setupMapBox() {
        mvProfile.setOnTouchListener(View.OnTouchListener { v, event -> return@OnTouchListener true })
    }



    /*override fun setupMap(locationLatLng: LatLng?) {
        mvProfile.getMapAsync({
            val iconFactory = IconFactory.getInstance(this)
            Log.e("ESTADO", enterprise!!.abierto.toString())
            val icon = iconFactory.fromResource(if(enterprise!!.abierto) R.drawable.ic_futbol_open else R.drawable.ic_futbol_close)
            it.addMarker(MarkerOptions()
                    .position(LatLng(locationLatLng!!.latitude, locationLatLng.longitude))
                    .icon(icon))

            val position = CameraPosition.Builder()
                    .target(LatLng(locationLatLng.latitude, locationLatLng.longitude)) // Sets the new camera position
                    .zoom(15.0) // Sets the zoom
                    .build() // Creates a CameraPosition from the builder
            it.animateCamera(CameraUpdateFactory.newCameraPosition(position), 500)
        })
    }

    // setup GUI and Life cycle

    */*/

/*
    private fun setupBusinessHours() {

        /*btnShowBusinessHours.setOnClickListener {
            //tvDayTitle
            //tvHourStartEnd
            if(enterprise != null && enterprise!!.hora != null && enterprise!!.hora != null) {
                val businessHoursFragment = BusinessHoursFragment
                        .newInstance(enterprise!!.hora!!, enterprise!!.horario!!)
                businessHoursFragment.show(supportFragmentManager, "BusinessHoursFragment")
            }else{
                BaseActivitys.showToastMessage(this,"Horarios no disponibles", Toast.LENGTH_SHORT)
            }
        }

        btnShowRateDay.setOnClickListener {
            if(enterprise != null && enterprise!!.precio != null) {
                val rateDayFragment = RateDayFragment.newInstance(enterprise!!.precio!!)
                rateDayFragment.show(supportFragmentManager, "RateDayFragment")
            }else{
                BaseActivitys.showToastMessage(this,"Precios no disponibles", Toast.LENGTH_SHORT)
            }
        }

        btnShowRateNight.setOnClickListener {
            if(enterprise != null && enterprise!!.precio != null) {

                val rateNightFragment = RateNightFragment.newInstance(enterprise!!.precio!!)
                rateNightFragment.show(supportFragmentManager, "RateNightFragment")
            }else{
                BaseActivitys.showToastMessage(this,"Precios no disponibles", Toast.LENGTH_SHORT)
            }
        }

        ibtnWhatsapp.setOnClickListener {
            openWhatsApp()
        }

        ibtnFacebook.setOnClickListener {
            openFacebook()
        }

        ibtnPhone.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                callPhone()
            }else{
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CODE_CALL_PHONE_PERMISSIONS)
                return@setOnClickListener
            }
        }

        */
        * */


    override fun setEnterprise(enterprise: Enterprise) {
        this.enterprise = enterprise
    }


    private fun setupAppBarSizeDynamic(){
        app_bar_layout_profile.post({
            val heightPx = (img_image_profile.height / 2.2).toInt()
            setAppBarOffset(heightPx)
        })
    }

    private fun setupImageProfile(){
        img_image_profile.setImageDrawable(resources.getDrawable(R.drawable.bg_image_progile))
        val displayMetrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(displayMetrics)
        img_image_profile.layoutParams.height = displayMetrics.widthPixels
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

    private fun openWhatsApp() {

        if(enterprise != null && enterprise!!.telefonos!![0].celular.isNotBlank()) {
            val ECU = "593"
            val formattedNumber: String = ECU + enterprise!!.telefonos!![0].celular
            try {
                val sendIntent: Intent
                sendIntent = Intent("android.intent.action.MAIN")
                sendIntent.setComponent(ComponentName("com.whatsapp", "com.whatsapp.Conversation"))
                sendIntent.setAction(Intent.ACTION_SEND)
                sendIntent.setType("text/plain")
                sendIntent.putExtra(Intent.EXTRA_TEXT, "")
                sendIntent.putExtra("jid", formattedNumber + "@s.whatsapp.net")
                sendIntent.setPackage("com.whatsapp")
                startActivity(sendIntent)
            } catch (e: Exception) {
                //Toast.makeText(this,"Error/n"+ e.toString(),Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "No tienes whatsapp instalado", Toast.LENGTH_SHORT).show();
            }
        }else {
            BaseActivitys.showToastMessage(this, "Whatsapp no disponible", Toast.LENGTH_SHORT)
        }
    }

    private fun openFacebook() {

        if(enterprise != null && enterprise!!.red_social!![0].facebook.isNotBlank()) {

            var intent: Intent?
            try {
                getPackageManager()
                        .getPackageInfo("com.facebook.katana", 0) //Checks if FB is even installed.
                intent = Intent(Intent.ACTION_VIEW,
                        Uri.parse("fb://profile/" + enterprise!!.red_social!![0].facebook)) //Trys to make intent with FB's URI
            } catch (e: Exception) {
                intent = Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.facebook.com/" + enterprise!!.red_social!![0].facebook)) //catches and opens a url to the desired page
            }

            if (intent != null) startActivity(intent)
        }else {
            BaseActivitys.showToastMessage(this, "Facebook no disponible", Toast.LENGTH_SHORT)
        }

    }

    @SuppressLint("MissingPermission")
    private fun callPhone() {
        if(enterprise != null && enterprise!!.telefonos!![0].convencional.isNotBlank()) {
            startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:" + enterprise!!.telefonos!![0].convencional)))
        }else{
            BaseActivitys.showToastMessage(this, "Teléfono no disponible", Toast.LENGTH_SHORT)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_profile, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share ->{
                shareProfile()
                return true
            }
            android.R.id.home ->{
                onBackPressed()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun shareProfile(){
        val sendIntent = Intent(android.content.Intent.ACTION_SEND)
        sendIntent.type = "text/plain"
        sendIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"share_option");
        sendIntent.putExtra(android.content.Intent.EXTRA_TEXT, SHARE)
        startActivity(Intent.createChooser(sendIntent,"Compartir a través de..."));
    }

    public override fun onResume() {
        super.onResume()
        profilePresenter.onResume()
        mvProfile.onResume()
    }

    override fun onStart() {
        super.onStart()
        mvProfile.onStart()
    }

    override fun onStop() {
        super.onStop()
        mvProfile.onStop()
    }

    public override fun onPause() {
        super.onPause()
        profilePresenter.onPause()
        mvProfile.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mvProfile.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        profilePresenter.onDestroy()
        mvProfile.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        mvProfile.onSaveInstanceState(outState!!)
        //outState.putParcelable("enterprise", enterprise)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i(TAG, "onRestoreInstanceState")
        //this.enterprise = savedInstanceState?.getParcelable("enterprise")
    }

    private fun onCreateMapBox(savedInstanceState: Bundle?){
        mvProfile.onCreate(savedInstanceState)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_CALL_PHONE_PERMISSIONS -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "Permission has been denied by user")
                } else {
                    callPhone()
                    Log.i(TAG, "Permission has been granted by user")
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}
