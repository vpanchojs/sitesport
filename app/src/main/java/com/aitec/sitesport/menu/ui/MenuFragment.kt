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
import com.aitec.sitesport.util.OptionMenu
import com.aitec.sitesport.work.Workme
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.android.synthetic.main.fragment_menu.view.*
import javax.inject.Inject

class MenuFragment :  Fragment(), MenusView, onOptionsAdapterListener, View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
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
    internal var accessToken: AccessToken? = null

    //Google//
    private var googleApiClient: GoogleApiClient? = null
    private val mGoogleSignInClient: GoogleSignInClient? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data!!.add(OptionMenu(R.drawable.ic_termins_conditions, getString(R.string.menu_option_termins_and_conditions)))
        data!!.add(OptionMenu(R.drawable.ic_help, getString(R.string.menu_option_help)))
        data!!.add(OptionMenu(R.drawable.ic_call_black_24dp,getString(R.string.menu_option_contact)))
        data!!.add(OptionMenu(R.drawable.ic_exit, getString(R.string.menu_option_signout)))
        adapterOptions = OptionsAdapter(data!!, this)


        /////jhony///////


    }
//////////jhony//////777
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    super.onActivityResult(requestCode, resultCode, data)

        callbackManager!!.onActivityResult(requestCode, resultCode, data)

        ///////Google///////77
        if (requestCode == SIGN_IN_CODE) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            handleSignInResult(result)
        }

}

    private fun handleSignInResult(result: GoogleSignInResult) {
        if (result.isSuccess) {
            val account = result.signInAccount
            mostrarmenu()
            Log.e("token de google", account!!.idToken)
            presenter.tokengoogle(account.idToken.toString())
            /*tv_name_user.setText(account.displayName)
            tv_email.setText(account.email)*/




        } else {
            Toast.makeText(context, R.string.not_log_in, Toast.LENGTH_SHORT).show()
            updateUI(false)

        }
    }
    private fun updateUI(signedIn: Boolean) {
        if (signedIn) {



        } else {

        }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {

    }
    ///////jhony hasta aqui////77


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ///////////////facebook//////7777

            callbackManager = CallbackManager.Factory.create()
            loginButton!!.setReadPermissions("email")
            loginButton.fragment = this
            loginButton!!.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {

                    ///////////////////////////////goMainScreen(MainActivity.face)
                    Log.e("token de facebook", accessToken.toString())
                    Log.e("inicio de sesion face", loginResult.toString())

                    presenter.tokenfacebook(loginResult.accessToken.token)
                }

                override fun onCancel() {
                    Toast.makeText(context, R.string.cancel_login, Toast.LENGTH_SHORT).show()
                }

                override fun onError(error: FacebookException) {
                    Toast.makeText(context, R.string.error_login, Toast.LENGTH_SHORT).show()
                }
            })


        //tv_email.setText(object.toStrgetString("email"))

        //////////////77//Google///////////////////77

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.id_google2))
                .build()

        googleApiClient = GoogleApiClient.Builder(context!!)
                .enableAutoManage(activity!!, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()

        //signInButton = findViewById<View>(R.id.signInButton) as SignInButton

        signInButton!!.setSize(SignInButton.SIZE_WIDE)
        signInButton.setColorScheme(SignInButton.COLOR_AUTO)
        signInButton!!.setOnClickListener {
            val intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
            startActivityForResult(intent, SIGN_IN_CODE)
        }
    }

    private fun enviar_token(token:String){

        presenter.tokenfacebook(accessToken!!.token)

    }

    override fun mostrarmenu() {

        menu.visibility=View.VISIBLE
        ver_loguin.visibility=View.GONE
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
                val intento1 = Intent(context, Workme::class.java)
                startActivity(intento1)
                //showMessagge("Contactenos")
            }
            3 ->{
                Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback { status ->
                    if (status.isSuccess) {
                        menu.visibility=View.GONE
                        ver_loguin.visibility=View.VISIBLE
                    } else {
                        LoginManager.getInstance().logOut()
                        Toast.makeText(context, R.string.not_revoke, Toast.LENGTH_SHORT).show()
                    }
                }
                //showMessagge("Cerrar session")
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

    override fun onDestroy() {
        super.onDestroy()
        googleApiClient!!.stopAutoManage(activity!!)
        googleApiClient!!.disconnect()

    }


}
