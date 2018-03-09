package com.aitec.sitesport.domain.util

import com.android.volley.*
import org.json.JSONObject

/**
 * Created by victor on 24/1/18.
 */
class MapperError {

    companion object {
        fun getErrorResponse(error: Any): String {
            val volleyError = error as VolleyError
            var message: String = ""
            if (volleyError is NetworkError) {
                message = "No se puede conectar a internet. Por favor, verifica tu conexión"
            } else if (volleyError is ServerError) {
                var code = volleyError.networkResponse.statusCode
                if (code == 400){
                    val json = JSONObject(String(volleyError.networkResponse.data))
                    message = json.get("error").toString()
                }else
                    message = "Debug error server " + code
                //message = "Tenemos problemas con nuestros servicios. Por favor, inténtelo más tarde"

            } else if (volleyError is AuthFailureError) {
                message = "No se puede conectar a internet. Por favor, verifica tu conexión"
            } /*else if (error is ParseError) {
                    message = "Parsing error! Please try again after some time!!"
                } */ else if (volleyError is NoConnectionError) {
                message = "No se puede conectar a internet. Por favor, verifica tu conexión"
            } else if (volleyError is TimeoutError) {
                message = "El tiempo de conexión expiró. Por favor, verifica tu conexión"
            } else {
                message = volleyError.message.toString()
            }
            return message
        }
    }
}