package com.aitec.sitesport.domain

import android.os.Handler
import android.util.Log
import com.aitec.sitesport.domain.listeners.onVolleyApiActionListener
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.aitec.sitesport.MyApplication
import org.json.JSONObject

class VolleyApi(var app: MyApplication) {
    val TAG = "VolleyApi"
    var request: RequestQueue = Volley.newRequestQueue(app.applicationContext)

    private val LAG = 500
    private val delay = 500
    private val TAG_NICKNAME = "nickname"
    private val TAG_SEARCHUE = "searchUserorEnterprise"

    private var handlerNicknameSignup: Handler? = null
    private var runnableNicknameSignup: Runnable? = null
    private var objectRequestNickname: JsonObjectRequest? = null

    private var handlerResquestSearchUserorEntrepise: Handler? = null
    private var runnableResquestSearchUserorEntrepise: Runnable? = null
    private var objectResquestSearchUserorEntrepise: JsonObjectRequest? = null

    //    val PATH_API = "http://192.168.1.37:8050/"

    val PATH_API = "http://54.200.239.140:8050/"
    val PATH_LOGIN = "api-dsafio-auth-token/login?format=json"
    val PATH_SIGNUP = "usuario/nuevo/"
    val PATH_VALIDATE_NICKNAME = "usuario/username/esunico/"
    var PATH_RECOVERY_PASSWORD = "usuario/validar/correo/"
    var PATH_PERSONAS = "api/personas/?"

    fun onSingIn(email: String, password: String, callback: onVolleyApiActionListener) {
        var parametros = HashMap<String, String>()
        parametros.put("email", email)
        parametros.put("password", password)
        Log.e(TAG_NICKNAME, parametros.toString())

        val ObjectRequest = object : JsonObjectRequest(
                Request.Method.POST,
                PATH_API + PATH_LOGIN,
                JSONObject(parametros),
                object : Response.Listener<JSONObject> {
                    override fun onResponse(response: JSONObject) {
                        callback.onSucces(response)
                    }
                },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError) {
                        callback.onError(error)

                    }
                }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Content-Type", "application/json")
                return headers
            }

        }

        request.add(ObjectRequest)

    }


    fun signup(jsonObject: JSONObject, callback: onVolleyApiActionListener) {
        Log.e("json", jsonObject.toString())
        val objectRequest = object : JsonObjectRequest(
                Request.Method.POST,
                PATH_API + PATH_SIGNUP,
                jsonObject,
                Response.Listener<JSONObject> { response ->
                    callback.onSucces(response)
                },
                Response.ErrorListener { error ->
                    callback.onError(error)
                }) {

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Content-Type", "application/json")
                return headers
            }

        }
        objectRequest.setShouldCache(false)
        request.add(objectRequest)
    }


    fun validateNickname(jsonObject: JSONObject, callback: onVolleyApiActionListener) {
        Log.e("json", jsonObject.toString())
        objectRequestNickname = object : JsonObjectRequest(
                Request.Method.POST,
                PATH_API + PATH_VALIDATE_NICKNAME,
                jsonObject,
                Response.Listener<JSONObject> { response ->
                    callback.onSucces(response)
                },
                Response.ErrorListener { error ->
                    callback.onError(error)
                }) {

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Content-Type", "application/json")
                return headers
            }

        }

        objectRequestNickname?.setShouldCache(false)
        objectRequestNickname?.tag = TAG_NICKNAME
        handlerNicknameSignup = Handler()
        runnableNicknameSignup = Runnable {
            request.add(objectRequestNickname)
        }

        handlerNicknameSignup?.postDelayed(runnableNicknameSignup, LAG.toLong())

    }

    fun removeAllRequestNickname() {
        removeHandlerNickname()
        removeRequestNickname()
    }

    private fun removeHandlerNickname() {
        if (handlerNicknameSignup != null) {
            handlerNicknameSignup?.removeCallbacks(runnableNicknameSignup)
            handlerNicknameSignup = null
        }
    }

    private fun removeRequestNickname() {
        request.cancelAll(TAG_NICKNAME)
        if (objectRequestNickname != null) {
            objectRequestNickname!!.cancel()
            objectRequestNickname = null
        }
    }


    fun recoveryPassword(email: String, callback: onVolleyApiActionListener) {
        var parametros = HashMap<String, String>()
        parametros.put("email", email)
        Log.e(TAG_NICKNAME, parametros.toString())
        val ObjectRequest = object : JsonObjectRequest(
                Request.Method.POST,
                PATH_API + PATH_RECOVERY_PASSWORD,
                JSONObject(parametros),
                object : Response.Listener<JSONObject> {
                    override fun onResponse(response: JSONObject) {
                        callback.onSucces(response)
                    }
                },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError) {
                        callback.onError(error)

                    }
                }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Content-Type", "application/json")
                return headers
            }

        }

        request.add(ObjectRequest)
        // callback.onSucces(R.string.enlance_send_recovery_password)
    }

    fun onSearchUserorEntrepise(query: String, token: String, callback: onVolleyApiActionListener) {
        Log.e(TAG, token)
        objectResquestSearchUserorEntrepise = object : JsonObjectRequest(
                Request.Method.GET,
                PATH_API + PATH_PERSONAS + "search=" + query + "&page=1",
                null,
                object : Response.Listener<JSONObject> {
                    override fun onResponse(response: JSONObject) {
                        callback.onSucces(response)
                    }
                },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError) {
                        callback.onError(error)

                    }
                }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Content-Type", "application/json")
                headers.put("Authorization", "Bearer " + token)
                return headers
            }

        }

        objectResquestSearchUserorEntrepise?.setShouldCache(false)
        objectResquestSearchUserorEntrepise?.tag = TAG_SEARCHUE
        handlerResquestSearchUserorEntrepise = Handler()
        runnableResquestSearchUserorEntrepise = Runnable {
            request.add(objectResquestSearchUserorEntrepise)
        }

        handlerResquestSearchUserorEntrepise?.postDelayed(runnableResquestSearchUserorEntrepise, delay.toLong())
    }


    fun removeAllRequestSearchUserorEntrepise() {
        removeHandlerSearchUserorEntrepise()
        removeRequestSearchUserorEntrepise()
    }

    private fun removeHandlerSearchUserorEntrepise() {
        if (handlerResquestSearchUserorEntrepise != null) {
            handlerResquestSearchUserorEntrepise?.removeCallbacks(runnableResquestSearchUserorEntrepise)
            handlerResquestSearchUserorEntrepise = null
        }
    }

    private fun removeRequestSearchUserorEntrepise() {
        request.cancelAll(TAG_SEARCHUE)
        if (objectResquestSearchUserorEntrepise != null) {
            objectResquestSearchUserorEntrepise!!.cancel()
            objectResquestSearchUserorEntrepise = null
        }
    }

}