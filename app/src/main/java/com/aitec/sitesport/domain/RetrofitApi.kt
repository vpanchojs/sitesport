package com.aitec.sitesport.domain

import android.util.Log
import com.aitec.sitesport.domain.listeners.onApiActionListener
import com.aitec.sitesport.entities.Entreprise
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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

    fun getCenterSport(latSouth: Double, latNorth: Double, lonWest: Double, lonEast: Double, latMe: Double, lngMe: Double, callback: onApiActionListener) {
        var parametros = HashMap<String, String>()
        parametros.put("latitud", latMe.toString())
        parametros.put("longitud", lngMe.toString())
        parametros.put("norte_lat", latNorth.toString())
        parametros.put("sur_lat", latSouth.toString())
        parametros.put("este_lon", lonEast.toString())
        parametros.put("oeste_lon", lonWest.toString())


        request.getCenterSportVisible(parametros).enqueue(object : Callback<List<Entreprise>> {
            override fun onResponse(call: Call<List<Entreprise>>, response: Response<List<Entreprise>>) {

                callback.onSucces(response.body())

            }

            override fun onFailure(call: Call<List<Entreprise>>, t: Throwable) {
                Log.e("error", t.message.toString())
            }
        })
    }

    fun onSearchNameCenterSport(query: String, callback: onApiActionListener) {

    }
}