package com.aitec.sitesport.menu.ui

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
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
import com.aitec.sitesport.util.BaseActivitys
import com.aitec.sitesport.util.OptionMenu
import com.aitec.sitesport.work.Workme
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.android.synthetic.main.fragment_menu.view.*
import java.util.*
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

    ////jhony//7
    val SIGN_IN_CODE = 888
    val face = 1
    private var callbackManager: CallbackManager? = null

    private var mGoogleSignInClient: GoogleSignInClient? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupMenuOptions();
    }

    private fun setupMenuOptions() {
        data!!.add(OptionMenu(R.drawable.ic_termins_conditions, getString(R.string.menu_option_termins_and_conditions)))
        data!!.add(OptionMenu(R.drawable.ic_help, getString(R.string.menu_option_help)))
        data!!.add(OptionMenu(R.drawable.ic_call_black_24dp, getString(R.string.menu_option_contact)))
        data!!.add(OptionMenu(R.drawable.ic_exit, getString(R.string.menu_option_signout)))
        adapterOptions = OptionsAdapter(data!!, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager!!.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SIGN_IN_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private fun handleSignInResult(result: Task<GoogleSignInAccount>) {
        try {
            val account = result.getResult(ApiException::class.java)
            Log.e(TAG, "signInResult:succes idtoken= ${account.idToken}")
            showMessagge("Session Correctamente")
            presenter.tokenGoogle(account.idToken!!)
        } catch (e: ApiException) {
            showMessagge("Error al iniciar session")
            Log.e(TAG, "signInResult:failed code=" + e.toString());
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
        presenter.inSession()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_menu, container, false)
        view.rv_menu_options.layoutManager = LinearLayoutManager(context)
        view.rv_menu_options.adapter = adapterOptions
        view.cl_my_profile.setOnClickListener(this)
        setupInjection()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSingInFacebook()
        setupSingInGoogle()
        btn_sigin_google.setOnClickListener(this)
        btn_sigin_facebook.setOnClickListener(this)

    }

    fun setupSingInFacebook() {
        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                presenter.tokenFacebook(loginResult.accessToken.token)
                showMessagge("Session Correctamente")
            }

            override fun onCancel() {
                Toast.makeText(context, R.string.cancel_login, Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: FacebookException) {
                Toast.makeText(context, R.string.error_login, Toast.LENGTH_SHORT).show()
            }
        })

    }

    fun setupSingInGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("138055446583-vk1h8k95h1ksqqs5akl9eaa5rturnr44.apps.googleusercontent.com")
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(activity!!, gso)
    }

    override fun mostrarmenu() {
        //menu.visibility = View.VISIBLE
        cl_login.visibility = View.GONE
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

    override fun showMessagge(message: Any) {
        BaseActivitys.showToastMessage(context!!, message, Toast.LENGTH_SHORT)
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
            0 -> {
                showMessagge("Terminos y Condiciones")
            }
            1 -> {
                showMessagge("Ayuda")
            }
            2 -> {
                val intento1 = Intent(context, Workme::class.java)
                startActivity(intento1)
                //showMessagge("Contactenos")
            }
            3 -> {
                /*
                Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback { status ->
                    if (status.isSuccess) {
                        menu.visibility = View.GONE
                        ver_loguin.visibility = View.VISIBLE
                    } else {
                        LoginManager.getInstance().logOut()
                        Toast.makeText(context, R.string.not_revoke, Toast.LENGTH_SHORT).show()
                    }
                }
                //showMessagge("Cerrar session")
                //presenter.onSingOut()
                */
            }


        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.cl_my_profile -> {
                navigationToProfile()
            }
            R.id.btn_sigin_google -> {
                val intent = mGoogleSignInClient!!.signInIntent
                startActivityForResult(intent, SIGN_IN_CODE)
            }
            R.id.btn_sigin_facebook -> {
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"))
            }
        }
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


    override fun visibleMenuOptions(visible: Int) {
        rv_menu_options.visibility = visible
    }

    override fun visibleLogin(visible: Int) {
        cl_login.visibility = visible
    }
}
