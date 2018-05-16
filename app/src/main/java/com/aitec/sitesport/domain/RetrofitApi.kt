package com.aitec.sitesport.domain

import RetrofitStatus
import android.os.Handler
import android.util.Log
import com.aitec.sitesport.domain.listeners.onApiActionListener
import com.aitec.sitesport.entities.SearchCentersName
import com.aitec.sitesport.entities.enterprise.Enterprise
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitApi {

    private val LAG = 500
    private var handlerSearchName: Handler? = null
    private var runnableSearchName: Runnable? = null

    private var handlerSearchVisibility: Handler? = null
    private var runnableSearchVisibility: Runnable? = null

    lateinit var requestSearchVisibility: Call<List<Enterprise>>

    lateinit var requestSearchName: Call<SearchCentersName>

    companion object {
        const val TAG = "RetrofitApi"
        val PATH_API = "http://18.219.31.241:8050/"
        const val PATH_SEARCH_CENTER = "api/search-centros/"
        const val PATH_SEARCH_NAME_CENTER_SPORT = "api/centros-deportivos/"
        const val PATH_PROFILE = "api/centros-deportivos/"
        const val PATH_IMAGES = "api/centros-deportivos/"
        const val PATH_CENTER_SPORT = "api/centros-deportivos/"

    }

    val retrofit = Retrofit.Builder()
            .baseUrl(PATH_API)
            .addConverterFactory(GsonConverterFactory.create(
                    GsonBuilder().serializeNulls().create())
            )
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

        requestSearchVisibility = request.getCenterSportVisible(parametros)

        handlerSearchVisibility = Handler()
        runnableSearchVisibility = Runnable {

            requestSearchVisibility.enqueue(object : Callback<List<Enterprise>> {
                override fun onResponse(call: Call<List<Enterprise>>, response: Response<List<Enterprise>>) {
                    RetrofitStatus.Response(response, object : RetrofitStatus.MyCallbackResponse<List<Enterprise>> {
                        override fun success(response: Response<List<Enterprise>>) {
                            //Respuesta correcta
                            callback.onSucces(response.body())
                        }

                        override fun unauthenticated(response: Response<*>) {
                            Log.e("unaA", response.message())
                            callback.onError(response.message())
                        }

                        override fun clientError(response: Response<*>) {
                            Log.e("ce", response.message())
                            callback.onError(response.message())
                        }

                        override fun serverError(response: Response<*>) {
                            Log.e("se", response.message())
                            callback.onError(response.message())
                        }


                        override fun unexpectedError(t: Throwable) {
                            Log.e("unaE", response.message())
                            callback.onError(response.message())
                        }
                    })
                }


                override fun onFailure(call: Call<List<Enterprise>>, t: Throwable) {
                    RetrofitStatus.Failure(t, object : RetrofitStatus.MyCallbackFailure<List<Enterprise>> {
                        override fun networkError(error: String) {
                            callback.onError(error)
                        }

                        override fun unexpectedError(t: Throwable) {
                            Log.e("unE", t!!.toString())
                            callback.onError(t!!.toString())
                        }
                    }
                    )


                }
            })
        }

        handlerSearchVisibility!!.postDelayed(runnableSearchVisibility, LAG.toLong())
    }

    fun deleteRequestGetCenterSport() {
        if (handlerSearchVisibility != null) {
            handlerSearchVisibility?.removeCallbacks(runnableSearchVisibility)
            handlerSearchVisibility = null
        }

        if (::requestSearchVisibility.isInitialized) {
            Log.e("delte", "eliminar peticion visibility")
            requestSearchVisibility.cancel()

        }
    }

    fun onSearchNameCenterSport(query: String, callback: onApiActionListener) {
        requestSearchName = request.searchNameCenterSport(query, 1)

        handlerSearchName = Handler()
        runnableSearchName = Runnable {
            requestSearchName.enqueue(object : Callback<SearchCentersName> {

                override fun onResponse(call: Call<SearchCentersName>?, response: Response<SearchCentersName>?) {
                    RetrofitStatus.Response(response!!, object : RetrofitStatus.MyCallbackResponse<SearchCentersName> {

                        override fun success(response: Response<SearchCentersName>) {
                            callback.onSucces(response.body())
                        }

                        override fun unauthenticated(response: Response<*>) {
                            Log.e("unaA", response.message())
                            callback.onError(response.message())
                        }

                        override fun clientError(response: Response<*>) {
                            Log.e("ce", response.message())
                            callback.onError(response.message())
                        }

                        override fun serverError(response: Response<*>) {
                            Log.e("se", response.message())
                            callback.onError(response.message())
                        }


                        override fun unexpectedError(t: Throwable) {
                            Log.e("unaE", response.message())
                            callback.onError(response.message())
                        }
                    })

                }

                override fun onFailure(call: Call<SearchCentersName>?, t: Throwable?) {

                    RetrofitStatus.Failure(t!!, object : RetrofitStatus.MyCallbackFailure<SearchCentersName> {
                        override fun networkError(e: String) {
                            callback.onError(e)
                        }

                        override fun unexpectedError(t: Throwable) {
                            Log.e("unE", t!!.toString())
                            callback.onError(t!!.toString())
                        }
                    })

                }
            })
        }
        handlerSearchName!!.postDelayed(runnableSearchName, LAG.toLong())
    }

    fun getProfile(pk: String, callback: onApiActionListener) {
        val parametros = HashMap<String, String>()
        parametros.put("pk", pk)


        request.getProfile(pk).enqueue(object : Callback<Enterprise> {
            override fun onResponse(call: Call<Enterprise>, response: Response<Enterprise>) {
                callback.onSucces(response.body())
            }

            override fun onFailure(call: Call<Enterprise>, t: Throwable) {
                //callback.onError(t.message.toString())
                RetrofitStatus.Failure(t!!, object : RetrofitStatus.MyCallbackFailure<SearchCentersName> {
                    override fun networkError(e: String) {
                        callback.onError(e)
                    }

                    override fun unexpectedError(t: Throwable) {
                        callback.onError(t!!.toString())
                    }
                })
            }
        })

    }

    fun deleteRequestSearchName() {
        if (handlerSearchName != null) {
            handlerSearchName?.removeCallbacks(runnableSearchName)
            handlerSearchName = null
        }
        Log.e("e", "eliminando peticion" + ::requestSearchName.isInitialized)
        if (::requestSearchName.isInitialized) {
            requestSearchName.cancel()
            Log.e("delte", "eliminar peticion name")
        }
    }

    fun getAllSites(callback: onApiActionListener) {
        request.getSites().enqueue(object : Callback<List<Enterprise>> {
            override fun onFailure(call: Call<List<Enterprise>>?, t: Throwable?) {
                RetrofitStatus.Failure(t!!, object : RetrofitStatus.MyCallbackFailure<List<Enterprise>> {
                    override fun networkError(e: String) {
                        callback.onError(e)
                    }

                    override fun unexpectedError(t: Throwable) {
                        callback.onError(t.toString())
                    }
                })

            }

            override fun onResponse(call: Call<List<Enterprise>>?, response: Response<List<Enterprise>>?) {
                RetrofitStatus.Response(response!!, object : RetrofitStatus.MyCallbackResponse<List<Enterprise>> {
                    override fun success(response: Response<List<Enterprise>>) {
                        callback.onSucces(response.body())
                    }

                    override fun unauthenticated(response: Response<*>) {
                        callback.onError(response.toString())
                    }

                    override fun clientError(response: Response<*>) {
                        callback.onError(response.toString())
                    }

                    override fun serverError(response: Response<*>) {
                        callback.onError(response.toString())
                    }

                    override fun unexpectedError(t: Throwable) {
                        callback.onError(t.toString())
                    }
                })
            }
        })
    }

    fun getAllSites(params: HashMap<String, String>, callback: onApiActionListener) {
        Log.e(TAG, "PARAMETROS ${params.toString()}")
        request.getSites(params).enqueue(object : Callback<List<Enterprise>> {
            override fun onFailure(call: Call<List<Enterprise>>?, t: Throwable?) {
                RetrofitStatus.Failure(t!!, object : RetrofitStatus.MyCallbackFailure<List<Enterprise>> {
                    override fun networkError(e: String) {
                        callback.onError(e)
                    }

                    override fun unexpectedError(t: Throwable) {
                        callback.onError(t.toString())
                    }
                })

            }

            override fun onResponse(call: Call<List<Enterprise>>?, response: Response<List<Enterprise>>?) {
                RetrofitStatus.Response(response!!, object : RetrofitStatus.MyCallbackResponse<List<Enterprise>> {
                    override fun success(response: Response<List<Enterprise>>) {
                        callback.onSucces(response.body())
                    }

                    override fun unauthenticated(response: Response<*>) {
                        callback.onError(response.toString())
                    }

                    override fun clientError(response: Response<*>) {
                        callback.onError(response.toString())
                    }

                    override fun serverError(response: Response<*>) {
                        callback.onError(response.toString())
                    }

                    override fun unexpectedError(t: Throwable) {
                        callback.onError(t.toString())
                    }
                })
            }
        })

    }

}