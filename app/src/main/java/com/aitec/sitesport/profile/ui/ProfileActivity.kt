package com.aitec.sitesport.profile.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.ComponentName
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.aitec.sitesport.R
import android.view.View
import com.mapbox.mapboxsdk.annotations.IconFactory
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.content_profile.*
import android.widget.Toast
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.PersistableBundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import com.aitec.sitesport.MyApplication
import com.aitec.sitesport.entities.enterprise.*
import com.aitec.sitesport.profile.ProfilePresenter
import com.aitec.sitesport.profile.ui.dialog.DefaultServicesFragment
import com.aitec.sitesport.profile.ui.dialog.ViewPagerAdapter
import com.aitec.sitesport.profile.ui.dialog.TableTimeFragment
import com.aitec.sitesport.reserve.adapter.CourtAdapter
import com.aitec.sitesport.reserve.adapter.OnClickListenerCourt
import com.aitec.sitesport.util.BaseActivitys
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import javax.inject.Inject


class ProfileActivity : AppCompatActivity(), OnClickListenerCourt, ProfileView{

    @Inject
    lateinit var profilePresenter: ProfilePresenter
    private var viewPagerAdapter: ViewPagerAdapter? = null
    private var enterprise: Enterprise? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setVectorCompatibility()
        setContentView(R.layout.activity_profile)
        setupInjection()
        onCreateMapBox(savedInstanceState)

        this.enterprise = intent.getParcelableExtra(ENTERPRISE)
        setupUI()
        profilePresenter.register()
        profilePresenter.getProfile(enterprise!!.urldetalle)
    }

    override fun onStart() {
        super.onStart()
        mvProfile.onStart()
    }

    override fun onResume() {
        super.onResume()
        mvProfile.onResume()
    }

    override fun onPause() {
        super.onPause()
        mvProfile.onPause()
    }

    override fun onStop() {
        super.onStop()
        mvProfile.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        mvProfile.onSaveInstanceState(outState!!)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mvProfile.onLowMemory()
    }

    override fun onDestroy() {
        mvProfile.onDestroy()
        profilePresenter.unregister()
        super.onDestroy()
    }

    private fun setVectorCompatibility(){
        if(android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.KITKAT)
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    private fun setupViewPager(fotos: List<Foto>) {
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, fotos)
        viewPagerImagesProfile.adapter = viewPagerAdapter
        viewPagerAdapter!!.notifyDataSetChanged()
    }

    private fun setupInjection() {
        val app: MyApplication = this.application as MyApplication
        val profileComponent = app.getProfileComponent(this)
        profileComponent.inject(this)
    }

    // ProfileView.kt implementation

    override fun showLoading() {
        btnReload.visibility = View.GONE
        tvInfo.text = ""
        pbLoading.visibility = View.VISIBLE
        if(clLoader.visibility == View.GONE) clLoader.visibility = View.VISIBLE
    }

    override fun hideLoading(msg: String) {
        Log.e(TAG, msg)
        if(msg.isNotBlank()){
            pbLoading.visibility = View.GONE
            tvInfo.text = msg
            btnReload.visibility = View.VISIBLE
        }else{
            clLoader.visibility = View.GONE
        }
    }

    override fun setServices(servicios: Servicios) {
        if (servicios.BAR) {
            ibtnBar.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN)
            setDataFragmentServices("Bar", "Aqui va la info del bar", R.drawable.ic_bar, ibtnBar)
        }else ibtnBar.visibility = View.GONE
        if (servicios.WIFI) {
            ibtnWiFi.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN)
            setDataFragmentServices("WiFi", "Aqui va la info del wifi", R.drawable.ic_wifi, ibtnWiFi)
        }else ibtnWiFi.visibility = View.GONE
        if (servicios.PARKER) {
            ibtnEstacionamiento.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN)
            setDataFragmentServices("Estacionamiento", "Aqui va la info del estacionamiento", R.drawable.ic_parked_car, ibtnEstacionamiento)
        }else ibtnEstacionamiento.visibility = View.GONE
        if (servicios.DUCHA){
            ibtnDuchas.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN)
            setDataFragmentServices("Ducha", "Aqui va la info de las duchas", R.drawable.ic_shower, ibtnDuchas)
        }else ibtnDuchas.visibility = View.GONE
        if (servicios.LOKER) {
            ibtnCasilleros.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN)
            setDataFragmentServices("Casillero", "Aqui va la info de los casilleros", R.drawable.ic_lockers, ibtnCasilleros)
        }else ibtnCasilleros.visibility = View.GONE
    }

    private fun setDataFragmentServices(title: String, info: String, idIcon: Int, imgButton: ImageView){
        imgButton.setOnClickListener {
            val defaultServicesFragment = DefaultServicesFragment.newInstance(title, info, idIcon)
            defaultServicesFragment.show(supportFragmentManager, "DefaultServicesFragment")
        }
    }

    override fun setImages(imagesUrls: List<Foto>) {
        setupViewPager(imagesUrls)
        tabImageProfileDots.setupWithViewPager(viewPagerImagesProfile, true)
    }

    override fun setLikes(likes: Int) {
        tvLike.text = likes.toString()
    }

    override fun setStateEnterprise(state: Boolean) {
        tvStateEnterprise.text = if(state) "Abierto" else "Cerrado"
    }

    override fun setEnterprise(enterprise: Enterprise) {
        this.enterprise = enterprise
    }

    override fun onCheckedCourt(court: Cancha) {
        tv_price_day.text = "$ " + court.dia.toString()
        tv_price_nigth.text = "$ " + court.noche.toString()
        tv_num_players.text = court.numero_jugadores
        tv_floor.text = court.piso

        if(!court.isAddPhotoProfile){
            val f= Foto()
            f.imagen = enterprise!!.foto_perfil
            court.fotos!!.add(f)
            court.isAddPhotoProfile = true
        }
        setImages(court.fotos!!)
    }

    override fun setTableTime(horarios: List<Horario>) {
        clTableTime.setOnClickListener {
            if(horarios!= null && horarios.isNotEmpty()) {
                val tableTImeFragment = TableTimeFragment.newInstance(horarios)
                tableTImeFragment.show(supportFragmentManager, "TableTimeFragment")
            }else{
                BaseActivitys.showToastMessage(this, "Horarios no disponibles", Toast.LENGTH_SHORT)
            }
        }
    }

    override fun setCourts(canchas: List<Cancha>) {
        val adapter = CourtAdapter(canchas, this)
        rv_fields_profile.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_fields_profile.adapter = adapter
    }


    // setup GUI

    private fun setupMap(latitude: Double, longitude: Double) {
        mvProfile.getMapAsync({
            val iconFactory = IconFactory.getInstance(this)
            val icon = iconFactory.fromResource(if(enterprise!!.abierto) R.drawable.ic_futbol_open else R.drawable.ic_futbol_close)
            it.addMarker(MarkerOptions()
                    .position(LatLng(latitude, longitude))
                    .icon(icon))
            val position = CameraPosition.Builder()
                    .target(LatLng(latitude, longitude)) // Sets the new camera position
                    .zoom(15.0) // Sets the zoom
                    .build() // Creates a CameraPosition from the builder
            it.animateCamera(CameraUpdateFactory.newCameraPosition(position), 500)
        })
        mvProfile.setOnTouchListener(View.OnTouchListener { v, event -> return@OnTouchListener true }) //desabilitar el touch en el mapa
    }

    private fun setNameProfile(name: String) {
        collapse_toolbar_profile.title = name
    }

    private fun setupToolBar(){
        setSupportActionBar(toolbar_profile)
        if (supportActionBar != null) {
            supportActionBar!!.title = ""
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }
    }

    private fun openInstagram(){
        var isExist = false
        if(enterprise!!.redes_sociales!!.isNotEmpty()){
            loop@ for(network in enterprise!!.redes_sociales!!.iterator()) {
                when (network.nombre) {
                    "INSTAGRAM" -> {
                        if(network.url.isEmpty()){
                            break@loop
                        }
                        isExist = true
                        try {
                            val url = network.url.substring(0, network.url.length - 1);
                            val username = url.substring(url.lastIndexOf("/") + 1);
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/_u/" + username)))
                        } catch (e: Exception) {
                            startActivity(Intent(Intent.ACTION_VIEW,
                                    Uri.parse(network.url)));
                        }
                        return
                    }
                }
            }
        }

        if(!isExist)
            BaseActivitys.showToastMessage(this, "Instagram no disponible", Toast.LENGTH_SHORT)

    }

    private fun openWhatsApp() {
        var isExist = false
        /*if(enterprise!!.redes_sociales!!.isNotEmpty()){
            for(network in enterprise!!.redes_sociales!!.iterator()){
                when (network.nombre){
                    "WHATSAPP" -> {
                        isExist = true
                        val ECU = "593"
                        val formattedNumber: String = ECU + network.url
                        try {
                            val sendIntent: Intent
                            sendIntent = Intent("android.intent.action.MAIN")
                            sendIntent.setComponent(ComponentName("com.nombre", "com.nombre.Conversation"))
                            sendIntent.setAction(Intent.ACTION_SEND)
                            sendIntent.setType("text/plain")
                            sendIntent.putExtra(Intent.EXTRA_TEXT, "")
                            sendIntent.putExtra("jid", formattedNumber + "@s.nombre.net")
                            sendIntent.setPackage("com.nombre")
                            startActivity(sendIntent)
                        } catch (e: Exception) {
                            //Toast.makeText(this,"Error/n"+ e.toString(),Toast.LENGTH_SHORT).show();
                            BaseActivitys.showToastMessage(this, "Algo salió mal, intéctalo más tarde", Toast.LENGTH_SHORT)
                        }
                        return
                    }
                }
            }
        }*/

        if(enterprise!!.telefono.isNotEmpty()){

            val ECU = "593"
            val formattedNumber: String = ECU + enterprise!!.telefono
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
                Log.e("ERROR WHATSAPP", e.message)
                BaseActivitys.showToastMessage(this, "Algo salió mal, inténtalo más tarde", Toast.LENGTH_SHORT)
            }
        }else{
            BaseActivitys.showToastMessage(this, "Whatsapp no disponible", Toast.LENGTH_SHORT)
        }
    }

    private fun openFacebook() {
        var isExist = false
        if(enterprise!!.redes_sociales!!.isNotEmpty()){
            loop@ for(network in enterprise!!.redes_sociales!!.iterator()){
                when (network.nombre){
                    "FACEBOOK" -> {
                        if(network.url.isEmpty()){
                            break@loop
                        }
                        isExist = true
                        try {
                            val url = network.url.substring(0, network.url.length - 1);
                            val username = url.substring(url.lastIndexOf("/") + 1);
                            getPackageManager().getPackageInfo("com.url.katana", 0)
                            startActivity(Intent(Intent.ACTION_VIEW,
                                    Uri.parse("fb://profile/" + username)))
                        } catch (e: Exception) {
                            startActivity(Intent(Intent.ACTION_VIEW,
                                    Uri.parse(network.url))) //catches and opens a url to the desired page
                        }
                        return
                    }
                }
            }
        }
        if(!isExist)
            BaseActivitys.showToastMessage(this, "Facebook no disponible", Toast.LENGTH_SHORT)

    }

    private fun setupUI(){

        btnReload.setOnClickListener{
            profilePresenter.getProfile(enterprise!!.urldetalle)
        }
        clLoader.visibility = View.GONE
        setNameProfile(enterprise!!.nombres)
        setupMap(enterprise!!.direccion!!.latitud, enterprise!!.direccion!!.longitud)
        setupToolBar()

        if(enterprise!!.me_gusta) imgLike.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fire_on))
        else imgLike.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fire_off))

        clLike.setOnClickListener {
            BaseActivitys.showToastMessage(this,
                    "Próximamente... " + String(Character.toChars(ProfileActivity.EMOTICON_EYE)),
                    Toast.LENGTH_SHORT)
            /*if(enterprise!!.me_gusta) {
                Toast.makeText(this, "Ya has calificado a " + enterprise!!.nombres, Toast.LENGTH_SHORT).show()
            }else{
                //MANDAR A CALIFICAR AQUIIIII XD
                //profilePresenter.like()
            }*/

        }

        btnReservation.setOnClickListener {
            Toast.makeText(this, "Próximamente " + String(Character.toChars(EMOTICON_EYE)), Toast.LENGTH_SHORT).show()
        }

        //NetworkSocial - Phone

        ibtnWhatsapp.setOnClickListener {
            openWhatsApp()
        }

        ibtnFacebook.setOnClickListener {
            openFacebook()
        }

        ibtnInstagram.setOnClickListener {
            openInstagram()
        }

        ibtnPhone.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                callPhone()
            } else {
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CODE_CALL_PHONE_PERMISSIONS)
                return@setOnClickListener
            }
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

    private fun shareProfile(){
        val i = Intent(android.content.Intent.ACTION_SEND)
        i.type = "text/plain"
        i.putExtra(android.content.Intent.EXTRA_SUBJECT, R.string.app_name  )
        i.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.textShare))
        startActivity(Intent.createChooser(i, "Compartir mediante..."))
    }

    @SuppressLint("MissingPermission")
    private fun callPhone() {
        //if(enterprise != null && enterprise!!.telefonos!![0].convencional.isNotBlank()) {
        if(enterprise != null && enterprise!!.telefono.isNotEmpty()) {
            startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:" + enterprise!!.telefono)))
        }else{
            BaseActivitys.showToastMessage(this, "Teléfono no disponible", Toast.LENGTH_SHORT)
        }
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

    companion object {
        const val SHARE = ""
        const val TAG = "ProfileActivity"
        const val EMOTICON_HAPPY = 0x1F60A
        const val EMOTICON_EYE = 0x1F609
        const val ENTERPRISE = "enterprise"
        const val REQUEST_CODE_CALL_PHONE_PERMISSIONS = 123
    }

}
