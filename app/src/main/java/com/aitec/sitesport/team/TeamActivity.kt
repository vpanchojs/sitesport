package com.aitec.sitesport.team

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.aitec.sitesport.MyApplication
import com.aitec.sitesport.R
import com.aitec.sitesport.domain.FirebaseApi
import com.aitec.sitesport.domain.listeners.onApiActionListener
import com.aitec.sitesport.entities.Team
import com.aitec.sitesport.util.BaseActivitys
import com.aitec.sitesport.util.GlideApp
import kotlinx.android.synthetic.main.activity_team.*
import kotlinx.android.synthetic.main.content_team.*
import java.net.URL

class TeamActivity : AppCompatActivity() {

    var team: Team?= null
    private var firebaseApi: FirebaseApi? = null
    val list : MutableList<String>  = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)
        setupInjection()
        team = intent.extras.getParcelable("team")
        setupToolBar()
        toolbar_layout.title = team!!.nombre
        getTeam()
        val adapter = PlayerAdapter(list)
        rvPlayers.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvPlayers.adapter = adapter
    }

    private fun getTeam(){
        firebaseApi!!.getTeam(team!!.pk, object : onApiActionListener<Team> {
            override fun onError(error: Any?) {}

            override fun onSucces(response: Team) {
                team = response
                loadImage()
                for(name in team!!.jugadores){
                    list.add(name)
                }
                rvPlayers.adapter.notifyDataSetChanged()
            }

        })
    }

    private fun setupInjection() {
        val application = getApplication() as MyApplication
        firebaseApi = application.domainModule!!.providesFirebaseApi()
    }


    private fun setupToolBar() {
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.title = ""
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }
    }

    private fun loadImage(){
        if(this != null && imgTeam != null && team!!.foto.isNotBlank()) {
            GlideApp.with(this)
                    .load(URL(team!!.foto).toString())
                    .placeholder(R.drawable.ic_bg_balon)
                    .centerCrop()
                    .error(R.drawable.ic_bg_balon)
                    .into(imgTeam)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
