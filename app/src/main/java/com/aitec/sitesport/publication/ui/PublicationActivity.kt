package com.aitec.sitesport.publication.ui

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.aitec.sitesport.MyApplication
import com.aitec.sitesport.R
import com.aitec.sitesport.domain.listeners.onApiActionListener
import com.aitec.sitesport.entities.Publication
import com.aitec.sitesport.profileEnterprise.ui.adapter.ImageAdapter
import com.aitec.sitesport.publication.PublicationPresenter
import com.aitec.sitesport.util.BaseActivitys
import kotlinx.android.synthetic.main.activity_publication.*
import kotlinx.android.synthetic.main.content_publication.*
import java.util.ArrayList
import javax.inject.Inject

class PublicationActivity : AppCompatActivity(), PublicationView {

    @Inject
    lateinit var publicationPresenter: PublicationPresenter
    private var publication: Publication = Publication()
    private var imageAdapter: ImageAdapter? = null
    private var images: ArrayList<String> = arrayListOf()
    private var snackBarInfo: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publication)
        setupInjection()
        publicationPresenter.register()
        publication.pk = intent.getStringExtra(PUBLICATION)
        setupUI()
        callPublication()
    }

    override fun onDestroy() {
        publicationPresenter.unregister()
        super.onDestroy()
    }

    // implementation PublicationView.kt

    override fun setPublication(p: Publication){
        publication = p
        updateView()
    }

    override fun showPbLoading() {
        pbLoading.visibility = View.VISIBLE
    }

    override fun hidePbLoading() {
        pbLoading.visibility = View.GONE
    }

    override fun showSnackBar(msg: String) {
        if (snackBarInfo != null && !snackBarInfo!!.isShown) {
            snackBarInfo!!.setText(msg)
            snackBarInfo!!.show()
        }
    }



    // setup view

    private fun updateView(){
        updateSectionPhoto()
        toolbar_layout.title = publication.nombre_centro_deportivo
        tvNameEnterprise.text = publication.titulo
        tvDate.text = publication.fecha
        tvDescription.text = publication.descripcion
    }

    private fun updateSectionPhoto(){
        images.clear()
        images.add(publication.foto)
        /*for (url in imageList) {
            images.add(url)
        }*/
        viewPagerImagesProfile.adapter!!.notifyDataSetChanged()
    }

    private fun setupUI(){
        setupToolBar()
        setupPhotosSection()
        snackBarInfo = Snackbar.make(clMainScreen, "", Snackbar.LENGTH_INDEFINITE).setAction("REINTENTAR") {
            callPublication()
        }
    }

    private fun callPublication(){
        publicationPresenter.callPublication(publication.pk!!)
    }

    private fun setupPhotosSection() {
        imageAdapter = ImageAdapter(supportFragmentManager, images)
        viewPagerImagesProfile.adapter = imageAdapter
        tabImageProfileDots.setupWithViewPager(viewPagerImagesProfile, true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_publication, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()
        else if (item.itemId == R.id.action_share)
            sharePublication()
        return super.onOptionsItemSelected(item)
    }

    private fun sharePublication() {
        BaseActivitys.showToastMessage(this, "Obteniendo aplicaciones...", Toast.LENGTH_LONG)
        BaseActivitys.buildDinamycLinkShareApp(publication.pk, BaseActivitys.LINK_PUBLICATION, object : onApiActionListener<String> {
            override fun onSucces(response: String) {
                intentShared(response)
            }

            override fun onError(error: Any?) {
                intentShared(null)
            }
        })
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

    private fun setupToolBar() {
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.title = ""
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }
    }

    private fun setupInjection() {
        val app: MyApplication = this.application as MyApplication
        val publicationComponent = app.getPublicationComponent(this)
        publicationComponent.inject(this)
    }

    companion object {
        const val PUBLICATION = "publication"
    }
}
