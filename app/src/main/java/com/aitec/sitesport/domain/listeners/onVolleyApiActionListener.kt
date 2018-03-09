package com.aitec.sitesport.domain.listeners

import com.android.volley.VolleyError
import org.json.JSONObject

/**
 * Created by victor on 15/1/18.
 */
interface onVolleyApiActionListener {
    fun onSucces(response: Any?)
    fun onError(error: Any?)
}