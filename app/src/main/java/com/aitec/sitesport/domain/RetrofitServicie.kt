package com.aitec.sitesport.domain

import com.aitec.sitesport.domain.RetrofitApi.Companion.PATH_PROFILE
import com.aitec.sitesport.domain.RetrofitApi.Companion.PATH_SEARCH_CENTER
import com.aitec.sitesport.domain.RetrofitApi.Companion.PATH_SEARCH_NAME_CENTER_SPORT
import com.aitec.sitesport.entities.enterprise.Enterprise
import com.aitec.sitesport.entities.SearchCentersName
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface RetrofitServicie {

    @Headers("Content-Type: application/json")
    @POST(PATH_SEARCH_CENTER)
    fun getCenterSportVisible(@Body params: HashMap<String, String>): Call<List<Enterprise>>

    @Headers("Content-Type: application/json")
    @GET(PATH_SEARCH_NAME_CENTER_SPORT)
    fun searchNameCenterSport(@Query("nombres") nombres: String, @Query("page") page: Int): Call<SearchCentersName>

    @Headers("Content-Type: application/json")
    @GET(PATH_PROFILE + "{pk}")
    fun getProfile(@Path("pk") pk : String): Call<JsonObject>
}