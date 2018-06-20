package com.aitec.sitesport.domain

import com.aitec.sitesport.domain.RetrofitApi.Companion.PATH_CENTER_SPORT
import com.aitec.sitesport.domain.RetrofitApi.Companion.PATH_SEARCH_CENTER
import com.aitec.sitesport.domain.RetrofitApi.Companion.PATH_SEARCH_NAME_CENTER_SPORT
import com.aitec.sitesport.entities.Cuenta
import com.aitec.sitesport.entities.SearchCentersName
import com.aitec.sitesport.entities.enterprise.Enterprise
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*


interface RetrofitServicie {

    @Headers("Content-Type: application/json")
    @POST(PATH_SEARCH_CENTER)
    fun getCenterSportVisible(@Body params: HashMap<String, String>): Call<List<Enterprise>>

    @Headers("Content-Type: application/json")
    @GET(PATH_SEARCH_NAME_CENTER_SPORT)
    fun searchNameCenterSport(@Query("nombre") nombres: String, @Query("page") page: Int): Call<SearchCentersName>

    @Headers("Content-Type: application/json")
    @GET()
    fun getProfile(@Url urlDetail: String): Call<Enterprise>

    @Headers("Content-Type: application/json")
    @GET(PATH_CENTER_SPORT)
    fun getSites(): Call<List<Enterprise>>

    @Headers("Content-Type: application/json")
    @GET(PATH_CENTER_SPORT)
    fun getSites(@QueryMap params: Map<String, String>): Call<List<Enterprise>>

    @Headers("Content-Type: application/json")
    @POST("api-rest-auth/facebook/")
    fun enviartoken(@Body access_token: java.util.HashMap<String, String>): Call<Cuenta>

    @Headers("Content-Type: application/json")
    @POST("api-rest-auth/google/")
    fun enviartokengoogle(@Body access_token: java.util.HashMap<String, String>): Call<Cuenta>

}