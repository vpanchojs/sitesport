package com.aitec.sitesport.profileEnterprise.ui

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Toast
import com.aitec.sitesport.MyApplication
import com.aitec.sitesport.R
import com.aitec.sitesport.domain.listeners.onApiActionListener
import com.aitec.sitesport.entities.enterprise.*
import com.aitec.sitesport.profileEnterprise.Constants
import com.aitec.sitesport.profileEnterprise.ProfilePresenter
import com.aitec.sitesport.profileEnterprise.ui.adapter.ImageAdapter
import com.aitec.sitesport.profileEnterprise.ui.dialog.DefaultServicesFragment
import com.aitec.sitesport.profileEnterprise.ui.dialog.TableTimeFragment
import com.aitec.sitesport.reserve.adapter.CourtAdapter
import com.aitec.sitesport.reserve.adapter.OnClickListenerCourt
import com.aitec.sitesport.reserve.ui.ReserveActivity
import com.aitec.sitesport.util.BaseActivitys
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_profile_enterprise.*
import kotlinx.android.synthetic.main.content_profile_enterprise.*
import kotlinx.android.synthetic.main.layout_loading.view.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap


class ProfileActivity : AppCompatActivity(), OnClickListenerCourt, ProfileView, com.google.android.gms.maps.OnMapReadyCallback {

    @Inject
    lateinit var profilePresenter: ProfilePresenter
    private var imageAdapter: ImageAdapter? = null
    private var enterprise: Enterprise = Enterprise()
    private var snackBarInfo: Snackbar? = null
    private var courtAdapter: CourtAdapter? = null
    private var images: ArrayList<String> = arrayListOf()
    private lateinit var gMap: GoogleMap
    private var uidUser: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setVectorCompatibility()
        setContentView(R.layout.activity_profile_enterprise)
        setupInjection()
        profilePresenter.register()
        enterprise.pk = intent.getStringExtra(ENTERPRISE)
        setupUI()
        //callSections()
    }

    private fun callSections() {
        profilePresenter.getBasicProfile(enterprise.pk)
        profilePresenter.getCourtsProfile(enterprise.pk)
        profilePresenter.getTableTimeProfile(enterprise.pk)
        profilePresenter.getServicesProfile(enterprise.pk)
        profilePresenter.getContactsProfile(enterprise.pk)
        profilePresenter.isAuthenticated()
        //profilePresenter.getLikes("sAcL7AsndlapxazBB5ZrHyCix782", enterprise.pk)
    }

    // ======= ProfileView.kt implementation

    override fun authenticated(uidUser: Any?) {
        if (uidUser != null) {
            this.uidUser = uidUser as String
            profilePresenter.getLike(uidUser, enterprise.pk)
        }
    }

    override fun updateBasicProfile() {
        collapse_toolbar_profile.title = enterprise.nombre
        cbRating.text = enterprise.me_gustas.toString()
        //updateImage(enterprise.foto_perfil)
        setMapView()
    }

    override fun updateImages(imageList: ArrayList<String>) {
        images.clear()
        images.add(enterprise.foto_perfil)
        for (url in imageList) {
            images.add(url)
        }
        viewPagerImagesProfile.adapter!!.notifyDataSetChanged()
    }

    private fun setupTableTimeSection() {
        clTableTime.setOnClickListener {
            val tableTImeFragment = TableTimeFragment.newInstance(enterprise.horarios)
            tableTImeFragment.show(supportFragmentManager, "TableTimeFragment")
        }
    }

    override fun isOpen(isOpen: Boolean) {
        tvStateEnterprise.text = if(isOpen) getString(R.string.string_open) else getString(R.string.string_closed)
    }

    private fun setupCourtsSection() {
        courtAdapter = CourtAdapter(enterprise.canchas, this)
        courtAdapter!!.setHasStableIds(true)
        rvCourts.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvCourts.adapter = courtAdapter
    }

    @SuppressLint("SetTextI18n")
    override fun onCheckedCourt(court: Cancha) {
        tvNumPlayers.text = court.numero_jugadores
        tvFloor.text = court.piso
        tvPriceDay.text = "$${court.precio_dia}"
        tvPriceNight.text = "$${court.precio_noche}"
        updateImages(court.fotos!!)
    }

    override fun updateCourts(courtList: List<Cancha>) {
        enterprise.canchas.clear()
        for (court in courtList) {
            enterprise.canchas.add(court)
        }
        rvCourts.adapter!!.notifyDataSetChanged()
    }

    override fun updateServices(serviceList: List<Servicio>) {
        enterprise.servicios.clear()
        for (service in serviceList) {
            enterprise.servicios.add(service)
            setServices(service)
        }
    }

    override fun updateContacts(contactList: List<RedSocial>) {
        enterprise.redes_social.clear()
        for (networkSocial in contactList) {
            enterprise.redes_social.add(networkSocial)
            setNetworkSocial(networkSocial)
        }
    }

    override fun isQualified(qualify: Boolean) {
        enterprise.isQualified = qualify
        cbRating.isChecked = qualify
        cbRating.isClickable = true
    }

    override fun updateLike(like: Int) {
        cbRating.text = like.toString()
        enterprise.me_gustas = like
        cbRating.isClickable = true
        //profilePresenter.getLikes("sAcL7AsndlapxazBB5ZrHyCix782", enterprise.pk)
    }

    override fun restoreRating() {
        cbRating.isChecked = enterprise.isQualified
        cbRating.text = enterprise.me_gustas.toString()
        cbRating.isClickable = true
    }

    override fun onMapReady(map: GoogleMap?) {
        if (map == null) return
        gMap = map
        gMap.uiSettings.isScrollGesturesEnabled = false
        gMap.uiSettings.isZoomGesturesEnabled = false
        gMap.uiSettings.isMyLocationButtonEnabled = false
        gMap.uiSettings.isMapToolbarEnabled = false
    }

    private fun setMapView() {
        tvLocation.text = enterprise.direccion!!.calles
        val latLng = LatLng(enterprise.direccion!!.latitud, enterprise.direccion!!.longitud)
        updateMarkerMapView(latLng)
        ivRunLocation.setOnClickListener {
            val gmmIntentUri =
                    Uri.parse("google.navigation:q=" +
                            latLng.latitude + "," +
                            latLng.longitude)
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.`package` = "com.google.android.apps.maps"
            if (mapIntent.resolveActivity(packageManager) != null) {
                startActivity(mapIntent)
            } else
                BaseActivitys.showToastMessage(this, "No se encontró Google Maps", Toast.LENGTH_SHORT)
        }
        ivRunLocation.visibility = View.VISIBLE
    }

    private fun updateMarkerMapView(latLng: LatLng){
        val cameraPosition = CameraPosition.Builder()
                .target(latLng)
                .zoom(15F)
                .build()
        gMap.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newCameraPosition(cameraPosition))
        val icon = BitmapDescriptorFactory.fromResource(if(enterprise.abierto) R.drawable.ic_futbol_open else R.drawable.ic_futbol_close)
        gMap.clear()
        gMap.addMarker(MarkerOptions()
                .position(latLng)
                .icon(icon)).showInfoWindow()
    }

    override fun onDestroy() {
        profilePresenter.unregister()
        super.onDestroy()
    }

    private fun setVectorCompatibility() {
        if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.KITKAT)
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    private fun setupPhotosSection() {
        images.add("https://firebasestorage.googleapis.com/v0/b/sitesport-204402.appspot.com/o/sport_center%2FZrBgMjjn8Pta8YlbLBLI%2FZrBgMjjn8Pta8YlbLBLI.jpg?alt=media&token=714cc694-05ef-4cae-9ea3-df00cf8c641c")
        imageAdapter = ImageAdapter(supportFragmentManager, images)
        viewPagerImagesProfile.adapter = imageAdapter
        tabImageProfileDots.setupWithViewPager(viewPagerImagesProfile, true)
    }

    private fun setupInjection() {
        val app: MyApplication = this.application as MyApplication
        val profileComponent = app.getProfileComponent(this)
        profileComponent.inject(this)
    }

    override fun setEnterprise(enterprise: Enterprise) {
        this.enterprise = enterprise
    }

    override fun getEnterprise(): Enterprise {
        return this.enterprise
    }

    override fun showSnackBarInfo(msg: String) {
        if (snackBarInfo != null && !snackBarInfo!!.isShown) {
            snackBarInfo!!.setText(msg)
            snackBarInfo!!.show()
        }
    }

    override fun showToastInfo(msg: String) {
        BaseActivitys.showToastMessage(this, msg, Toast.LENGTH_SHORT)
    }

    override fun hideLoadingTableTimeSection(msg: String?) {
        hideLoading(clTableTime, loadingTableTime, msg)
    }

    override fun hideLoadingCourtSection(msg: String?) {
        hideLoading(clCourts, loadingCourts, msg)
    }

    override fun hideLoadingServicesSection(msg: String?) {
        hideLoading(clServices, loadingServices, msg)
    }

    override fun hideLoadingContactsSection(msg: String?) {
        hideLoading(clContacts, loadingContacts, msg)
    }

    override fun showLoadingTableTimeSection() {
        showLoading(clTableTime, loadingTableTime)
    }

    override fun showLoadingCourtSection() {
        showLoading(clCourts, loadingCourts)
    }

    override fun showLoadingServicesSection() {
        showLoading(clServices, loadingServices)
    }

    override fun showLoadingContactsSection() {
        showLoading(clContacts, loadingContacts)
    }

    private fun showLoading(sectionView: View, loadingView: View) {
        sectionView.visibility = View.INVISIBLE
        loadingView.tvLoading.visibility = View.GONE
        loadingView.pbLoading.visibility = View.VISIBLE
        loadingView.visibility = View.VISIBLE
    }


    private fun hideLoading(sectionView: View, loadingView: View, msg: String?) {
        if(msg != null) {
            loadingView.visibility = View.GONE
            sectionView.visibility = View.VISIBLE
        }else{
            loadingView.pbLoading.visibility = View.GONE
            loadingView.tvLoading.text = msg
            loadingView.tvLoading.visibility = View.VISIBLE
            loadingView.visibility = View.VISIBLE
            sectionView.visibility = View.INVISIBLE
        }
    }

    private fun setServices(service: Servicio) {

        var imageView = ImageView(this)
        var idIcon = R.drawable.ic_court

        when (service.nombre.toLowerCase()) {
            Constants.wifi -> {
                imageView = ibtnWiFi
                idIcon = R.drawable.ic_wifi
            }

            Constants.bar -> {
                imageView = ibtnBar
                idIcon = R.drawable.ic_bar
            }

            Constants.parqueadero -> {
                imageView = ibtnEstacionamiento
                idIcon = R.drawable.ic_parked_car
            }

            Constants.duchas -> {
                imageView = ibtnDuchas
                idIcon = R.drawable.ic_shower
            }

            Constants.casilleros -> {
                imageView = ibtnCasilleros
                idIcon = R.drawable.ic_lockers
            }

            Constants.otros -> {
                imageView = ibtnOthers
                idIcon = R.drawable.ic_more
            }
        }

        imageView.visibility = View.VISIBLE
        //imageView.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN)
        setDataFragmentServices(service.nombre, service.descripcion, idIcon, imageView)
    }

    private fun setDataFragmentServices(title: String, info: String, idIcon: Int, imgButton: ImageView) {
        imgButton.setOnClickListener {
            val defaultServicesFragment = DefaultServicesFragment.newInstance(title, info, idIcon)
            defaultServicesFragment.show(supportFragmentManager, "DefaultServicesFragment")
        }
    }

    private fun setNetworkSocial(networkSocial: RedSocial) {

        var imageView = ImageView(this)

        when (networkSocial.nombre.toLowerCase()) {
            Constants.telefono -> {
                imageView = ibtnPhone
                imageView.setOnClickListener {
                    callPhone()
                }
            }

            Constants.facebook -> {
                imageView = ibtnFacebook
                imageView.setOnClickListener {
                    openFacebook(networkSocial.url)
                }
            }

            Constants.instagram -> {
                imageView = ibtnInstagram
                imageView.setOnClickListener {
                    openInstagram(networkSocial.url)
                }
            }

            Constants.whatsapp -> {
                imageView = ibtnWhatsapp
                imageView.setOnClickListener {
                    openWhatsApp(networkSocial.url)
                }
            }
        }

        imageView.visibility = View.VISIBLE
        imageView.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN)
    }

    private fun openFacebook(url: String) {
        try {
            //val subUrl = url.substring(0, url.length - 1)
            val username = url.substring(url.lastIndexOf("/") + 1)
            packageManager.getPackageInfo("com.url.katana", 0)
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/$username")))
        } catch (e: Exception) {
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            } catch (e: Exception) {
                BaseActivitys.showToastMessage(this, "Ha ocurrido un problema al abrir Facebook", Toast.LENGTH_SHORT)
            }
        }
    }

    private fun openInstagram(url: String) {
        try {
            //val subUrl = url.substring(0, url.length - 1)
            val username = url.substring(url.lastIndexOf("/") + 1)
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/_u/$username")))
        } catch (e: Exception) {
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            } catch (e: Exception) {
                BaseActivitys.showToastMessage(this, "Ha ocurrido un problema al abrir Instagram", Toast.LENGTH_SHORT)
            }
        }
    }

    private fun openWhatsApp(number: String) {
        val formattedNumber: String = ECU + number.substring(1, number.length)
        try {
            val sendIntent = Intent("android.intent.action.MAIN")
            sendIntent.component = ComponentName("com.whatsapp", "com.whatsapp.Conversation")
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.type = "text/plain"
            sendIntent.putExtra(Intent.EXTRA_TEXT, "")
            sendIntent.putExtra("jid", "$formattedNumber@s.whatsapp.net")
            sendIntent.`package` = "com.whatsapp"
            startActivity(sendIntent)
        } catch (e: Exception) {
            BaseActivitys.showToastMessage(this, "Ha ocurrido un problema al abrir Whatsapp", Toast.LENGTH_SHORT)
        }
    }


    // ========= setup GUI

    private fun setupUI() {
        setupToolBar()
        stateInitialView()
        setupMap()
        setupTableTimeSection()
        setupCourtsSection()
        setupPhotosSection()
        setupLikes()
        setupBtnReservation()
        snackBarInfo = Snackbar.make(clMainScreen, "", Snackbar.LENGTH_INDEFINITE).setAction("REINTENTAR") {
            callSections()
        }
    }

    private fun setupBtnReservation() {
        btnReservation.setOnClickListener {
            val intent = Intent(this, ReserveActivity::class.java)
            Log.e(ENTERPRISE, enterprise.canchas.toString())
            intent.putExtra(ENTERPRISE, enterprise)
            startActivity(intent)
        }
    }

    private fun setupLikes() {
        cbRating.setOnClickListener {
            if (uidUser != null) {
                val checkBox: CheckBox = it as CheckBox
                cbRating.isChecked = checkBox.isChecked
                if (cbRating.isChecked) cbRating.text = (enterprise.me_gustas + 1).toString()
                else cbRating.text = (enterprise.me_gustas - 1).toString()
                profilePresenter.toggleLike(uidUser!!, enterprise.pk, !cbRating.isChecked)
                cbRating.isClickable = false
            } else {
                cbRating.isChecked = !cbRating.isChecked
                BaseActivitys.showToastMessage(this, "Debes iniciar sesión primero", Toast.LENGTH_SHORT)
            }
        }
    }

    private fun stateInitialView() {
        ibtnBar.visibility = View.GONE
        ibtnWiFi.visibility = View.GONE
        ibtnEstacionamiento.visibility = View.GONE
        ibtnDuchas.visibility = View.GONE
        ibtnCasilleros.visibility = View.GONE
        ibtnOthers.visibility = View.GONE

        ibtnPhone.visibility = View.GONE
        ibtnWhatsapp.visibility = View.GONE
        ibtnFacebook.visibility = View.GONE
        ibtnInstagram.visibility = View.GONE
        ivRunLocation.visibility = View.GONE

        cbRating.isClickable = false
    }


    private fun setupMap() {
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.mapViewProfile) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun setupToolBar() {
        setSupportActionBar(toolbar_profile)
        if (supportActionBar != null) {
            supportActionBar!!.title = ""
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_profile, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()
        else if (item.itemId == R.id.action_share)
            shareProfile()
        return super.onOptionsItemSelected(item)
    }

    private fun shareProfile() {
        BaseActivitys.showToastMessage(this, "Obteniendo aplicaciones...", Toast.LENGTH_LONG)
        BaseActivitys.buildDinamycLinkShareApp(enterprise.pk, BaseActivitys.LINK_ENTERPRISE, object : onApiActionListener<String> {
            override fun onSucces(response: String) {
                intentShared(response)
            }

            override fun onError(error: Any?) {
                intentShared(null)
            }
        })
    }

    private fun intentShared(link: String?) {
        var auxLink = " ${resources.getString(R.string.url_play_store)}"
        if (link != null) auxLink = " $link"
        val i = Intent(android.content.Intent.ACTION_SEND)
        i.type = "text/plain"
        i.putExtra(android.content.Intent.EXTRA_SUBJECT, R.string.app_name)
        i.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.textShareEnterprise) + auxLink)
        startActivity(Intent.createChooser(i, "Compartir mediante..."))
    }

    @SuppressLint("MissingPermission")
    private fun callPhone() {
        if (enterprise.telefono.isNotEmpty()) {
            startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:" + enterprise.telefono)))
        } else {
            BaseActivitys.showToastMessage(this, "Teléfono no disponible", Toast.LENGTH_SHORT)
        }
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

    override fun onResume() {
        super.onResume()
        callSections()
    }

    companion object {
        const val TAG = "ProfileActivity"
        //const val EMOTICON_HAPPY = 0x1F60A
        const val EMOTICON_EYE = 0x1F609
        const val ENTERPRISE = "enterprise"
        const val ECU = "593"
        const val REQUEST_CODE_CALL_PHONE_PERMISSIONS = 123
        const val SECTION_TABLE_TIME = 0
        const val SECTION_COURTS = 1
        const val SECTION_SERVICES = 2
        const val SECTION_CONTACTS = 3
        const val SECTION_BASIC = 4
        const val SECTION_INITIAL_LIKE = 5
        const val SECTION_UPDATE_LIKE = 6
        const val AUTHENTICATION = 7
    }

}
