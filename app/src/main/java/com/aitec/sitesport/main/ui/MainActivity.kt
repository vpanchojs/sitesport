package com.aitec.sitesport.main.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.support.design.widget.AppBarLayout
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.aitec.sitesport.MyApplication
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.enterprise.Enterprise
import com.aitec.sitesport.home.ui.HomeFragment
import com.aitec.sitesport.main.MainPresenter
import com.aitec.sitesport.main.adapter.SearchNamesEntrepiseAdapter
import com.aitec.sitesport.mapSites.ui.MapSitesActivity
import com.aitec.sitesport.menu.ui.MenuFragment
import com.aitec.sitesport.profileEnterprise.ui.ProfileActivity
import com.aitec.sitesport.publication.PublicationActivity
import com.aitec.sitesport.record.RecordFragment
import com.aitec.sitesport.sites.ui.SitesFragment
import com.aitec.sitesport.util.BaseActivitys
import com.aitec.sitesport.welcome.WelcomeActivity
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener, MainView, SearchNamesEntrepiseAdapter.onEntrepiseSearchNameListener, SitesFragment.onSitesFragmentListener {


    override fun navigatioProfile(entrepise: Enterprise) {
        startActivity(Intent(this, ProfileActivity::class.java).putExtra(ProfileActivity.ENTERPRISE, entrepise.pk))
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


    val TAG = "MainActivity"
    lateinit var application: MyApplication
    @Inject
    lateinit var presenter: MainPresenter
    lateinit var fragment: Fragment
    var params: AppBarLayout.LayoutParams? = null
    var searchrResultList = ArrayList<Enterprise>()

    var searchResultsAdapter: SearchNamesEntrepiseAdapter? = null

    private val mOnNavigationItemSelectedListener = OnNavigationItemSelectedListener { item ->
        when (item.itemId) {

            R.id.navigation_home -> {
                btn_map.visibility = View.INVISIBLE
                toolbar.title="Inicio"
                fragment = HomeFragment.newInstance()
            }
            R.id.navigation_site -> {
                btn_map.visibility = View.VISIBLE
                toolbar.title="Sitios Deportivos"
                fragment = SitesFragment.newInstance()
            }
            R.id.navigation_my_reserve -> {
                btn_map.visibility = View.INVISIBLE
                toolbar.title="Mis Reservas"
                fragment = RecordFragment.newInstance()

            }

            R.id.navigation_menu -> {
                btn_map.visibility = View.INVISIBLE
                toolbar.title="Menú"
                fragment = MenuFragment.newInstance()
            }
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_container, fragment).commitAllowingStateLoss()
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolBar()
        onGetDynamicLink()
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        setupInjection()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mSettingsClient = LocationServices.getSettingsClient(this)
        createLocationCallback()
        createLocationRequest()
        buildLocationSettingsRequest()

        searchResultsAdapter = SearchNamesEntrepiseAdapter(searchrResultList, this)
        rv_results_searchs.layoutManager = LinearLayoutManager(this)
        rv_results_searchs.adapter = searchResultsAdapter
        navigation.selectedItemId = R.id.navigation_site
        btn_map.setOnClickListener {
            //permission()
            startActivity(Intent(this, MapSitesActivity::class.java))
        }
    }

    private fun onGetDynamicLink() {
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener {
                    if (it != null && it.link.getQueryParameter("type") != null) {

                        when(it.link.getQueryParameter("type").toInt()){

                            //compartir centro deportivo
                            BaseActivitys.LINK_ENTERPRISE -> {
                                startActivity(
                                        Intent(this, ProfileActivity::class.java)
                                                .putExtra(
                                                        ProfileActivity.ENTERPRISE,
                                                        it.link.getQueryParameter("id")
                                                )
                                )
                            }

                            //compartir publicación
                            BaseActivitys.LINK_PUBLICATION -> {
                                startActivity(
                                        Intent(this, PublicationActivity::class.java)
                                                .putExtra(
                                                        PublicationActivity.PUBLICATION,
                                                        it.link.getQueryParameter("id")
                                                )
                                )
                            }

                        }
                    }
                }
                .addOnFailureListener {

                }
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
        presenter.initfirstRun()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    private fun setupInjection() {
        application = getApplication() as MyApplication
        application.getMainComponent(this).inject(this)
    }


    private fun setupToolBar() {
        params = toolbar.layoutParams as AppBarLayout.LayoutParams
        settoolbarScroll(true)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.app_name)
    }

    private fun settoolbarScroll(active: Boolean) {
        if (active)
            params!!.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
        else
            params!!.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val menuItem = menu!!.findItem(R.id.action_search)
        menuItem.setOnActionExpandListener(this)
        val searchView = menuItem.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setQueryHint("Buscar sitios deportivos")
        searchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onMenuItemActionExpand(item: MenuItem): Boolean {
        Log.e("*******", "onMenuItemActionExpand")
        settoolbarScroll(false)
        navigation.visibility = View.GONE
        nsv_container.visibility = View.GONE
        rv_results_searchs.visibility = View.VISIBLE
        return true
    }

    override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
        Log.e("*******", "onMenuItemActionCollapse")
        settoolbarScroll(true)
        rv_results_searchs.visibility = View.GONE
        nsv_container.visibility = View.VISIBLE
        navigation.visibility = View.VISIBLE
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    /*Enviar ubicacion a la vista de lista de sitios deportivos*/
    fun sendMyLocation() {
        if (fragment is SitesFragment) {
            (fragment as SitesFragment).setMyLocation(mCurrentLocation)
        }
    }

    /*Notificar imposibilidad de obtener la ubicacion*/
    fun imposibleGetLocacion() {
        if (fragment is SitesFragment) {
            (fragment as SitesFragment).checkedDistanceNoneEvent(false)
        }
    }


    override fun onQueryTextChange(newText: String?): Boolean {
        presenter.stopSearchName()
        presenter.getSearchName(newText!!)
        return true
    }

    override fun showMessagge(message: Any) {
        if (message is Int) {
            Toast.makeText(this, getString(message), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, message.toString(), Toast.LENGTH_SHORT).show()
        }
    }


    override fun setResultsSearchs(listUser: ArrayList<Enterprise>) {
        if (listUser.size <= 0) {
            Log.e(TAG, "lista vacia")
            tv_message_search_results.visibility = View.VISIBLE
            searchResultsAdapter!!.data.clear()
            searchResultsAdapter!!.notifyDataSetChanged()

        } else {
            tv_message_search_results.visibility = View.GONE
            searchResultsAdapter!!.data.clear()
            for (user in listUser) {
                searchrResultList.add(user)
            }
            searchResultsAdapter!!.notifyDataSetChanged()

        }
    }


    override fun showProgresBar(show: Boolean) {
        if (show) pb_search.visibility = View.VISIBLE else pb_search.visibility = View.GONE
    }


    override fun clearSearchResults() {
        tv_message_search_results.visibility = View.GONE
        searchrResultList.clear()
        searchResultsAdapter!!.notifyDataSetChanged()
    }


    /*Codigo ubicacion*/

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
                    stopLocationUpdates()
                    sendMyLocation()
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
                                rae.startResolutionForResult(this@MainActivity, REQUEST_CHECK_SETTINGS)
                            } catch (se: IntentSender.SendIntentException) {
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

    fun getLocation() {
        if (checkPermissions()) {
            startLocationUpdates();
        } else {
            requestPermissions();
        }
    }


    /*Permisos*/
    private fun requestPermissions() {

        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)


        ActivityCompat.requestPermissions(this@MainActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_PERMISSIONS_REQUEST_CODE)

        /*
        Log.e("permisos", "pidiendo permiso" + shouldProvideRationale)
        if (shouldProvideRationale) {
            Log.e("permisos", "true")
            ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSIONS_REQUEST_CODE)
        } else {

            ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSIONS_REQUEST_CODE)

        }
        */
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
                startLocationUpdates();

            } else {
                Log.e(TAG, "no se han habilitado los permisos")
                imposibleGetLocacion()
                //notificar que el permiso no ha sido concedido
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
                        Log.e(TAG, "User agreed to make required location settings changes.")
                        startLocationUpdates();
// Nothing to do. startLocationupdates() gets called in onResume again.
                    }
                    Activity.RESULT_CANCELED -> {
                        Log.e(TAG, "User chose not to make required location settings changes.")
                        imposibleGetLocacion()

                        //requestingLocationUpdates = false;
                        //updateUI();
                    }
                }
            }
        }
    }

    override fun getMyLocation() {
        getLocation()
    }

    override fun navigationWelcome() {
        startActivity(Intent(this, WelcomeActivity::class.java))
    }
}
