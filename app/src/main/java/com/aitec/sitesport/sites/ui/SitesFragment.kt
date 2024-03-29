package com.aitec.sitesport.sites.ui

import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import com.aitec.sitesport.MyApplication
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.enterprise.Enterprise
import com.aitec.sitesport.profileEnterprise.ui.ProfileActivity
import com.aitec.sitesport.sites.SitesPresenter
import com.aitec.sitesport.sites.adapter.EntrepiseAdapter
import com.aitec.sitesport.util.BaseActivitys
import kotlinx.android.synthetic.main.fragment_sites.*
import java.util.*
import javax.inject.Inject


class SitesFragment : Fragment(), View.OnClickListener, EntrepiseAdapter.onEntrepiseAdapterListener, SitesView, RadioGroup.OnCheckedChangeListener {
    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {

        when (p0!!.checkedRadioButtonId) {
            R.id.cb_distance -> {
                callback!!.getMyLocation()
                //showProgressBar(true)
                //visibleListSites(View.INVISIBLE)

                /*
                if (p1) {
                    callback!!.getMyLocation()
                    showProgressBar(true)
                    visibleListSites(View.INVISIBLE)
                } else {
                    presenter.addFilterLocation("", false)
                }
                */
                Log.e(TAG, "distancia $p1")
            }
            R.id.cb_open -> {
                presenter.addFilterOpen(true)
                Log.e(TAG, "open $")
            }
            R.id.cb_score -> {
                presenter.addFilterScore(true)
                Log.e(TAG, "puntaje $p1")
            }
        }

    }


    var ubicacion: String = ""

    var callback: onSitesFragmentListener? = null

    /*
    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {

        when (p0!!.pk) {
            R.pk.cb_distance -> {
                if (p1) {
                    callback!!.getMyLocation()
                    showProgressBar(true)
                    visibleListSites(View.INVISIBLE)
                } else {
                    presenter.addFilterLocation("", false)
                }
            }
            R.pk.cb_open -> {
                presenter.addFilterOpen(p1)
            }
            R.pk.cb_score -> {
                presenter.addFilterScore(p1)
            }
        }
    }
    */

    fun visibleListSites(visible: Int) {
        rv_entrepise.visibility = visible
    }

    fun checkedDistanceNoneEvent(isCheck: Boolean) {
        Log.e(TAG, "desabilitando")
        rg_filters.setOnCheckedChangeListener(null)
        cb_distance.isChecked = isCheck
        rg_filters.setOnCheckedChangeListener(this)

    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btn_reload -> {
                presenter.onGetSites()
                rg_filters.setOnCheckedChangeListener(null)
                rg_filters.clearCheck()
                rg_filters.setOnCheckedChangeListener(this)
            }
        }
    }

    override fun navigatioProfile(entrepise: Enterprise) {
        startActivity(Intent(context, ProfileActivity::class.java).putExtra(ProfileActivity.ENTERPRISE, entrepise.pk))
    }


    private val TAG = "MenuFragment"
    private var adapter: EntrepiseAdapter? = null
    private var data: ArrayList<Enterprise>? = ArrayList()

    @Inject
    lateinit var presenter: SitesPresenter
    lateinit var myApplication: MyApplication


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupInjection()
        presenter.subscribe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unSubscribe()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sites, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onGetSites()
        setupRecyclerView()
        setupEvents()
    }

    private fun setupEvents() {
        rg_filters.setOnCheckedChangeListener(this)
        btn_reload.setOnClickListener(this)

    }

    private fun setupRecyclerView() {
        adapter = EntrepiseAdapter(data!!, this)
        rv_entrepise.layoutManager = LinearLayoutManager(context)
        rv_entrepise.adapter = adapter
    }


    private fun setupInjection() {
        myApplication = activity!!.getApplication() as MyApplication
        myApplication.getSitesComponent(this).inject(this)
    }


    companion object {
        fun newInstance(): SitesFragment {
            val fragment = SitesFragment()
            return fragment
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is onSitesFragmentListener) {
            callback = context
        } else {
            throw  RuntimeException(context.toString()
                    + " must implement OnRecoveryPasswordListener");
        }
    }

    override fun showMessagge(message: Any) {
        BaseActivitys.showToastMessage(context!!, message, Toast.LENGTH_LONG)
    }

    override fun setResultsSearchs(listEnterprise: List<Enterprise>) {
        data!!.clear()
        data!!.addAll(listEnterprise)
        data!!.add(getEnterpriseEmpty())
        adapter!!.notifyDataSetChanged()
    }

    fun getEnterpriseEmpty(): Enterprise {
        val enterprise = Enterprise()
        enterprise.viewType = Enterprise.TYPE_SPACE
        return enterprise
    }

    override fun showProgresBar(show: Boolean) {
        if (show)
            progressbar.visibility = View.VISIBLE
        else
            progressbar.visibility = View.GONE
    }

    override fun clearSearchResults() {

    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    //Metodo llamado desde la actividad
    fun setMyLocation(mCurrentLocation: Location) {
        //checkedDistanceNoneEvent(true)
        visibleListSites(View.VISIBLE)
        ubicacion = "${mCurrentLocation.latitude},${mCurrentLocation.longitude}"
        //Log.e(TAG, "MI UBICACION ES $ubicacion")
        presenter.addFilterLocation(mCurrentLocation.latitude, mCurrentLocation.longitude, true)
    }

    interface onSitesFragmentListener {
        fun getMyLocation();
    }

    override fun clearListSites() {
        data!!.clear()
        adapter!!.notifyDataSetChanged()

    }

    override fun showButtonReload(visible: Int) {
        btn_reload.visibility = visible
    }

    override fun enableFilters(enable: Boolean) {
        cb_distance.isEnabled = enable
        cb_open.isEnabled = enable
        cb_score.isEnabled = enable
    }

    override fun showSnackBar(message: String) {
        Snackbar.make(cl_sites, message, Snackbar.LENGTH_INDEFINITE).setAction("Reintentar") {
            presenter.onGetSites()
        }
    }

}
