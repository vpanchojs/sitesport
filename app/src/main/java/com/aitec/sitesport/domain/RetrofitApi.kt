package com.aitec.sitesport.domain

import android.util.Log
import com.aitec.sitesport.domain.listeners.onVolleyApiActionListener
import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by victor on 14/3/18.
 */
class RetrofitApi {

    companion object {
        val PATH_API = "http://54.200.239.140:8050/"
        const val PATH_SEARCH_CENTER = "api/search-centros/"
    }

    val retrofit = Retrofit.Builder()
            .baseUrl(PATH_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val request = retrofit.create(RetrofitServicie::class.java)

    fun getCenterSport(latSouth: Double, latNorth: Double, lonWest: Double, lonEast: Double, latMe: Double, lngMe: Double, callback: onVolleyApiActionListener) {
        var parametros = HashMap<String, String>()
        parametros.put("latitud", latMe.toString())
        parametros.put("longitud", lngMe.toString())
        parametros.put("norte_lat", latNorth.toString())
        parametros.put("sur_lat", latSouth.toString())
        parametros.put("este_lon", lonEast.toString())
        parametros.put("oeste_lon", lonWest.toString())


        request.getCenterSportVisible(parametros).enqueue(object : Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                Log.e("correct", "yes" + response.body().toString())

            }

            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                Log.e("error", t.message.toString())
            }
        })
    }
}