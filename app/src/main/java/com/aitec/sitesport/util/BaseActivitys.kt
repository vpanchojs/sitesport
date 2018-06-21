package com.aitec.sitesport.util

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.support.design.widget.TextInputEditText
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import com.aitec.sitesport.domain.listeners.onApiActionListener
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks

/**
 * Created by victor on 25/1/18.
 */
class BaseActivitys() {

    /*Metodo para validar el correo_electronico*/
    companion object {
        var progressDialog: ProgressDialog? = null

        const val LINK_ENTERPRISE = 0
        const val LINK_PUBLICATION = 1

        fun validateFieldEmail(context: Context, field: TextInputEditText): Boolean {
            if (Patterns.EMAIL_ADDRESS.matcher(field.text.toString()).matches()) {
                field.setError(null)
                return true;
            } else {
                //field.setError(context.resources.getString(R.string.login_edittext_error_invalid_password))
                return false
            }
        }

        fun onTextChangedListener(fields: ArrayList<TextInputEditText>, button: Button) {
            fields.forEach { f ->
                f.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        button.isEnabled = validateBlank(fields)
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                })
            }
        }

        fun validateBlank(fields: ArrayList<TextInputEditText>): Boolean {
            var enable = true
            fields.forEach { f ->
                if (f.text.toString().isBlank())
                    enable = false
            }
            return enable
        }

        fun showToastMessage(context: Context, message: Any, duration: Int) {
            Toast.makeText(context, getStringMessage(context, message), duration).show()
        }

        fun getStringMessage(context: Context, message: Any): String {
            if (message is Int) {
                return context.getString(message)
            } else {
                return message as String
            }
        }

        fun showProgressDialog(context: Context, message: Any) {
            progressDialog = ProgressDialog(context)
            progressDialog!!.setMessage(getStringMessage(context, message));
            progressDialog!!.setCancelable(false)
            progressDialog!!.show()
        }

        fun hideProgressDialog() {
            progressDialog!!.dismiss()
            progressDialog = null
        }


        fun validaDni(dni: String): Boolean {
            if (dni.length == 10) {
                val ultimo = dni.substring(9, 10).toInt()
                val aux = dni.substring(0, 9)
                val verificador = arrayOf(2, 1, 2, 1, 2, 1, 2, 1, 2)
                var sum = 0
                val region = dni.substring(0, 2).toInt()

                if (region in 1..24) {
                    for (i in 0..aux.length - 1) {
                        val valor = aux[i].toString().toInt() * verificador[i]
                        sum += if (valor >= 10) valor - 9 else valor
                    }

                    val result = ((10 - sum.rem(10)) + sum) - sum
                    if (result == ultimo) return true else return result == 10 && ultimo == 0

                } else {
                    return false
                }
            } else {
                return false
            }
        }


        fun buildDinamycLinkShareApp(pk: String?, type: Int?, callbacks: onApiActionListener<String>) {
            var link = "https://sitesport.aitecec.com"
            if (pk != null && type != null) {
                link = "https://sitesport.aitecec.com?id=$pk&type=$type"
            }

            FirebaseDynamicLinks.getInstance().createDynamicLink()
                    .setLink(Uri.parse(link))
                    .setDynamicLinkDomain("sitesport.page.link")
                    // Open links with this app on Android
                    .setAndroidParameters(DynamicLink.AndroidParameters.Builder().build())
                    .buildShortDynamicLink()
                    .addOnSuccessListener {
                        callbacks.onSucces(it.shortLink.toString())
                        /*
                        Log.e(TAG, "el link es: ${it.shortLink}")
                        val i = Intent(Intent.ACTION_SEND)
                        i.type = "text/plain"
                        i.putExtra(Intent.EXTRA_TEXT, "descarga " + it.shortLink.toString())
                        startActivity(Intent.createChooser(i, "Compartir mediante..."))
                        */
                    }
                    .addOnFailureListener {
                        callbacks.onError(it.toString())
                    }

        }


    }
}