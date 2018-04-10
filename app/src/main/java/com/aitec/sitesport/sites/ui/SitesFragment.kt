package com.aitec.sitesport.sites.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ToggleButton
import com.aitec.sitesport.MyApplication
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.enterprise.Enterprise
import com.aitec.sitesport.profile.ui.ProfileActivity
import com.aitec.sitesport.sites.SitesPresenter
import com.aitec.sitesport.sites.adapter.EntrepiseAdapter
import kotlinx.android.synthetic.main.fragment_sites.*
import javax.inject.Inject

class SitesFragment : Fragment(), View.OnClickListener, EntrepiseAdapter.onEntrepiseAdapterListener, SitesView, CompoundButton.OnCheckedChangeListener {


    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
        when (p0!!.id) {

            R.id.tb_distance -> {
                setBackground(p0 as ToggleButton, p1)
            }
            R.id.tb_open -> {
                setBackground(p0 as ToggleButton, p1)
            }
            R.id.tb_favorites -> {
                setBackground(p0 as ToggleButton, p1)
            }
        }
    }


    fun setBackground(tb: ToggleButton, state: Boolean) {
        if (state) {
            tb.background = resources.getDrawable(R.drawable.bg_chip_on)
            tb.setTextColor(resources.getColor(R.color.white))
        } else {
            tb.background = resources.getDrawable(R.drawable.bg_chip_off)
            tb.setTextColor(resources.getColor(R.color.colorDisableIButton))
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
        tb_distance.setOnCheckedChangeListener(this)
        tb_open.setOnCheckedChangeListener(this)
        tb_favorites.setOnCheckedChangeListener(this)
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

    }

    override fun setResultsSearchs(listEnterprise: List<Enterprise>) {
        data!!.clear()
        data!!.addAll(listEnterprise)
        adapter!!.notifyDataSetChanged()
    }

    override fun showProgresBar(show: Boolean) {

    }

    override fun clearSearchResults() {

    }


}
