package com.aitec.sitesport.work


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.aitec.sitesport.R
import com.aitec.sitesport.util.BaseActivitys
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_workme.*
import java.io.IOException
import java.util.*
import java.util.regex.Pattern


class Workme : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnCameraIdleListener {
    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!

        val cameraPosition = CameraPosition.Builder()
                .target(com.google.android.gms.maps.model.LatLng(-4.008100, -79.21083))
                .zoom(17F)
                .build()

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

        mMap.setOnCameraIdleListener(this)
    }


    override fun onCameraIdle() {
        var center = mMap.cameraPosition.target

        var addresses: List<Address> = emptyList()
        val geocoder = Geocoder(this, Locale.getDefault())
        var errorMessage = ""


        try {
            addresses = geocoder.getFromLocation(
                    center.latitude,
                    center.longitude,
                    1)

        } catch (ioException: IOException) {
            // Catch network or other I/O problems.
            errorMessage = getString(R.string.service_not_available)
            Log.e(TAG, errorMessage, ioException)
        } catch (illegalArgumentException: IllegalArgumentException) {
            // Catch invalid latitude or longitude values.
            errorMessage = getString(R.string.invalid_lat_long_used)
            Log.e(TAG, "$errorMessage. Latitude = $center.latitude , " +
                    "Longitude =  $center.longitude", illegalArgumentException)
        }

        if (addresses.isEmpty()) {
            if (errorMessage.isEmpty()) {
                errorMessage = getString(R.string.no_address_found)
                Log.e(TAG, errorMessage)
            }
            deliverResultToReceiver(Constants.FAILURE_RESULT, errorMessage)
        } else {
            val address = addresses[0]
            // Fetch the address lines using getAddressLine,
            // join them, and send them to the thread.
            val addressFragments = with(address) {
                (0..maxAddressLineIndex).map { getAddressLine(it) }
            }
            Log.i(TAG, getString(R.string.address_found))
            deliverResultToReceiver(Constants.SUCCESS_RESULT,
                    addressFragments.joinToString(separator = "\n"))
            tie_address.setText(address.getAddressLine(0))
        }

        // Handle case where no address was found.


    }

    private fun deliverResultToReceiver(resultCode: Int, message: String) {
        val bundle = Bundle().apply { putString(Constants.RESULT_DATA_KEY, message) }
        //receiver?.send(resultCode, bundle)
    }


    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var mSettingsClient: SettingsClient
    private lateinit var mCurrentLocation: Location
    private lateinit var mLocationSettingsRequest: LocationSettingsRequest

    private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    private val REQUEST_CHECK_SETTINGS = 0x1
    private val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 1000
    private val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2

    val TAG = "WorkMe"
    lateinit var mMap: GoogleMap

    val REQUESTING_LOCATION_UPDATES_KEY = "location"
    var requestingLocationUpdates = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workme)
        setupactionbar()
        setupMap()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mSettingsClient = LocationServices.getSettingsClient(this)
        createLocationCallback()
        createLocationRequest()
        buildLocationSettingsRequest()

        btn_location.setOnClickListener {
            permission()
        }

        BaseActivitys.onTextChangedListener(arrayListOf(tie_name, tie_phone, tie_email, tie_phone), btn_send)
        btn_send.setOnClickListener {
            enviar()
        }

    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {

            android.R.id.home -> {
                finish()
            }
        }

        return super.onContextItemSelected(item)

    }

    private fun setupactionbar() {
        val ActionBar = supportActionBar
        if (ActionBar != null) {
            ActionBar.setDisplayHomeAsUpEnabled(true)
        }

    }

    private fun setupMap() {
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    fun isEmailValid(email: String): Boolean {
        var isValid = false

        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"


        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        if (matcher.matches()) {
            isValid = true
        }
        return isValid
    }

    fun enviar() {

        val CC = "soporte@aitecec.com"
        val TO = arrayOf<String>(CC.toString())
        val email = Intent(android.content.Intent.ACTION_SEND)
        email.setType("text/html")
        email.putExtra(Intent.EXTRA_EMAIL, TO)
        email.putExtra(Intent.EXTRA_CC, CC)
        email.putExtra(android.content.Intent.EXTRA_TITLE, "SITESPORT")
        email.putExtra(android.content.Intent.EXTRA_SUBJECT, "TRABAJA CON NOSOTROS")
        email.putExtra(android.content.Intent.EXTRA_TEXT, "Nombre del sitio deportivo: ${tie_name.text} + '\n'  Teléfono :  ${tie_phone.text}  '\n'  Dirección: ${tie_address.text}  '\n'  Correo electronico: ${tie_email.text}")

        try {
            startActivity(Intent.createChooser(email, "enviar Email"))
        } catch (e: IOException) {
            e.printStackTrace()

        }

    }


    public override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putBoolean(REQUESTING_LOCATION_UPDATES_KEY, requestingLocationUpdates)
        super.onSaveInstanceState(outState)
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
                    if (::mMap.isInitialized) {
                        animateCamera(LatLng(mCurrentLocation.latitude, mCurrentLocation.longitude), 16.0)
                        Log.e("address", mCurrentLocation.toString())
                        stopLocationUpdates()
                    }

                }
            }

        }
    }


    fun animateCamera(latLng: LatLng, zoom: Double) {
        val cameraPosition = CameraPosition.Builder()
                .target(latLng)
                .zoom(17F)
                .build()

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    @SuppressLint("RestrictedApi")
    fun createLocationRequest() {
        locationRequest = LocationRequest().apply {
            interval = UPDATE_INTERVAL_IN_MILLISECONDS
            fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
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
                                rae.startResolutionForResult(this@Workme, REQUEST_CHECK_SETTINGS)
                            } catch (se: IntentSender.SendIntentException) {
                                //   Log.i(FragmentActivity.TAG, "PendingIntent unable to execute request.")
                            }

                        }
                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                            val errorMessage = "Location settings are inadequate, and cannot be " + "fixed here. Fix in Settings."
                            //   Log.e(FragmentActivity.TAG, errorMessage)
                            Toast.makeText(this@Workme, errorMessage, Toast.LENGTH_LONG).show()
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
            ActivityCompat.requestPermissions(this@Workme,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSIONS_REQUEST_CODE)
        } else {

            ActivityCompat.requestPermissions(this@Workme,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSIONS_REQUEST_CODE)

        }
    }

    private fun checkPermissions(): Boolean {
        var permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }


    object Constants {
        const val SUCCESS_RESULT = 0
        const val FAILURE_RESULT = 1
        const val PACKAGE_NAME = "com.google.android.gms.location.sample.locationaddress"
        const val RECEIVER = "$PACKAGE_NAME.RECEIVER"
        const val RESULT_DATA_KEY = "${PACKAGE_NAME}.RESULT_DATA_KEY"
        const val LOCATION_DATA_EXTRA = "${PACKAGE_NAME}.LOCATION_DATA_EXTRA"
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.size <= 0) {

                Log.e(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();

            } else {

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


}





