package com.aitec.sitesport.entities

/**
 * Created by Jhony on 27 abr 2018.
 */
class Cuenta {





    var token: String? = null
    var user: User? = null

    companion object {
        const val EMAIL = 0
        const val FACEBOOK = 1
        const val GOOGLE = 2
    }

}