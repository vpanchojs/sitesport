package com.aitec.sitesport.domain

import android.util.Log
import com.aitec.sitesport.domain.listeners.onApiActionListener
import com.aitec.sitesport.entities.enterprise.*
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.StorageReference


class FirebaseApi(var db: FirebaseFirestore, var mAuth: FirebaseAuth, var storage: StorageReference) {

    fun autenticationGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
                .addOnSuccessListener {

                }
                .addOnFailureListener {

                }
    }

    fun autenticationFacebook(accesToken: String) {
        val credential = FacebookAuthProvider.getCredential(accesToken);
        mAuth.signInWithCredential(credential)
                .addOnSuccessListener {

                }.addOnFailureListener {

                }
    }



    //S1lqtUnghcdcPwPRsRPr
    fun getBasicProfile(pk: String, callback: onApiActionListener<Enterprise>){
        db.collection("centro_deportivo").document(pk)
                .get()//.addOnCompleteListener(object : OnCompleteListener<QuerySnapshot>())
                .addOnSuccessListener {
                    Log.e(TAG, it.id + "getBasicProfile() => " + it.data)
                    val e = it.toObject(Enterprise::class.java)!!
                    e.pk = it.id
                    callback.onSucces(e)
                }
                .addOnFailureListener {
                    Log.d(TAG, "Error => " + it.message)
                    callback.onError(it.message)
                }
    }

    /*fun getLikesProfile(pk: String, callback: onApiActionListener<Enterprise>){
        db.collection("centro_deportivo").document(pk).collection("like")
                .get()//.addOnCompleteListener(object : OnCompleteListener<QuerySnapshot>())
                .addOnSuccessListener {
                    it.size()
                    Log.e(TAG, "Likes ")
                    //callback.onSucces(e)
                }
                .addOnFailureListener {
                    Log.d(TAG, "Error => " + it.message)
                    callback.onError(it.message)
                }
    }*/

    fun getTableTimeProfile(idSportCenter: String, callback: onApiActionListener<Enterprise>){
        db.collection("centro_deportivo").document(idSportCenter).collection("table_time")
                .get()//.addOnCompleteListener(object : OnCompleteListener<QuerySnapshot>())
                .addOnSuccessListener {
                    val dayList = ArrayList<Dia>()
                    it.forEach {
                        dayList.add(it.toObject(Dia::class.java))
                        Log.d(TAG, "Success => Horario = " + it.toObject(Dia::class.java).nombre)
                    }
                    val enterprise = Enterprise()
                    enterprise.horario = dayList

                    if(it.metadata.isFromCache) enterprise.isOnline = false
                    callback.onSucces(enterprise)
                }
                .addOnFailureListener {
                    Log.d(TAG, "Error => " + it.message)
                    callback.onError(it.message)
                }

    }

    fun getCourts(idSportCenter: String, callback: onApiActionListener<Enterprise>){
        db.collection("centro_deportivo").document(idSportCenter).collection("court")
                .get()//.addOnCompleteListener(object : OnCompleteListener<QuerySnapshot>())
                .addOnSuccessListener {
                    val courtList = ArrayList<Cancha>()
                    it.forEach {
                        courtList.add(it.toObject(Cancha::class.java))
                        Log.d(TAG, "Success => Canchas = " + it.toObject(Cancha::class.java).nombre)
                    }
                    val enterprise = Enterprise()
                    enterprise.canchas = courtList

                    if(it.metadata.isFromCache) enterprise.isOnline = false

                    callback.onSucces(enterprise)
                }
                .addOnFailureListener {
                    Log.d(TAG, "Error => " + it.message)
                    callback.onError(it.message)
                }

    }

    fun getServices(idSportCenter: String, callback: onApiActionListener<Enterprise>){
        db.collection("centro_deportivo").document(idSportCenter).collection("service")
                .get()//.addOnCompleteListener(object : OnCompleteListener<QuerySnapshot>())
                .addOnSuccessListener {
                    val serviceList = ArrayList<Servicio>()
                    it.forEach {
                        serviceList.add(it.toObject(Servicio::class.java))
                        Log.d(TAG, "Success => Servicios = " + it.toObject(Servicio::class.java).nombre)
                        Log.d(TAG, "Success => Servicios = " + it.toObject(Servicio::class.java))

                    }
                    val enterprise = Enterprise()
                    enterprise.servicios = serviceList

                    if(it.metadata.isFromCache) enterprise.isOnline = false

                    callback.onSucces(enterprise)
                }
                .addOnFailureListener {
                    Log.d(TAG, "Error => " + it.message)
                    callback.onError(it.message)
                }

    }

    fun getContacts(idSportCenter: String, callback: onApiActionListener<Enterprise>){
        db.collection("centro_deportivo").document(idSportCenter).collection("social_network")
                .get()//.addOnCompleteListener(object : OnCompleteListener<QuerySnapshot>())
                .addOnSuccessListener {
                    val redSocialList = ArrayList<RedSocial>()
                    it.forEach {
                        redSocialList.add(it.toObject(RedSocial::class.java))
                        Log.d(TAG, "Success => RedSocial = " + it.toObject(RedSocial::class.java).nombre)
                    }
                    val enterprise = Enterprise()
                    enterprise.redesSociales = redSocialList
                    if(it.metadata.isFromCache) enterprise.isOnline = false
                    callback.onSucces(enterprise)
                }
                .addOnFailureListener {
                    Log.d(TAG, "Error => " + it.message)
                    callback.onError(it.message)
                }
    }

    companion object {
        const val TAG = "FirebaseApi"
    }

}