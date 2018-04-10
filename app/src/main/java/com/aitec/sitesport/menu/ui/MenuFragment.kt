package com.aitec.sitesport.menu.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.aitec.sitesport.MyApplication
import com.aitec.sitesport.R
import com.aitec.sitesport.entities.User
import com.aitec.sitesport.menu.MenusPresenter
import com.aitec.sitesport.menu.adapter.OptionsAdapter
import com.aitec.sitesport.menu.adapter.onOptionsAdapterListener
import com.aitec.sitesport.util.OptionMenu
import kotlinx.android.synthetic.main.fragment_menu.view.*
import javax.inject.Inject

class MenuFragment : Fragment(), MenusView, onOptionsAdapterListener, View.OnClickListener {
    private val TAG = "MenuFragment"
    private var adapterOptions: OptionsAdapter? = null
    private var data: ArrayList<OptionMenu>? = ArrayList()
    lateinit var progressDialog: ProgressDialog
    private var user: User? = null

    @Inject
    lateinit var presenter: MenusPresenter
    lateinit var myApplication: MyApplication


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data!!.add(OptionMenu(R.drawable.ic_termins_conditions, getString(R.string.menu_option_termins_and_conditions)))
        data!!.add(OptionMenu(R.drawable.ic_help, getString(R.string.menu_option_help)))
        data!!.add(OptionMenu(R.drawable.ic_exit, getString(R.string.menu_option_signout)))
        adapterOptions = OptionsAdapter(data!!, this)
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
        presenter.getMyProfile()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(R.layout.fragment_menu, container, false)
        view.rv_menu_options.layoutManager = LinearLayoutManager(context)
        view.rv_menu_options.adapter = adapterOptions
        view.cl_my_profile.setOnClickListener(this)
        setupInjection()

        return view
    }

    private fun setupInjection() {
        myApplication = activity!!.getApplication() as MyApplication
        myApplication.getMenusComponent(this).inject(this)
    }

    override fun showProgressDialog(message: Int) {
        progressDialog = ProgressDialog(context)
        progressDialog.setMessage(getString(message));
        progressDialog.setCancelable(false)
        progressDialog.show()
    }

    override fun hideProgressDialog() {
        progressDialog.hide()
    }

    override fun showMessagge(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    override fun navigationToProfile() {
        //startActivity(Intent(context, ProfileActivity::class.java))
    }

    override fun navigationToTermsAndConditions() {

    }

    override fun navigationToLogin() {

    }

    override fun onClick(position: Int) {
        when (position) {
        /*
        0 -> {
            showMessagge("Cambiar Contrasena")
            val changePasswordFragment = ChangePasswordFragment.newInstance()
            changePasswordFragment.show(childFragmentManager, "Cambiar Contrasena")
        }
        */
            0 -> {
                showMessagge("Terminos y Condiciones")
            }
            1 -> {
                showMessagge("Ayuda")
            }
            2 -> {
                showMessagge("Cerrar session")
                //presenter.onSingOut()
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.cl_my_profile -> {
                navigationToProfile()
            }
        }
    }

    fun onUpdatePassword(password: String, passwordOld: String) {
        presenter.onUpdatePassword(password, passwordOld)
    }

    override fun setDataProfile(user: User) {
        /*
        this.user = user
        tv_name_user.text = user.names
        tv_email.text = user.email
        GlideApp.with(context!!)
                .load(user.photo)
                .placeholder(R.drawable.ic_person_black_24dp)
                .centerCrop()
                .error(R.drawable.ic_person_black_24dp)
                .into(civ_user)
                */
    }

    companion object {
        fun newInstance(): MenuFragment {
            val fragment = MenuFragment()
            return fragment
        }
    }
}
