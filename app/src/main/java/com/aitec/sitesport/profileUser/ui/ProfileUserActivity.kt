package com.aitec.sitesport.profileUser.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.aitec.sitesport.MyApplication
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.User
import com.aitec.sitesport.profileUser.ProfileUserPresenter
import com.aitec.sitesport.util.BaseActivitys
import com.aitec.sitesport.util.GlideApp
import kotlinx.android.synthetic.main.activity_profile_user.*
import javax.inject.Inject

class ProfileUserActivity : AppCompatActivity(), ProfileUserView, View.OnClickListener {


    companion object {
        const val USER = "user"
    }

    lateinit var user: User

    val application: MyApplication by lazy {
        getApplication() as MyApplication
    }

    @Inject
    lateinit var presenter: ProfileUserPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_user)
        setupToolbar()
        setupInject()
        setupEventsElements()
        setupValidationInputs()
        setDataProfile(intent.extras.getParcelable(USER))
        presenter.onSubscribe()
        presenter.getInfoUser()
    }

    private fun setDataProfile(user: User) {
        this.user = user
        tie_names.setText(user.names.toString())
        tie_email.setText(user.email)
        GlideApp.with(this)
                .load(user.photo)
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .error(R.mipmap.ic_launcher)
                .into(civ_user)
    }

    private fun setupValidationInputs() {
        BaseActivitys.onTextChangedListener(arrayListOf(tie_names, tie_dni, tie_phone), btn_update)
    }

    private fun setupEventsElements() {
        btn_update.setOnClickListener(this)
    }


    override fun onDestroy() {
        super.onDestroy()
        presenter.onUnSubscribe()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Perfil de Usuario"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupInject() {
        application.getProfileUserComponent(this).inject(this)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btn_update -> {
                presenter.updateInfoUser(
                        tie_names.text.toString(),
                        tie_dni.text.toString(),
                        tie_phone.text.toString(),
                        user.photo.toString()
                )
            }
        }
    }

    override fun showMessagge(message: Any) {
        BaseActivitys.showToastMessage(this, message, Toast.LENGTH_SHORT)
    }

    override fun showProgresBar(show: Int) {
        progressbar.visibility = show
    }

    override fun showButtonUpdate(show: Int) {
        btn_update.visibility = show
    }

    override fun showErrorDniInput(message: Any) {
        tie_dni.error = message.toString()
    }

    override fun showErrorPhoneInput(message: Any) {
        tie_phone.error = message.toString()
    }

    override fun showViewInfo(visibility: Int) {
        cl_info_get_user.visibility = visibility
    }

    override fun setInfoUser(dni: String?, phone: String?) {
        tie_dni.setText(dni)
        tie_phone.setText(phone)
    }

    override fun showButtonReload(visibility: Int) {
        btn_reload.visibility = visibility
    }

    override fun showProgressAndMessagin(visibility: Int) {
        progressbar_get_info_user.visibility = visibility
        tv_info.visibility = visibility
    }

}
