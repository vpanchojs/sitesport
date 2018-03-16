package com.aitec.sitesport.domain

import com.aitec.sitesport.domain.RetrofitApi.Companion.PATH_SEARCH_CENTER
import com.aitec.sitesport.entities.Entrepise
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Created by victor on 15/3/18.
 */
interface RetrofitServicie {

    @Headers("Content-Type: application/json")
    @POST(PATH_SEARCH_CENTER)
    fun getCenterSportVisible(@Body params: HashMap<String, String>): Call<List<Entrepise>>
}