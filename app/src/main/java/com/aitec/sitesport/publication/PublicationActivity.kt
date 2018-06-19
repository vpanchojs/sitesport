package com.aitec.sitesport.publication

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.aitec.sitesport.R
import com.aitec.sitesport.util.BaseActivitys
import kotlinx.android.synthetic.main.activity_profile_enterprise.*

import kotlinx.android.synthetic.main.activity_publication.*

class PublicationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publication)
        setupToolBar()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        BaseActivitys.showToastMessage(this, intent.getStringExtra(PublicationActivity.PUBLICATION), Toast.LENGTH_SHORT)
    }

    private fun setupToolBar(){
        setSupportActionBar(toolbarPublication)
        if (supportActionBar != null) {
            //supportActionBar!!.title = ""
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val PUBLICATION = "publication"
    }

}
