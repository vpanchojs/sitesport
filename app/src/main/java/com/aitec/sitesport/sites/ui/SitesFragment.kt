package com.aitec.sitesport.sites.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import com.aitec.sitesport.MyApplication
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.enterprise.Enterprise
import com.aitec.sitesport.profile.ui.ProfileActivity
import com.aitec.sitesport.sites.SitesPresenter
import com.aitec.sitesport.sites.adapter.EntrepiseAdapter
import com.aitec.sitesport.util.BaseActivitys
import kotlinx.android.synthetic.main.fragment_sites.*
import javax.inject.Inject

class SitesFragment : Fragment(), View.OnClickListener, EntrepiseAdapter.onEntrepiseAdapterListener, SitesView, CompoundButton.OnCheckedChangeListener {


    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
        when (p0!!.id) {

            R.id.cb_distance -> {

            }
            R.id.cb_open -> {

            }
            R.id.cb_score -> {

            }
        }
    }


    override fun onClick(p0: View?) {

    }

    override fun navigatioProfile(entrepise: Enterprise) {
        startActivity(Intent(context, ProfileActivity::class.java).putExtra(ProfileActivity.ENTERPRISE, entrepise))
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
        var view = inflater!!.inflate(R.layout.fragment_sites, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onGetSites()
        setupRecyclerView()
        setupEvents()
    }

    private fun setupEvents() {
        cb_distance.setOnCheckedChangeListener(this)
        cb_open.setOnCheckedChangeListener(this)
        cb_score.setOnCheckedChangeListener(this)
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


    override fun showMessagge(message: Any) {
        BaseActivitys.showToastMessage(context!!, message, Toast.LENGTH_LONG)
    }

    override fun setResultsSearchs(listEnterprise: List<Enterprise>) {
        data!!.clear()
        data!!.addAll(listEnterprise)
        adapter!!.notifyDataSetChanged()
    }

    override fun showProgresBar(show: Boolean) {
        if (show)
            progressbar.visibility = View.VISIBLE
        else
            progressbar.visibility = View.GONE
    }

    override fun clearSearchResults() {

    }


}
