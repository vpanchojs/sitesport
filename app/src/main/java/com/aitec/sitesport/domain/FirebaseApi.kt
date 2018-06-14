package com.aitec.sitesport.domain

import android.net.Uri
import android.os.Handler
import com.aitec.sitesport.entities.User
import android.util.Log
import com.aitec.sitesport.domain.listeners.RealTimeListener
import com.aitec.sitesport.domain.listeners.onApiActionListener
import com.aitec.sitesport.entities.Publications
import com.aitec.sitesport.entities.SearchCentersName
import com.aitec.sitesport.entities.enterprise.*
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.*
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.storage.StorageReference
import com.google.gson.Gson
import android.support.constraint.solver.widgets.ResolutionNode.REMOVED
import com.google.firebase.firestore.DocumentChange




class FirebaseApi(var db: FirebaseFirestore, var mAuth: FirebaseAuth, var storage: StorageReference, var fuctions: FirebaseFunctions) {

    companion object {
        const val TAG = "FirebaseApi"
        const val PATH_USER = "users"

    }

    var mAuthListener: FirebaseAuth.AuthStateListener? = null
    var pulistener: ListenerRegistration?=null
    private var handlerSearchName: Handler? = null
    private var runnableSearchName: Runnable? = null

    fun autenticationGoogle(idToken: String, callback: onApiActionListener<User>) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
                .addOnSuccessListener {
                    Log.e(TAG, "BIEN" + it.user.providers)
                    val user = User()
                    user.pk = it.user.uid
                    user.names = it.user.displayName
                    user.email = it.user.email
                    user.photo = it.user.photoUrl.toString()
                    callback.onSucces(user)
                }
                .addOnFailureListener {
                    Log.e(TAG, it.toString())
                    callback.onError(it.message + "")
                }
    }

    fun autenticationFacebook(accesToken: String, callback: onApiActionListener<User>) {
        val credential = FacebookAuthProvider.getCredential(accesToken);
        mAuth.signInWithCredential(credential)
                .addOnSuccessListener {
                    Log.e(TAG, "BIEN" + it.user.providers)
                    val user = User()
                    user.pk = it.user.uid
                    user.names = it.user.displayName
                    user.email = it.user.email
                    user.photo = it.user.photoUrl.toString()
                    callback.onSucces(user)
                }.addOnFailureListener {
                    Log.e(TAG, it.toString())
                    callback.onError(it.message + "")
                }
    }

    fun sigOut() {
        mAuth.signOut()
    }


    fun suscribeAuth(callback: onApiActionListener<User>) {
        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val userAuth = firebaseAuth.currentUser
            if (userAuth != null) {
                Log.e(TAG, "EL display" + mAuth.currentUser!!.displayName.toString())
                val user = User()
                user.pk = userAuth.uid
                user.names = userAuth.displayName
                user.email = userAuth.email
                user.photo = userAuth.photoUrl.toString()
                callback.onSucces(user)
            } else {
                callback.onError(Unit)
            }
        }
        mAuth.addAuthStateListener(mAuthListener!!)
    }


    fun unSuscribeAuth() {
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener!!)
        }
    }

    fun updateUser(user: User, listener: onApiActionListener<Unit>) {

        updateProfileData(user.names!!, user.photo!!, object : onApiActionListener<Unit> {
            override fun onSucces(response: Unit) {
                db.collection(PATH_USER).document(mAuth.currentUser!!.uid).set(user.toMapPost())
                        .addOnSuccessListener {
                            listener.onSucces(Unit)

                        }.addOnFailureListener {

                            listener.onError(it.message)
                        }
            }

            override fun onError(error: Any?) {
                listener.onError(error)
            }
        })


    }


    fun updateProfileData(names: String, photo: String, listener: onApiActionListener<Unit>) {

        val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(names)
                .setPhotoUri(Uri.parse(photo))
                .build()

        mAuth.currentUser!!.updateProfile(profileUpdates)
                .addOnSuccessListener {
                    listener.onSucces(Unit)
                }
                .addOnFailureListener {
                    Log.e(TAG, "error" + it)
                    listener.onError(it.toString())
                }
    }

    fun getUid(): String {
        return mAuth.currentUser!!.uid
    }

    fun getInfoUser(listener: onApiActionListener<DocumentSnapshot>) {

        db.collection(PATH_USER).document(getUid()).get()
                .addOnSuccessListener {
                    listener.onSucces(it)
                }
                .addOnFailureListener {
                    listener.onError(it.message)
                }

    }


    //S1lqtUnghcdcPwPRsRPr
    fun getBasicProfile(idEnterprise: String, callback: onApiActionListener<Enterprise>){
        db.collection("centro_deportivo").document(idEnterprise)
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

    fun getTableTimeProfile(idEnterprise: String, callback: onApiActionListener<Enterprise>){
        db.collection("centro_deportivo").document(idEnterprise).collection("table_time")
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

    fun getCourts(idEnterprise: String, callback: onApiActionListener<Enterprise>){
        db.collection("centro_deportivo").document(idEnterprise).collection("court")
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

    fun getServices(idEnterprise: String, callback: onApiActionListener<Enterprise>){
        db.collection("centro_deportivo").document(idEnterprise).collection("service")
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

    fun getContacts(idEnterprise: String, callback: onApiActionListener<Enterprise>){
        db.collection("centro_deportivo").document(idEnterprise).collection("social_network")
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

    fun getLike(idUser: String, idEnterprise: String, callback: onApiActionListener<Boolean>){
        db.collection("centro_deportivo")
                .document(idEnterprise)
                .collection("like").document(idUser)
                .get()
                .addOnSuccessListener {
                    callback.onSucces(it.exists())
                }
    }

    fun removeLike(idUser: String, idEnterprise: String, callback: onApiActionListener<Int>){
        val ref = db.collection("centro_deportivo")
                .document(idEnterprise)

        val refEnterprise = db.collection("centro_deportivo")
                .document(idEnterprise)
                .collection("like")
                .document(idUser)

        val refUser = db.collection("users")
                .document(idUser)
                .collection("like")
                .document(idEnterprise)

        db.runTransaction { it ->
            val e = it.get(ref).toObject(Enterprise::class.java)!!
            val likes = e.likes - 1

            val hashMapLikes = HashMap<String, Any>()
            hashMapLikes["likes"] = likes
            it.update(ref, hashMapLikes)

            it.delete(refEnterprise)
            it.delete(refUser)
            likes
        }.addOnSuccessListener{
            callback.onSucces(it)
        }.addOnFailureListener{
            callback.onError(it.message)
        }
    }

    fun setLike(idUser: String, idEnterprise: String, callback: onApiActionListener<Int>){
        val ref = db.collection("centro_deportivo")
                .document(idEnterprise)

        val refEnterprise = db.collection("centro_deportivo")
                .document(idEnterprise)
                .collection("like")
                .document(idUser)

        val refUser = db.collection("users")
                .document(idUser)
                .collection("like")
                .document(idEnterprise)

        db.runTransaction { it ->
            val e = it.get(ref).toObject(Enterprise::class.java)!!
            val likes = e.likes + 1

            val hashMapLikes = HashMap<String, Any>()
            hashMapLikes["likes"] = likes
            it.update(ref, hashMapLikes)

            val hashMapDate = HashMap<String, Any>()
            hashMapDate["fecha_like"] = FieldValue.serverTimestamp()

            it.set(refEnterprise, hashMapDate)
            it.set(refUser, hashMapDate)
            likes
        }.addOnSuccessListener{
            callback.onSucces(it)
        }.addOnFailureListener{
            callback.onError(it.message)
        }
    }



    fun getSearchName(query: String, listener: onApiActionListener<SearchCentersName>) {
        var parametros = HashMap<String, String>()
        parametros.put("query", query)
        val gson = Gson()
        handlerSearchName = Handler()
        runnableSearchName = Runnable {
            fuctions.getHttpsCallable("searchCentersName")
                    .call(parametros)
                    .addOnSuccessListener {
                        val searchCentersName = SearchCentersName()
                        val listSportCenter = ArrayList<Enterprise>()


                        var data = it.data as ArrayList<HashMap<String, Any>>
                        data.forEach { item ->
                            val entrepise = gson.fromJson(gson.toJson(item), Enterprise::class.java)
                            listSportCenter.add(entrepise)
                            Log.e(TAG, "error ${entrepise.nombres}")
                        }
                        searchCentersName.results = listSportCenter
                        listener.onSucces(searchCentersName)
                    }
                    .addOnFailureListener {
                        Log.e(TAG, "error $it")
                        listener.onError(it.message)
                    }
        }
        val LAG = 1000
        handlerSearchName!!.postDelayed(runnableSearchName, LAG.toLong())


    }

    fun deleteRequestSearchName() {
        if (handlerSearchName != null) {
            handlerSearchName?.removeCallbacks(runnableSearchName)
            handlerSearchName = null
        }
    }

    fun getAllSites(parametros: HashMap<String, String>, onApiActionListener: onApiActionListener<List<Enterprise>>) {
        val gson = Gson()
        parametros.put("query", "ti")
        Log.e(TAG, "llegue")
        //db.collection(CENTER_SPORT_PATH).orderBy("numero_likes", Query.Direction.DESCENDING)
        /*
        fuctions.getHttpsCallable("helloWorld")
                .call(parametros)
                .addOnSuccessListener {
                    val query = it.data as ArrayList<HashMap<String, Any>>
                    query.forEach { item ->
                        var entrepise = gson.fromJson(gson.toJson(item), Enterprise::class.java)
                        Log.e(TAG, "error ${entrepise.nombres}")
                    }

                }
                .addOnFailureListener {
                    Log.e(TAG, "error $it")
                }
                */
    }

    fun getHome(callback: RealTimeListener<Publications>) {
        pulistener=db.collection("publish").addSnapshotListener { querySnapshot, e ->
            if (e != null) {
                Log.w(TAG, "listen:error", e)
                callback.omError(e)
            }
            querySnapshot!!.documentChanges.forEach {
                when (it.getType()) {
                    DocumentChange.Type.ADDED ->{
                        var pu=it.document.toObject(Publications::class.java)
                        pu.id=it.document.id
                        callback.addDocument(pu)
                    }
                    DocumentChange.Type.MODIFIED ->{
                        var pu=it.document.toObject(Publications::class.java)
                        pu.id=it.document.id
                        callback.updateDocument(pu)
                }
                    DocumentChange.Type.REMOVED ->{
                        var pu=it.document.toObject(Publications::class.java)
                        pu.id=it.document.id
                        callback.removeDocument(pu)
                }
                }
            }

        }

    }

    /*
    fun onSetRaiting(raiting: Raiting, update: Boolean, oldRaiting: Double) {

        raiting.nameUser = getNameUser()
        raiting.me = true

        var newAvgRating: Double = 0.0
        /*Referencia al cuestionrio a calificar */
        var questionaireRef = db.collection(QUESTIONNAIRE_PATH).document(raiting.idQuestionaire)

        var ratingsRef: DocumentReference

        /*Referencia al nodo de calificaciones*/
        //if (raiting.idRaiting.isNullOrBlank()) {
        ratingsRef = questionaireRef.collection(RATING_PATH).document(getUid())
        //} else {
        //ratingsRef = questionaireRef.collection(RATING_PATH).document(raiting.idRaiting)
        //}


        db.runTransaction {
            var questionnaire = it.get(questionaireRef).toObject(Questionaire::class.java)


            // Compute new number of ratings
            val newNumRatings = if (update) questionnaire!!.numAssessment else questionnaire!!.numAssessment + 1

            var aux = questionnaire!!.assessment
            if (update) {
                aux = questionnaire!!.assessment - oldRaiting
            }

            // Compute new average rating
            val oldRatingTotal = aux * questionnaire.numAssessment
            newAvgRating = (oldRatingTotal + raiting.value) / newNumRatings

            // Set new info
            questionnaire.numAssessment = newNumRatings
            questionnaire.assessment = newAvgRating

            raiting.idRaiting = questionaireRef.id

            // actualizamos el cuestionnario
            it.update(questionaireRef, questionnaire.toMapRating())

            //creamos la calificacion
            it.set(ratingsRef, raiting.toMap())

        }
                .addOnSuccessListener {
                    Log.e("R", "todo bien" + newAvgRating)
                    callback.onSuccess(raiting)
                }
                .addOnFailureListener {
                    Log.e("R", it.toString())
                    callback.onError(it.message)
                }
    }
    */



}