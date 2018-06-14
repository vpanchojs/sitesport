package com.aitec.sitesport.home.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.aitec.sitesport.MyApplication

import com.aitec.sitesport.R
import com.aitec.sitesport.R.id.progressbar_home
import com.aitec.sitesport.R.id.rv_entrepise_home
import com.aitec.sitesport.entities.Publications
import com.aitec.sitesport.home.HomePresenter
import com.aitec.sitesport.home.adapter.HomeAdapter
import com.aitec.sitesport.home.adapter.onHomeAdapterListener
import com.aitec.sitesport.util.BaseActivitys
import com.aitec.sitesport.util.GlideApp
import kotlinx.android.synthetic.main.fragment_home2.*

import kotlinx.android.synthetic.main.fragment_home2.view.*
import kotlinx.android.synthetic.main.item_home.*
import java.sql.RowId
import java.util.ArrayList
import javax.inject.Inject


class HomeFragment : Fragment(), onHomeAdapterListener, HomeView {
    override fun updatePublicacion(publications: Publications) {
        var pu=findpublicacion(publications.id!!)
        if (pu!=null){
            pu.title=publications.title
            pu.fecha=publications.fecha
            pu.imageIcon= publications.imageIcon
            pu.description=publications.description

            adapterOptions!!.notifyDataSetChanged()
        }

    }

    override fun removePublicacion(publications: Publications) {

    }

    fun findpublicacion(id: String):Publications?{
        return data!!.find { p->
            id.equals(p.id)
        }
    }


    override fun addPublicacion(publicacion: Publications) {

        data!!.add(publicacion)
        adapterOptions!!.notifyDataSetChanged()
    }


    private var publications: Publications? = null
    private var data: ArrayList<Publications>? = ArrayList()
    private var adapterOptions: HomeAdapter? = null

    @Inject
    lateinit var presenter: HomePresenter
    lateinit var myApplication: MyApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupInjection()
        presenter.Suscribe()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onSuscribe()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_home2, container, false)
        view.rv_entrepise_home.layoutManager = LinearLayoutManager(context)
        view.rv_entrepise_home.adapter = adapterOptions

        /*val mDividerItemDecoration = DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL)
        view.rv_entrepise_home.addItemDecoration(mDividerItemDecoration)
        view.rv_entrepise_home.layoutManager = LinearLayoutManager(context,LinearLayout.VERTICAL,false)*/
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.getHome()
        setupRecyclerView()


        //setupMenuOptions()
    }

    private fun setupRecyclerView() {
        adapterOptions = HomeAdapter(data!!, this)
        rv_entrepise_home.layoutManager = LinearLayoutManager(context)
        rv_entrepise_home.adapter = adapterOptions
    }

    private fun setupInjection() {
        myApplication = activity!!.getApplication() as MyApplication
        myApplication.getHomeComponent(this).inject(this)
    }

    override fun setDataPublications(publications: List<Publications>) {

        data!!.clear()
        data!!.addAll(publications)
        adapterOptions!!.notifyDataSetChanged()

        /*this.publications = publications

        GlideApp.with(context!!)
                .load(publications.photo)
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .error(R.mipmap.ic_launcher)
                .into(civ_center)


        txttitle.text = publications.title
        txtfecha.text=publications.fecha

        GlideApp.with(context!!)
                .load(publications.imageIcon)
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .error(R.mipmap.ic_launcher)
                .into(img_imagen)

        txtdescription.text = publications.description*/

    }


    companion object {
        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            return fragment
        }
    }

    override fun showMessagge(message: Any) {
        BaseActivitys.showToastMessage(context!!, message, Toast.LENGTH_LONG)
    }

    override fun setResultsSearchs(listNotices: List<Publications>) {
        data!!.clear()
        data!!.addAll(listNotices)
        adapterOptions!!.notifyDataSetChanged()

    }

    override fun showProgresBar(show: Boolean) {
        if (show)
            progressbar_home.visibility = View.VISIBLE
        else
            progressbar_home.visibility = View.GONE
    }

    override fun clearSearchResults() {

    }

    override fun clearListNotices() {
        data!!.clear()
        adapterOptions!!.notifyDataSetChanged()
    }

    override fun navigatioProfile(position: Int) {
    }



}
    /*override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is onHomeFragmentListener) {
            callback = context
        } else {
            throw RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }*/




// Required empty public constructor
