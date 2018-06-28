package com.aitec.sitesport.menu.ui

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
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
import com.aitec.sitesport.domain.listeners.onApiActionListener
import com.aitec.sitesport.entities.Cuenta
import com.aitec.sitesport.entities.User
import com.aitec.sitesport.menu.MenusPresenter
import com.aitec.sitesport.menu.adapter.OptionsAdapter
import com.aitec.sitesport.menu.adapter.onOptionsAdapterListener
import com.aitec.sitesport.profileUser.ui.ProfileUserActivity
import com.aitec.sitesport.util.BaseActivitys
import com.aitec.sitesport.util.GlideApp
import com.aitec.sitesport.util.OptionMenu
import com.aitec.sitesport.work.Workme
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.Profile
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
        data!!.add(OptionMenu(R.drawable.ic_share, getString(R.string.menu_option_share_app)))
        data!!.add(OptionMenu(R.drawable.ic_termins_conditions, getString(R.string.menu_option_termins_and_conditions)))
        data!!.add(OptionMenu(R.drawable.ic_help, getString(R.string.menu_option_help)))
        data!!.add(OptionMenu(R.drawable.ic_email_black_24dp, getString(R.string.menu_option_contact)))
        data!!.add(OptionMenu(R.drawable.ic_exit, getString(R.string.menu_option_signout)))


        adapterOptions = OptionsAdapter(data!!, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager!!.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SIGN_IN_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(result: Task<GoogleSignInAccount>) {
        try {
            val account = result.getResult(ApiException::class.java)
            Log.e(TAG, "signInResult:succes idtoken= ${account.givenName}")
            // showMessage("Session Correctamente")
            presenter.tokenGoogle(account.idToken!!, account.givenName, account.familyName, account.email, account.photoUrl)
        } catch (e: Exception) {
            showMessagge("Error al autenticarse en google")
            Log.e(TAG, "signInResult:failed code=" + e.toString());
        }
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()
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
        presenter.onResume()
        presenter.subscribeAuth()
    }

    fun setupSingInFacebook() {
        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {

                Log.e(TAG, "TOKEN FA ${loginResult.recentlyGrantedPermissions}")

                val profile = Profile.getCurrentProfile()

                if (profile.firstName != null && profile.lastName != null) {
                    presenter.tokenFacebook(loginResult.accessToken.token, profile.firstName, profile.lastName, "", profile.getProfilePictureUri(200, 200))
                } else {
                    showMessagge("Tenemos problemas al obtener su información de Facebook, intentelo nuevamente")
                }
            }

            override fun onCancel() {
                //Toast.makeText(context, R.string.cancel_login, Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: FacebookException) {
                //Toast.makeText(context, R.string.error_login, Toast.LENGTH_SHORT).show()
                showMessagge("Error al autenticarse con facebook")
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

    override fun showMessagge(message: Any?) {
        BaseActivitys.showToastMessage(context!!, message!!, Toast.LENGTH_LONG)
    }

    override fun navigationToProfile() {
        startActivity(Intent(context, ProfileUserActivity::class.java).putExtra(ProfileUserActivity.USER, user))
    }

    override fun navigationToTermsAndConditions() {

    }

    override fun navigationToLogin() {

    }

    override fun onClick(position: Int) {
        when (position) {
            0 -> {
                showMessagge("Obteniendo aplicaciones")
                BaseActivitys.buildDinamycLinkShareApp(null, null, object : onApiActionListener<String> {
                    override fun onSucces(response: String) {
                        val i = Intent(android.content.Intent.ACTION_SEND)
                        i.type = "text/plain"
                        i.putExtra(Intent.EXTRA_TEXT, "${getString(R.string.textShareApp)} $response")
                        startActivity(Intent.createChooser(i, "Compartir mediante..."))
                    }

                    override fun onError(error: Any?) {
                        Log.e(TAG, "Error dynamic link $error")
                    }
                })
            }

            1 -> {
                //showMessage("Terminos y Condiciones")
                openBrowser("https://www.google.com")
            }
            2 -> {
                //showMessage("Ayuda")
                openBrowser("https://www.google.com")
            }
            3 -> {
                val intento1 = Intent(context, Workme::class.java)
                startActivity(intento1)
                //showMessage("Contactenos")
            }
            4 -> {
                presenter.onSingOut()
            }


        }
    }

    fun openBrowser(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.cl_my_profile -> {
                navigationToProfile()
            }
            R.id.btn_sigin_google -> {

                val intent = mGoogleSignInClient!!.signInIntent
                startActivityForResult(intent, SIGN_IN_CODE)
                //showMessage("Estamos Trabajando en ello")
            }
            R.id.btn_sigin_facebook -> {
                //showMessage("Estamos Trabajando en ello")

                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"))

            }
        }
    }

    override fun setDataProfile(user: User) {
        this.user = user
        tv_name_user.text = "${user.nombre} ${user.apellido}"
        tv_email.text = user.correo_electronico
        GlideApp.with(context!!)
                .load(user.foto)
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .error(R.mipmap.ic_launcher)
                .into(civ_user)
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

    override fun singOut(platform: Int) {
        Log.e(TAG, "la plataforma ha cerrar session $platform")
        when (platform) {
            Cuenta.FACEBOOK -> {
                LoginManager.getInstance().logOut()
            }
            Cuenta.GOOGLE -> {
                mGoogleSignInClient!!.signOut()
            }
        }
    }

    override fun showProgress(visible: Int) {
        progressbar.visibility = visible
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onPause()
        presenter.unSubscribeAuth()
    }
}
