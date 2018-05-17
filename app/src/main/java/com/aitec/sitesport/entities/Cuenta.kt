package com.aitec.sitesport.entities

/**
 * Created by Jhony on 27 abr 2018.
 */
class Cuenta {
    var token: String? = null

    private val user: User? = null

    inner class User {


        var pk: String? = null
        var email: String? = null
        var username: String? = null
        var isIs_active: Boolean = false
    }
}