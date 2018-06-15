package com.aitec.sitesport.profileUser.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Parcelable
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
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
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ProfileUserActivity : AppCompatActivity(), ProfileUserView, View.OnClickListener {


    companion object {
        const val USER = "user"
    }

    lateinit var user: User
    private val MY_PERMISSIONS_REQUEST_CODE = 1
    private val REQUEST_GET_IMAGE = 100
    private var photo: String? = ""

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
        // setDataProfile(intent.extras.getParcelable(USER))
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
        fab_get_photo.setOnClickListener(this)
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
                        tie_lastnames.text.toString(),
                        tie_dni.text.toString(),
                        tie_phone.text.toString(),
                        user.photo.toString()
                )
            }
            R.id.fab_get_photo -> {
                if (checkPermissions()) {
                    getPhoto()
                }
            }
        }
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA), MY_PERMISSIONS_REQUEST_CODE)
            return false
        } else {
            return true
        }
    }

    fun getPhoto() {
        var chooserIntent: Intent? = null
        var intentList: MutableList<Intent> = ArrayList()
        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra("return-data", true)

        val photoFile = getFile()
        if (photoFile != null) {
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile))
            if (cameraIntent.resolveActivity(packageManager) != null) {
                intentList = addIntentsToList(intentList, cameraIntent) as MutableList<Intent>
            }
        }

        if (pickIntent.resolveActivity(packageManager) != null) {
            intentList = addIntentsToList(intentList, pickIntent) as MutableList<Intent>
        }

        if (intentList.size > 0) {
            chooserIntent = Intent.createChooser(intentList.removeAt(intentList.size - 1),
                    getString(R.string.main_message_picture_source))
            chooserIntent!!.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toTypedArray<Parcelable>())
        }

        if (chooserIntent != null) {
            startActivityForResult(chooserIntent, REQUEST_GET_IMAGE)
        }
    }

    private fun getFile(): File? {
        var photoFile: File? = null
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timestamp + "_"
        val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        try {
            photoFile = File.createTempFile(imageFileName, ".jpg", storageDir)
            photo = photoFile!!.absolutePath
            Log.e("FOTO", "obtener foto")
        } catch (e: IOException) {
            //showSnackbar(R.string.main_error_dispatch_camera);
            Log.e("crear archivo", e.message.toString())
        }

        return photoFile
    }

    private fun addIntentsToList(list: MutableList<Intent>, intent: Intent): List<Intent> {
        val resInfo = getPackageManager().queryIntentActivities(intent, 0)
        for (resolveInfo in resInfo) {
            val packageName = resolveInfo.activityInfo.packageName
            val targetIntent = Intent(intent)
            targetIntent.`package` = packageName
            list.add(targetIntent)
        }

        return list
    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_GET_IMAGE) {
            if (resultCode == RESULT_OK) {
                val fromCamera = data == null || data.data == null
                if (fromCamera) {
                    // addToGallery()
                } else {
                    photo = getRealPathFromURI(data!!.data)
                }
                civ_user.setImageURI(Uri.parse(photo))
                presenter.updatePhotoUser(photo!!)
            }
        }
    }

    private fun getRealPathFromURI(contentURI: Uri?): String? {
        var result: String? = null

        val cursor = getContentResolver().query(contentURI!!, null, null, null, null)
        if (cursor == null) {
            result = contentURI.path
        } else {
            if (contentURI.toString().contains("mediaKey")) {
                cursor.close()

                try {
                    val file = File.createTempFile("tempImg", ".jpg", getCacheDir())
                    val input = getContentResolver().openInputStream(contentURI)
                    val output = FileOutputStream(file)

                    try {
                        val buffer = ByteArray(4 * 1024)
                        var read = 0

                        while ((input!!.read(buffer)) != -1) {
                            output.write(buffer, 0, read)
                        }
                        output.flush()
                        result = file.absolutePath
                    } finally {
                        output.close()
                        input!!.close()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            } else {
                cursor.moveToFirst()
                val dataColumn = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                result = cursor.getString(dataColumn)
                cursor.close()
            }
        }
        return result
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_CODE -> if (grantResults.size > 0) {
                when (permissions[0]) {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE ->
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                        else
                            Toast.makeText(this, "Permisos necesarios para funcionamiento mostrar el mapa ", Toast.LENGTH_LONG).show()
                }
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

    override fun setInfoUser(user: User) {
        this.user = user
        tie_names.setText(user.names)
        tie_lastnames.setText(user.lastName)
        tie_email.setText(user.email)
        tie_dni.setText(user.dni)
        tie_phone.setText(user.phone)
        GlideApp.with(this)
                .load(user.photo)
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .error(R.mipmap.ic_launcher)
                .into(civ_user)

    }

    override fun showButtonReload(visibility: Int) {
        btn_reload.visibility = visibility
    }

    override fun showProgressAndMessagin(visibility: Int) {
        progressbar_get_info_user.visibility = visibility
        tv_info.visibility = visibility
    }

    override fun setPhoto(url: String) {
        GlideApp.with(this)
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .error(R.mipmap.ic_launcher)
                .into(civ_user)
    }
}
