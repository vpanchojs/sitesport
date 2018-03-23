package com.aitec.sitesport.profile.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.ComponentName
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.app.AppCompatActivity
import com.aitec.sitesport.R
import android.support.design.widget.CoordinatorLayout
import android.view.View
import android.widget.LinearLayout
import android.support.v4.content.ContextCompat
import android.view.WindowManager
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.support.v7.graphics.Palette
import com.aitec.sitesport.profile.ui.custom.HeaderView
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
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
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
import com.aitec.sitesport.profile.ui.dialog.BusinessHoursFragment
import com.aitec.sitesport.profile.ui.dialog.RateDayFragment
import com.aitec.sitesport.profile.ui.dialog.RateNightFragment
import com.aitec.sitesport.util.GlideApp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.item_entrepise.*
import javax.inject.Inject


class ProfileActivity : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener, ProfileView {

    companion object {
        var tag_enterprise = "entrepise"
    }

    private var isHideToolbarView = false
    private val REQUEST_CODE_CALL_PHONE_PERMISSIONS = 123
    private val TAG = "ProfileActivity"
    private var enterprise : Enterprise? = null


    @Inject
    lateinit var profilePresenter: ProfilePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setupInjection()

        this.enterprise = intent.getParcelableExtra(ProfileActivity.tag_enterprise)
        collapse_toolbar_profile.title = ""
        header_tv_title.text = enterprise!!.nombres

        Log.e("onCreate", enterprise!!.pk)
        setupToolBar()
        setupImageProfile()
        setupAppBarSizeDynamic()
        //setupBarsFromColorImageProfile() //cambia el color de statusBar(DarkColor) y toolbar(PrimaryColor) de acuerdo a la imagen de perfil
        //setupHeader()
        setupListenerScrollAppBarLayout()
        setupMapBox(savedInstanceState)
        setupBusinessHours()
    }

    private fun setupInjection() {
        val app : MyApplication = this.application as MyApplication
        val profileComponent = app.getProfileComponent(this)
        profileComponent.inject(this)
    }

    // ProfileView.kt implementation

    override fun setEnterprise(enterprise: Enterprise) {
        this.enterprise = enterprise
    }

    override fun setImageProfile(urls: List<Fotos>) {
        //setupImageProfile(image)
        //fotos!![0]
        enterprise!!.fotos
        GlideApp.with(this)
                .load("https://firebasestorage.googleapis.com/v0/b/blockstudy-97c0c.appspot.com/o/user-photos%2FvcOcLK7V6Md4kco3TfR9DRfkhZh2?alt=media&token=dc38586b-9dd6-41e8-9720-f1630b07bddf")
                .placeholder(img_image_profile.drawable)
                .fitCenter()
                .centerCrop()
                .error(img_image_profile.drawable)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(img_image_profile)
        //setupImageProfile(BitmapFactory.decodeResource(resources, R.drawable.piscina))
        setupImageProfile()
    }

    override fun setNameProfile(name: String) {
        collapse_toolbar_profile.title = ""
        (toolbar_header_profile as HeaderView).setTitle(name)
        (float_header_profile as HeaderView).setTitle(name)
    }

    override fun setQualificationProfile(qualification: Float) {
        //rtbQualificationSite.rating = qualification
    }

    override fun setVisits(visits: String) {
        (toolbar_header_profile as HeaderView).setSubTitle(visits)
        (float_header_profile as HeaderView).setSubTitle(visits)
    }

    override fun setupCollapsibleDynamic(){
        setupAppBarSizeDynamic()
        setupListenerScrollAppBarLayout()
    }

    override fun setTableTime(times: TableTime) {
        enterprise
    }

    override fun setPriceHourDay(prices: List<Precio>) {
        enterprise
    }

    override fun setPriceHourNight(prices: List<Precio>) {
        enterprise
    }

    override fun setPhoneNumber(phonesNumber: ArrayList<String>) {
        enterprise
    }

    override fun setWhatsAppNumber(whatAppNumber: String) {
        enterprise
    }

    override fun setFacebookUser(facebookUser: String) {
        enterprise
    }

    override fun setLatLngLocationMap(locationLatLng: LatLng) {
        mvProfile.getMapAsync({
            val iconFactory = IconFactory.getInstance(this)
            val icon = iconFactory.fromResource(R.drawable.ic_ball_futbol)
            it.addMarker(MarkerOptions()
                    .position(LatLng(locationLatLng.latitude, locationLatLng.longitude))
                    .icon(icon))

            val position = CameraPosition.Builder()
                    .target(LatLng(locationLatLng.latitude, locationLatLng.longitude)) // Sets the new camera position
                    .zoom(15.0) // Sets the zoom
                    .build() // Creates a CameraPosition from the builder
            it.animateCamera(CameraUpdateFactory.newCameraPosition(position), 500)
        })
    }

    override fun setMarkerLocationMap(marker: Bitmap) {}

    // setup GUI and Life cycle

    private fun setupListenerScrollAppBarLayout() {
        app_bar_layout_profile.addOnOffsetChangedListener(this)
    }

    private fun setupBusinessHours() {

        btnShowBusinessHours.setOnClickListener {
            //tvDayTitle
            //tvHourStartEnd
            val businessHoursFragment = BusinessHoursFragment
                    .newInstance(enterprise!!.hora!!, enterprise!!.horario!!)
            businessHoursFragment.show(supportFragmentManager, "BusinessHoursFragment")
        }

        btnShowRateDay.setOnClickListener {
            val rateDayFragment = RateDayFragment.newInstance()
            rateDayFragment.show(supportFragmentManager, "RateDayFragment")
        }

        btnShowRateNight.setOnClickListener {
            val rateNightFragment = RateNightFragment.newInstance()
            rateNightFragment.show(supportFragmentManager, "RateNightFragment")
        }

        ibtnWhatsapp.setOnClickListener {
            openWhatsApp("991933291")
        }

        ibtnFacebook.setOnClickListener {
            openFacebook("jose.aguilar3")
        }

        ibtnPhone.setOnClickListener {
            //if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    callPhone("0980858483")
                    Log.e("permisos", "llamando")
                }else{
                    Log.e("permisos", "lanzando permisos")
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CODE_CALL_PHONE_PERMISSIONS)
                    return@setOnClickListener
                }
            //}else{
                // No se necesita requerir permiso OS menos a 6.0.
            //}
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_CALL_PHONE_PERMISSIONS -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "Permission has been denied by user")
                } else {
                    callPhone("0980858483")
                    Log.i(TAG, "Permission has been granted by user")
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun setupMapBox(savedInstanceState: Bundle?){
        mvProfile.onCreate(savedInstanceState)
    }

    private fun setupAppBarSizeDynamic(){
        app_bar_layout_profile.post({
            val heightPx = (img_image_profile.height / 2.2).toInt()
            setAppBarOffset(heightPx)
        })
    }

    private fun setupImageProfile(){
        //img_image_profile.setImageBitmap(BitmapFactory.decodeResource(resources, R.drawable.piscina))
        //val displayMetrics = DisplayMetrics()
        //this.windowManager.defaultDisplay.getMetrics(displayMetrics)
        //img_image_profile.layoutParams.height = displayMetrics.widthPixels

        img_image_profile.setImageDrawable(resources.getDrawable(R.drawable.bg_image_progile))
        val displayMetrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(displayMetrics)
        img_image_profile.layoutParams.height = displayMetrics.widthPixels
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setupBarsFromColorImageProfile(){
        val bitmap = (img_image_profile.drawable as BitmapDrawable).bitmap
        Palette.from(bitmap).generate { palette ->
            val mutedColor = palette.getMutedColor(ContextCompat.getColor(this, R.color.colorPrimary))
            setupColorToolbar(mutedColor)
            setupColorStatusBar(getDarkColor(mutedColor))
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
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

    /*private fun setupHeader(){
        collapse_toolbar_profile.title = ""
        (toolbar_header_profile as HeaderView).bindTo("Centro XYZ", "24 visitas")
        (float_header_profile as HeaderView).bindTo("Centro XYZ", "24 visitas")
    }*/

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

    private fun openWhatsApp(cellPhone : String) {
        val ECU = "593"
        val formattedNumber : String  = ECU + cellPhone
        try{
            val sendIntent : Intent
            sendIntent = Intent("android.intent.action.MAIN")
            sendIntent.setComponent(ComponentName("com.whatsapp", "com.whatsapp.Conversation"))
            sendIntent.setAction(Intent.ACTION_SEND)
            sendIntent.setType("text/plain")
            sendIntent.putExtra(Intent.EXTRA_TEXT,"")
            sendIntent.putExtra("jid", formattedNumber +"@s.whatsapp.net")
            sendIntent.setPackage("com.whatsapp")
            startActivity(sendIntent)
        }
        catch(e : Exception)
        {
            //Toast.makeText(this,"Error/n"+ e.toString(),Toast.LENGTH_SHORT).show();
            Toast.makeText(this,"No tienes whatsapp instalado",Toast.LENGTH_SHORT).show();
        }
    }

    private fun openFacebook(user: String) {
        /*
        ¡También debería agregar intent.setPackage("com.facebook.katana");que hace que la actividad de Facebook se abra más rápido!
         */
        var intent : Intent?
        try {
            getPackageManager()
                    .getPackageInfo("com.facebook.katana", 0) //Checks if FB is even installed.
            intent =  Intent(Intent.ACTION_VIEW,
                    Uri.parse("fb://profile/100008411250163")) //Trys to make intent with FB's URI
        } catch (e: Exception) {
            intent =  Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/" + user)) //catches and opens a url to the desired page
        }

        if(intent != null) startActivity(intent)

    }

    @SuppressLint("MissingPermission")
    private fun callPhone(phone : String) {
        startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone)))
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
        sendIntent.putExtra(android.content.Intent.EXTRA_TEXT, "¡Que mas ve!")
        startActivity(Intent.createChooser(sendIntent,"Compartir a través de..."));
    }

    public override fun onResume() {
        super.onResume()
        profilePresenter.onResume()
        profilePresenter.getProfile(enterprise)
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
        outState.putParcelable("enterprise", enterprise)
        outState.putBoolean("isHideToolbarView", isHideToolbarView)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i(TAG, "onRestoreInstanceState")
        this.enterprise = savedInstanceState?.getParcelable("enterprise")
        this.isHideToolbarView = savedInstanceState!!.getBoolean("isHideToolbarView")
    }
}