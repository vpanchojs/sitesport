package com.aitec.sitesport.sites

class SitesInteractorImp(var repository: SitesRepository) : SitesInteractor {

    override fun onGetSites() {
        repository.onGetSites()
    }
}