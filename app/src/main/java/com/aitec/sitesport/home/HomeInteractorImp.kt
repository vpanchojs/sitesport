package com.aitec.sitesport.home

import com.aitec.sitesport.lib.base.EventBusInterface
import com.aitec.sitesport.reserve.ReserveInteractor
import com.aitec.sitesport.reserve.ReserveRepository

/**
 * Created by Jhony on 28 may 2018.
 */




class HomeInteractorImp (var repository: HomeRepository) : HomeInteractor {
    override fun getHome() {

        repository.getHome()
    }


}