package com.aitec.sitesport.home

/**
 * Created by Jhony on 28 may 2018.
 */


class HomeInteractorImp(var repository: HomeRepository) : HomeInteractor {
    override fun remove() {
        repository.remove()
    }

    override fun getHome() {

        repository.getHome()

    }


}