package com.aitec.sitesport.publication

class PublicationInteractorImpl(val publicationRepository: PublicationRepository) : PublicationInteractor {
    override fun callPublication(pk: String) {
        publicationRepository.callPublication(pk)
    }
}