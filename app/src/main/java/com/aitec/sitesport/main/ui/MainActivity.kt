package com.aitec.sitesport.main.ui

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.View
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.Entrepise
import com.aitec.sitesport.main.adapter.EntrepiseAdapter
import com.aitec.sitesport.profile.ProfileActivity
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.IconFactory
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet_results.*


class MainActivity : AppCompatActivity(), EntrepiseAdapter.onEntrepiseAdapterListener, SelectDistanceFragment.OnSelectDistanceListener, SearchView.OnQueryTextListener, View.OnFocusChangeListener {
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

    lateinit var entrepiseAdapter: EntrepiseAdapter
    var data = ArrayList<Entrepise>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(this, "pk.eyJ1IjoidnBhbmNob2pzIiwiYSI6ImNqN2gzdXdrbzFkejEyeG82Z2IyaDcxazUifQ.LMqRVTqNyzGOtED90lMtZA");
        setContentView(R.layout.activity_main)
        setupBottomSheet()
        btn_sport.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync({
            Log.e("HOLA", "mapa")
            val iconFactory = IconFactory.getInstance(this)
            var icon = iconFactory.fromResource(R.drawable.ic_ball_futbol)
            it.addMarker(MarkerOptions()
                    .position(LatLng(-4.028872, -79.213712))
                    .icon(icon))

            val position = CameraPosition.Builder()
                    .target(LatLng(-4.028872, -79.213712)) // Sets the new camera position
                    .zoom(17.0) // Sets the zoom
                    .build() // Creates a CameraPosition from the builder
            it.animateCamera(CameraUpdateFactory.newCameraPosition(position), 1000)
        })

        btn_distance.setOnClickListener {
            val selectDistanceFragment = SelectDistanceFragment.newInstance()
            selectDistanceFragment.show(supportFragmentManager, "Recuperar Contrasena")
        }

        data.add(Entrepise("Sitio Deportivo"))
        data.add(Entrepise("Centro De Deportes"))
        entrepiseAdapter = EntrepiseAdapter(data, this)
        var mDividerItemDecoration = DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL)
        rv_results.addItemDecoration(mDividerItemDecoration)
        rv_results.layoutManager = LinearLayoutManager(this)
        rv_results.adapter = entrepiseAdapter

        sv_search.setOnQueryTextListener(this)
        sv_search.setOnQueryTextFocusChangeListener(this)

    }

    fun setupBottomSheet() {
        val bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)

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

    public override fun onResume() {
        super.onResume()
        mapView.onResume()
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
        mapView.onPause()
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
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState!!)
    }

    override fun onRecoveryPassword(email: String) {

    }
}
