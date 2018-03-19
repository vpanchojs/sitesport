package com.aitec.sitesport.domain

import com.aitec.sitesport.domain.RetrofitApi.Companion.PATH_PROFILE
import com.aitec.sitesport.domain.RetrofitApi.Companion.PATH_SEARCH_CENTER
import com.aitec.sitesport.entities.Entreprise
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by victor on 15/3/18.
 */
interface RetrofitServicie {

    @Headers("Content-Type: application/json")
    @POST(PATH_SEARCH_CENTER)
    fun getCenterSportVisible(@Body params: HashMap<String, String>): Call<List<Entreprise>>

    @Headers("Content-Type: application/json")
    @GET(PATH_PROFILE + "{pk}")
    fun getProfile(@Path("pk") pk : String): Call<JsonObject>

}