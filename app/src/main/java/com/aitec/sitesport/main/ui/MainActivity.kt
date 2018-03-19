package com.aitec.sitesport.main.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
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
import com.aitec.sitesport.entities.Entreprise
import com.aitec.sitesport.main.MainPresenter
import com.aitec.sitesport.main.adapter.EntrepiseAdapter
import com.aitec.sitesport.profile.ui.ProfileActivity
import com.aitec.sitesport.util.BaseActivitys
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.mapbox.mapboxsdk.annotations.IconFactory
import com.mapbox.mapboxsdk.annotations.Marker
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet_results.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), EntrepiseAdapter.onEntrepiseAdapterListener, SelectDistanceFragment.OnSelectDistanceListener, SearchView.OnQueryTextListener, View.OnFocusChangeListener, OnMapReadyCallback, View.OnClickListener, MapboxMap.OnCameraIdleListener, MainView, MapboxMap.OnMarkerClickListener {


    override fun onMarkerClick(marker: Marker): Boolean {
        data.forEach {
            if (it.idMarker == marker.id) {
                tv_title_bs.setText(it.centro_deportivo)
                tv_subtitle_bs.setText(it.distancia.toString() + " Km")
            }
        }

        return true
    }

    override fun showMessagge(message: Any) {
        BaseActivitys.showToastMessage(this, message, Toast.LENGTH_SHORT)
    }

    override fun setResultsSearchs(entrepriseList: List<Entreprise>) {
        Log.e(TAG, "id" + entrepriseList.get(0).pk)
        data.clear();
        mapboxMap.clear()
        data.addAll(entrepriseList)
        tv_subtitle_bs.setText(entrepriseList.size.toString() + " encontrados")
        entrepiseAdapter.notifyDataSetChanged()
    }

    override fun showProgresBar(show: Boolean) {
        if (show) progressbar.visibility = VISIBLE else progressbar.visibility = GONE
    }

    override fun clearSearchResults() {

    }

    private val TAG = MainActivity::class.java.simpleName
    /**
     * Code used in requesting runtime permissions.
     */
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    /**
     * Constant used in the location settings dialog.
     */
    private val REQUEST_CHECK_SETTINGS = 0x1
    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    private val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 1000
    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    private val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2
    // Keys for storing activity state in the Bundle.
    private val KEY_REQUESTING_LOCATION_UPDATES = "requesting-location-updates"
    private val KEY_LOCATION = "location"
    private val KEY_LAST_UPDATED_TIME_STRING = "last-updated-time-string"


    val REQUESTING_LOCATION_UPDATES_KEY = "location"
    var requestingLocationUpdates = false
    lateinit var entrepiseAdapter: EntrepiseAdapter
    var data = ArrayList<Entreprise>()
    var markers = HashMap<Long, Int>()
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
    lateinit var presenter: MainPresenter


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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


    private fun setupInjection() {
        application = getApplication() as MyApplication
        application.getMainComponent(this).inject(this)
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

                    exception.startResolutionForResult(this@MainActivity,
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
        //fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
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
                                rae.startResolutionForResult(this@MainActivity, REQUEST_CHECK_SETTINGS)
                            } catch (sie: IntentSender.SendIntentException) {
                                //   Log.i(FragmentActivity.TAG, "PendingIntent unable to execute request.")
                            }

                        }
                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                            val errorMessage = "Location settings are inadequate, and cannot be " + "fixed here. Fix in Settings."
                            //   Log.e(FragmentActivity.TAG, errorMessage)
                            Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG).show()
                            // requestingLocationUpdates = false
                        }
                    }
                    // updateUI()
                }
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    fun setupReciclerView() {
        //data.add(Entreprise())
        //data.add(Entreprise())
        entrepiseAdapter = EntrepiseAdapter(data, this)
        var mDividerItemDecoration = DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL)
        rv_results.addItemDecoration(mDividerItemDecoration)
        rv_results.layoutManager = LinearLayoutManager(this)
        rv_results.adapter = entrepiseAdapter


    }

    fun setupEvents() {
        btn_sport.setOnClickListener(this)
        btn_distance.setOnClickListener(this)
        btn_my_location.setOnClickListener(this)
        sv_search.setOnQueryTextListener(this)
        sv_search.setOnQueryTextFocusChangeListener(this)
    }

    fun setupMap(savedInstanceState: Bundle?) {
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    fun setupBottomSheet() {
        val bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.blocksInteractionBelow(cc, cl_header_bs)
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

    public override fun onResume() {
        super.onResume()
        presenter.onResume()
        mapView.onResume()
        if (checkPermissions() && !::mCurrentLocation.isInitialized) {
            startLocationUpdates();
        } else if (!checkPermissions()) {
            requestPermissions();
        }
    }

    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)

        if (shouldProvideRationale) {

            ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSIONS_REQUEST_CODE)
        } else {

            ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSIONS_REQUEST_CODE)
        }
    }

    private fun checkPermissions(): Boolean {
        var permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    public override fun onPause() {
        super.onPause()
        presenter.onPause()
        mapView.onPause()
        stopLocationUpdates()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putBoolean(REQUESTING_LOCATION_UPDATES_KEY, requestingLocationUpdates)
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState!!)
    }


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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.size <= 0) {

                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // if (requestingLocationUpdates) {
                Log.i(TAG, "Permission granted, updates requested, starting location updates");
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
                        intent.setData(uri);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                */
            }
        }
    }

    override fun onRecoveryPassword(email: String) {

    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btn_sport -> {
                startActivity(Intent(this, ProfileActivity::class.java).putExtra("pk", "33e6528d-f745-4241-9a6b-717838210f52"))
            }
            R.id.btn_distance -> {
                //val selectDistanceFragment = SelectDistanceFragment.newInstance()
                //selectDistanceFragment.show(supportFragmentManager, "SDistance")
            }
            R.id.btn_my_location -> {
                startLocationUpdates()
            }
        }
    }

    override fun onMapReady(mapboxMaps: MapboxMap?) {
        mapboxMap = mapboxMaps!!
        mapboxMap.setOnMarkerClickListener(this)
        iconFactory = IconFactory.getInstance(this)
        mapboxMap.addOnCameraIdleListener(this)

    }

    override fun addMarker(entreprise: Entreprise) {
        var icon = iconFactory.fromResource(R.drawable.ic_ball_futbol)
        var marker = mapboxMap!!.addMarker(MarkerOptions()
                .position(LatLng(entreprise.latitud, entreprise.longitud))
                .icon(icon))

        entreprise.idMarker = marker.id

    }

    fun animateCamera(latLng: LatLng, zoom: Double) {
        val position = CameraPosition.Builder()
                .target(latLng) // Sets the new camera position
                .zoom(zoom) // Sets the zoom
                .build() // Creates a CameraPosition from the builder
        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000)
    }

    override fun onCameraIdle() {
        var bounds = mapboxMap.projection.visibleRegion.latLngBounds

        Log.e("co", "latS" + bounds.latSouth)
        Log.e("co", "latN" + bounds.latNorth)
        Log.e("co", "lngO" + bounds.lonWest)
        Log.e("co", "lngE" + bounds.lonEast)

        Log.e("coordenadas", "lat" + bounds.center.latitude + "lng" + bounds.center.longitude)
        presenter.onGetCenterSportVisible(bounds.latSouth, bounds.latNorth, bounds.lonWest, bounds.lonEast, bounds.center.latitude, bounds.center.longitude)

    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (hasFocus) {
            rv_results_searchs.visibility = View.VISIBLE

            bottom_sheet.visibility = View.GONE
            cl_chips.visibility = View.GONE
            btn_my_location.visibility = View.GONE

        } else {
            rv_results_searchs.visibility = View.GONE

            bottom_sheet.visibility = View.VISIBLE
            cl_chips.visibility = View.VISIBLE
            btn_my_location.visibility = View.VISIBLE
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    override fun navigatioProfile(idEntrepise: Any) {

    }
}
