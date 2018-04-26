package com.aitec.sitesport.mapSites.ui

import android.Manifest
import android.animation.ObjectAnimator
import android.animation.TypeEvaluator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.support.constraint.ConstraintLayout
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import com.aitec.sitesport.MyApplication
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.enterprise.Enterprise
import com.aitec.sitesport.mapSites.MapSitesPresenter
import com.aitec.sitesport.mapSites.adapter.EntrepiseAdapter
import com.aitec.sitesport.mapSites.adapter.SearchNamesEntrepiseAdapter
import com.aitec.sitesport.profile.ui.ProfileActivity
import com.aitec.sitesport.util.BaseActivitys
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.mapbox.mapboxsdk.annotations.Icon
import com.mapbox.mapboxsdk.annotations.IconFactory
import com.mapbox.mapboxsdk.annotations.Marker
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import kotlinx.android.synthetic.main.activity_mapsites.*
import kotlinx.android.synthetic.main.bottom_sheet_results.*
import java.text.DecimalFormat
import javax.inject.Inject


class MapSitesActivity : AppCompatActivity(), EntrepiseAdapter.onEntrepiseAdapterListener, SearchView.OnQueryTextListener, View.OnFocusChangeListener, OnMapReadyCallback, View.OnClickListener, MapboxMap.OnCameraIdleListener, MapSitesView, MapboxMap.OnMarkerClickListener, SearchNamesEntrepiseAdapter.onEntrepiseSearchNameListener {

    private val TAG = MapSitesActivity::class.java.simpleName
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    private val REQUEST_CHECK_SETTINGS = 0x1
    private val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 1000
    private val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2

    private var markerSelect: Marker? = null


    val REQUESTING_LOCATION_UPDATES_KEY = "location"
    var requestingLocationUpdates = false
    lateinit var entrepiseAdapter: EntrepiseAdapter
    lateinit var searchNamesEntrepiseAdapter: SearchNamesEntrepiseAdapter
    var entrepiseResultsSearchVisible = ArrayList<Enterprise>()

    var entrepiseResultsSearchName = ArrayList<Enterprise>()

    lateinit var entrepiseSelect: Enterprise

    lateinit var mapboxMap: MapboxMap
    lateinit var iconFactory: IconFactory

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var mSettingsClient: SettingsClient
    private lateinit var mCurrentLocation: Location
    private lateinit var mLocationSettingsRequest: LocationSettingsRequest

    lateinit var application: MyApplication
    @Inject
    lateinit var presenter: MapSitesPresenter


    lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>


    /*Ciclo de Vida*/
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapsites)
        setupInjection()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mSettingsClient = LocationServices.getSettingsClient(this)
        createLocationCallback()
        createLocationRequest()
        buildLocationSettingsRequest()
        setupMap(savedInstanceState)
        setupBottomSheet()
        setupReciclerView()
        setupEvents()

    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
        Log.e("resume", "en resumen")
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
        Log.e("stop", "stop")
    }

    public override fun onPause() {
        super.onPause()
        presenter.onPause()
        mapView.onPause()
        stopLocationUpdates()
        Log.e("pause", "en pause")
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    public override fun onResume() {
        super.onResume()
        presenter.onResume()
        mapView.onResume()

    }

    override fun onBackPressed() {
        if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        } else {
            super.onBackPressed()
        }

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putBoolean(REQUESTING_LOCATION_UPDATES_KEY, requestingLocationUpdates)
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState!!)
    }

    /*Setup*/
    fun setupReciclerView() {
        entrepiseAdapter = EntrepiseAdapter(entrepiseResultsSearchVisible, this)
        var mDividerItemDecoration = DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL)
        rv_results.addItemDecoration(mDividerItemDecoration)
        rv_results.layoutManager = LinearLayoutManager(this)
        rv_results.adapter = entrepiseAdapter


        searchNamesEntrepiseAdapter = SearchNamesEntrepiseAdapter(entrepiseResultsSearchName, this)
        rv_results_searchs.layoutManager = LinearLayoutManager(this)
        rv_results_searchs.adapter = searchNamesEntrepiseAdapter


    }

    fun setupEvents() {
        btn_sport.setOnClickListener(this)
        btn_distance.setOnClickListener(this)
        btn_my_location.setOnClickListener(this)
        sv_search.setOnQueryTextListener(this)
        sv_search.setOnQueryTextFocusChangeListener(this)
        cl_header_bs.setOnClickListener(this)
        btn_profile_entrepise.setOnClickListener(this)
    }

    fun setupMap(savedInstanceState: Bundle?) {
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    fun setupBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        iv_icon_open.rotation = 0F
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        iv_icon_open.rotation = 180F

                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })
    }

    private fun setupInjection() {
        application = getApplication() as MyApplication
        application.getMapSitesComponent(this).inject(this)
    }

    private fun buildLocationSettingsRequest() {
        mLocationSettingsRequest = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest).build()
    }

    private fun createLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    Log.e(TAG, "" + location.latitude)
                    mCurrentLocation = location
                    if (::mapboxMap.isInitialized) {
                        animateCamera(LatLng(mCurrentLocation.latitude, mCurrentLocation.longitude), 16.0)
                        showProgresBarResultsMapVisible(false)
                        stopLocationUpdates()
                    }
                }
            }
        }
    }

    fun createLocationRequest() {
        locationRequest = LocationRequest().apply {
            interval = UPDATE_INTERVAL_IN_MILLISECONDS
            fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

/*
        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener { locationSettingsResponse ->
            Log.e("aa", locationSettingsResponse.toString())
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().

                    exception.startResolutionForResult(this@mapSitesActivity,
                            REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
        */
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        showProgresBarResultsMapVisible(true)
        setInfoHeaderBottomSheet("Centros Deportivos", "Obteniendo su Ubicación")
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this) {
                    Log.e(TAG, "All location settings are satisfied.")
                    fusedLocationClient.requestLocationUpdates(locationRequest,
                            locationCallback, Looper.myLooper())

                }
                .addOnFailureListener(this) { e ->
                    val statusCode = (e as ApiException).statusCode
                    when (statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                            Log.e(TAG, "Location settings are not satisfied. Attempting to upgrade " + "location settings ")
                            try {
                                // Show the dialog by calling startResolutionForResult(), and check the
                                // result in onActivityResult().
                                val rae = e as ResolvableApiException
                                rae.startResolutionForResult(this@MapSitesActivity, REQUEST_CHECK_SETTINGS)
                            } catch (se: IntentSender.SendIntentException) {
                                //   Log.i(FragmentActivity.TAG, "PendingIntent unable to execute request.")
                            }

                        }
                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                            val errorMessage = "Location settings are inadequate, and cannot be " + "fixed here. Fix in Settings."
                            //   Log.e(FragmentActivity.TAG, errorMessage)
                            Toast.makeText(this@MapSitesActivity, errorMessage, Toast.LENGTH_LONG).show()
                            // requestingLocationUpdates = false
                        }
                    }
                    // updateUI()
                }
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun permission() {
        if (checkPermissions()) {
            startLocationUpdates();
        } else if (!checkPermissions()) {
            requestPermissions();
        }
    }

    /*Permisos*/
    private fun requestPermissions() {

        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)

        Log.e("permisos", "pidiendo permiso" + shouldProvideRationale)
        if (shouldProvideRationale) {
            Log.e("permisos", "true")
            ActivityCompat.requestPermissions(this@MapSitesActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSIONS_REQUEST_CODE)
        } else {

            Log.e("permisos", "false")

            /* ActivityCompat.requestPermissions(this@mapSitesActivity,
                     arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                     REQUEST_PERMISSIONS_REQUEST_CODE)
                     */
        }
    }

    private fun checkPermissions(): Boolean {
        var permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.size <= 0) {

                Log.e(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // if (requestingLocationUpdates) {
                Log.e(TAG, "Permission granted, updates requested, starting location updates");
                startLocationUpdates();
                //}
            } else {
                /*
                showSnackbar(R.string.permission_denied_explanation,
                        R.string.settings, new View . OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        // Build intent that displays the App settings screen.
                        Intent intent = new Intent();
                        intent.setAction(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri . fromParts ("package",
                        BuildConfig.APPLICATION_ID, null);
                        intent.setEntrepiseResultsSearchVisible(uri);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                */
            }
        }
    }

    /*Activity Result */

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
        // Check for the integer request code originally supplied to startResolutionForResult().
            REQUEST_CHECK_SETTINGS -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        Log.i(TAG, "User agreed to make required location settings changes.");
// Nothing to do. startLocationupdates() gets called in onResume again.
                    }
                    Activity.RESULT_CANCELED -> {
                        Log.i(TAG, "User chose not to make required location settings changes.");
                        //requestingLocationUpdates = false;
                        //updateUI();
                    }
                }
            }
        }
    }

    /*Eventos Click*/
    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btn_sport -> {

            }
            R.id.btn_distance -> {

            }
            R.id.btn_profile_entrepise -> {
                navigatioProfile(entrepiseSelect)
            }
            R.id.btn_my_location -> {
                permission()
            }
            R.id.cl_header_bs -> {
                when (bottomSheetBehavior.state) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                }
            }

        }
    }


    /*Busqueda SearchView*/
    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (hasFocus) {
            rv_results_searchs.visibility = View.VISIBLE
            bottom_sheet.visibility = View.GONE
            //cl_chips.visibility = View.GONE
            btn_my_location.visibility = View.GONE

        } else {
            rv_results_searchs.visibility = View.GONE
            sv_search.setQuery("", false)
            noneResultSearchsName("", false)
            bottom_sheet.visibility = View.VISIBLE
            //cl_chips.visibility = View.VISIBLE
            btn_my_location.visibility = View.VISIBLE
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        presenter.stopSearchName()
        presenter.getSearchName(newText!!)
        return true
    }

    override fun navigatioProfile(entrepise: Enterprise) {
        startActivity(Intent(this, ProfileActivity::class.java).putExtra(ProfileActivity.ENTERPRISE, entrepise))
    }


    /*Mapa*/
    override fun onMapReady(mapboxMaps: MapboxMap?) {
        mapboxMap = mapboxMaps!!
        mapboxMap.setOnMarkerClickListener(this)
        iconFactory = IconFactory.getInstance(this)
        mapboxMap.addOnCameraIdleListener(this)

    }


    override fun addMarker(entreprise: Enterprise) {
        var icono: Icon

        if (entreprise.abierto) {
            icono = iconFactory.fromResource(R.drawable.ic_futbol_open)
        } else {
            icono = iconFactory.fromResource(R.drawable.ic_futbol_close)
        }


        var marker = mapboxMap!!.addMarker(MarkerOptions()
                .position(LatLng(entreprise.direccion.latitud, entreprise.direccion.longitud))
                .icon(icono))

        entreprise.idMarker = marker.id
        // marker.title = "Que mas v"
        // marker.showInfoWindow(mapboxMap, mapView)

    }

    fun animateCamera(latLng: LatLng, zoom: Double) {
        val position = CameraPosition.Builder()
                .target(latLng) // Sets the new camera position
                .zoom(zoom) // Sets the zoom
                .build() // Creates a CameraPosition from the builder
        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000)
    }


    override fun onCameraIdle() {
        presenter.stopSearchVisibility()
        var bounds = mapboxMap.projection.visibleRegion.latLngBounds
        Log.e("coordenadas", "lat" + bounds.center.latitude + "lng" + bounds.center.longitude)
        presenter.onGetCenterSportVisible(bounds.latSouth, bounds.latNorth, bounds.lonWest, bounds.lonEast, bounds.center.latitude, bounds.center.longitude)

    }

    override fun onMarkerClick(marker: Marker): Boolean {
        if (markerSelect != null) {
            if (entrepiseSelect.abierto) {
                markerSelect!!.icon = iconFactory.fromResource(R.drawable.ic_futbol_open)
            } else {
                markerSelect!!.icon = iconFactory.fromResource(R.drawable.ic_futbol_close)
            }
        }

        val df = DecimalFormat("0.00")
        btn_profile_entrepise.visibility = VISIBLE
        //animateMarker(marker, mapboxMap.cameraPosition.target)

        entrepiseResultsSearchVisible.forEach {
            if (it.idMarker == marker.id) {
                entrepiseSelect = it
                markerSelect = marker

                if (it.abierto) {
                    markerSelect!!.icon = iconFactory.fromResource(R.drawable.ic_futbol_open_select)
                } else {
                    markerSelect!!.icon = iconFactory.fromResource(R.drawable.ic_futbol_close_select)
                }
                tv_title_bs.setText(it.nombres)
                tv_subtitle_bs.setText(df.format(it.distance).toString() + " Km")
            }
        }

        return true
    }


    /*mapSitesView*/
    override fun setResultSearchsName(results: List<Enterprise>) {
        entrepiseResultsSearchName.clear()
        entrepiseResultsSearchName.addAll(results)
        searchNamesEntrepiseAdapter.notifyDataSetChanged()
        Log.e("a", results.get(0).pk)
    }

    override fun setInfoHeaderBottomSheet(title: Any, subtitle: Any) {
        tv_title_bs.setText(title.toString())
        tv_subtitle_bs.setText(subtitle.toString())
    }

    override fun noneResultCenterVisible(s: Any) {
        tv_subtitle_bs.setText(s.toString())
    }

    override fun noneResultSearchsName(message: Any, show: Boolean) {
        if (show) {
            tv_none_results.visibility = VISIBLE
        } else {
            tv_none_results.visibility = GONE

        }


    }

    override fun showMessagge(message: Any) {
        BaseActivitys.showToastMessage(this, message, Toast.LENGTH_SHORT)
    }

    override fun setResultsSearchs(entrepriseList: List<Enterprise>) {
        Log.e(TAG, "id" + entrepriseList.get(0).pk)
        entrepiseResultsSearchVisible.clear()
        mapboxMap.clear()
        entrepiseResultsSearchVisible.addAll(entrepriseList)
        tv_subtitle_bs.setText(entrepriseList.size.toString() + " encontrados")
        entrepiseAdapter.notifyDataSetChanged()
    }

    override fun showProgresBar(show: Boolean) {
        if (show) pg_searchName.visibility = VISIBLE else pg_searchName.visibility = GONE

    }

    override fun showProgresBarResultsMapVisible(show: Boolean) {
        if (show) progressbar.visibility = VISIBLE else progressbar.visibility = GONE
    }

    override fun clearSearchResultsName() {
        noneResultSearchsName("", false)
        entrepiseResultsSearchName.clear()
        searchNamesEntrepiseAdapter.notifyDataSetChanged()
    }

    override fun clearSearchResultsVisible() {
        entrepiseResultsSearchVisible.clear()
        entrepiseAdapter.notifyDataSetChanged()

    }

    override fun showNoneResulstEntrepiseVisible(show: Boolean) {
        if (show) tv_none_results_entrepise_visibles.visibility = VISIBLE else tv_none_results_entrepise_visibles.visibility = GONE
    }

    override fun hideButtonProfileEntrepise() {
        btn_profile_entrepise.visibility = GONE
    }


    class LatLngEvaluator : TypeEvaluator<LatLng> {
        private val latLng = LatLng()

        override fun evaluate(fraction: Float, startValue: LatLng?, endValue: LatLng?): LatLng {
            latLng.setLatitude(startValue!!.getLatitude()
                    + ((endValue!!.getLatitude() - startValue.getLatitude()) * fraction))
            latLng.setLongitude(startValue.getLongitude()
                    + ((endValue!!.getLongitude() - startValue.getLongitude()) * fraction))
            return latLng
        }
    }

    fun animateMarker(marker: Marker, point: LatLng) {
        var markerAnimator = ObjectAnimator.ofObject(marker, "position",
                LatLngEvaluator(), marker.getPosition(), point);
        markerAnimator.setDuration(2000);
        markerAnimator.start();
    }
}
