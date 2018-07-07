package com.aitec.sitesport.home.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.aitec.sitesport.MyApplication
import com.aitec.sitesport.R
import com.aitec.sitesport.champions.ChampionShipActivity
import com.aitec.sitesport.domain.listeners.onApiActionListener
import com.aitec.sitesport.entities.Publication
import com.aitec.sitesport.home.HomePresenter
import com.aitec.sitesport.home.adapter.HomeAdapter
import com.aitec.sitesport.home.adapter.onHomeAdapterListener
import com.aitec.sitesport.main.ui.MainActivity
import com.aitec.sitesport.publication.ui.PublicationActivity
import com.aitec.sitesport.util.BaseActivitys
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_publications.*
import kotlinx.android.synthetic.main.fragment_publications.view.*
import java.util.*
import javax.inject.Inject


class HomeFragment : Fragment(), onHomeAdapterListener, HomeView {

    override fun showInfo(msg: String?) {
        if (msg != null) {
            tvInfoPublications.text = msg
            if (tvInfoPublications.visibility == View.GONE)
                tvInfoPublications.visibility = View.VISIBLE
        } else
            if (tvInfoPublications.visibility == View.VISIBLE)
                tvInfoPublications.visibility = View.GONE
    }

    @Inject
    lateinit var presenter: HomePresenter
    lateinit var myApplication: MyApplication
    private var data: ArrayList<Publication> = arrayListOf()
    private var adapterPublications: HomeAdapter? = null


    // CAMBIAR ESTO A FUTURO ===========================================
    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    // ==============================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupInjection()
        presenter.Suscribe()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_publications, container, false)
        view.rvPublications.layoutManager = LinearLayoutManager(context)
        view.rvPublications.adapter = adapterPublications
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getHome()
        setupRecyclerView()
        tvInfoPublications.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onSuscribe()
        presenter.remove()
    }

    override fun updatePublication(publication: Publication) {
        val pu = findPublication(publication.pk)
        if (pu != null) {
            pu.titulo = publication.titulo
            pu.fecha = publication.fecha
            pu.icon = publication.icon
            pu.descripcion = publication.descripcion
            adapterPublications!!.notifyDataSetChanged()
        }
    }

    override fun removePublication(publication: Publication) {}

    override fun addPublication(publication: Publication) {
        data.add(publication)
        adapterPublications!!.notifyDataSetChanged()
    }

    override fun showMessage(message: Any) {
        BaseActivitys.showToastMessage(context!!, message, Toast.LENGTH_LONG)
    }

    override fun showProgressBar(show: Boolean) {
        if (show) pbPublications.visibility = View.VISIBLE else pbPublications.visibility = View.GONE
    }

    override fun navigatioProfile(position: Int) {
        if (mAuth.currentUser != null) {
            var i: Intent? = null
            when (data[position].type) {
                Publication.LINK_PUBLICATION_EVENT -> {
                    i = Intent(context, ChampionShipActivity::class.java)
                }
                Publication.LINK_PUBLICATION_PROMO -> {
                    i = Intent(context, PublicationActivity::class.java)
                }
            }
            i!!.putExtra(Publication.PUBLICATION, data[position].pk)
            startActivity(i)
        } else {
            (context!! as MainActivity).goLogin()
        }
    }

    override fun sharePublication(publication: Publication) {
        BaseActivitys.showToastMessage(context!!, "Obteniendo aplicaciones...", Toast.LENGTH_LONG)
        BaseActivitys.buildDinamycLinkShareApp(publication.pk, publication.type, object : onApiActionListener<String> {
            override fun onSucces(response: String) {
                intentShared(response)
            }

            override fun onError(error: Any?) {
                intentShared(null)
            }
        })
    }

    private fun setupRecyclerView() {
        adapterPublications = HomeAdapter(data, this)
        rvPublications.layoutManager = LinearLayoutManager(context)
        rvPublications.adapter = adapterPublications
    }

    private fun setupInjection() {
        myApplication = activity!!.getApplication() as MyApplication
        myApplication.getHomeComponent(this).inject(this)
    }

    private fun intentShared(link: String?) {
        var auxLink = " ${resources.getString(R.string.url_play_store)}"
        if (link != null) auxLink = " $link"
        val i = Intent(android.content.Intent.ACTION_SEND)
        i.type = "text/plain"
        i.putExtra(android.content.Intent.EXTRA_SUBJECT, R.string.app_name)
        i.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.textSharePublication) + auxLink)
        startActivity(Intent.createChooser(i, "Compartir mediante..."))
    }

    private fun findPublication(id: String): Publication? {
        return data.find { p ->
            id.equals(p.pk)
        }
    }

    companion object {
        const val TAG = "HomeFragment"
        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            return fragment
        }
    }
}
