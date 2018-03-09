package com.aitec.sitesport.main

import android.util.Log

/**
 * Created by victor on 27/1/18.
 */
class MainInteractorImp(var repository: MainRepository) : MainInteractor {
    override fun getSearchUserEntrepise(query: String): Boolean {
        if (query.length > 0) {
            repository.getSearchUserEntrepise(query)
            return true
        } else {
            Log.e("mainI", "query muy pequeño")
            return false
        }
    }

    override fun stopSearchUserEntrepise() {
        repository.stopSearchUserEntrepise()
    }
}