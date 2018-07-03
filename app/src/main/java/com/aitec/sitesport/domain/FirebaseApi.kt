package com.aitec.sitesport.domain

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.util.Log
import com.aitec.sitesport.domain.listeners.RealTimeListener
import com.aitec.sitesport.domain.listeners.onApiActionListener
import com.aitec.sitesport.entities.*
import com.aitec.sitesport.entities.enterprise.*
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.*
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.storage.StorageReference
import com.google.gson.Gson
import java.io.ByteArrayOutputStream


class FirebaseApi(var db: FirebaseFirestore, var mAuth: FirebaseAuth, var storage: StorageReference, var fuctions: FirebaseFunctions) {

    companion object {
        const val TAG = "FirebaseApi"
        const val PATH_USER = "usuario"
        const val PATH_TEAMS = "equipo"
        const val PATH_ENCUENTROS = "encuentros"
        const val STORAGE_USER_PHOTO_PATH = "usuario_photos"
        const val PATH_SPORT_CENTER = "centro_deportivo"
        const val PATH_TABLE_TIME = "horario"
        const val PATH_LIKES = "me_gustas"
        const val PATH_DATE_LIKES = "fecha_me_gusta"
        const val PATH_COURT = "cancha"
        const val PATH_SOCIAL_NETWORK = "red_social"
        const val PATH_SERVICIE = "servicio"
        const val PATH_RESERVATION = "reservacion"
        const val PATH_PUBLICATIONS = "publicacion"
        const val PATH_SEARCH_CENTER_SPORT_BY_NAME = "buscar_centro_deportivo_por_nombre"
        const val PATH_FILTER_OPEN_CENTER_SPORT = "filtro_abierto_centro_deportivo"
        const val PATH_FILTER_DISTANCE_CENTER_SPORT = "filtro_cercanos_centro_deportivo"

    }

    var mAuthListener: FirebaseAuth.AuthStateListener? = null
    var pulistener: ListenerRegistration? = null
    private var handlerSearchName: Handler? = null
    private var runnableSearchName: Runnable? = null
    val gson = Gson()

    fun autenticationGoogle(idToken: String, user: User, callback: onApiActionListener<User>) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
                .addOnSuccessListener {
                    Log.e(TAG, "BIEN" + it.additionalUserInfo.isNewUser)
                    /*
                    val user = User()
                    user.pk = it.user.uid
                    user.nombre_centro_deportivo = it.user.displayName
                    user.correo_electronico = it.user.correo_electronico

                    user.foto = it.user.photoUrl.toString()
                    */
                    if (it.additionalUserInfo.isNewUser) {
                        user.pk = it.user.uid
                        saveUser(user, object : onApiActionListener<Unit> {
                            override fun onSucces(response: Unit) {
                                callback.onSucces(user)
                            }

                            override fun onError(error: Any?) {
                                callback.onError(error)
                            }
                        })
                    } else {
                        callback.onSucces(user)
                    }
                }
                .addOnFailureListener {
                    callback.onError(ManagerExcepcionFirebase.getMessageErrorFirebaseAuth(it))
                }
    }

    fun autenticationFacebook(accesToken: String, user: User, callback: onApiActionListener<User>) {
        val credential = FacebookAuthProvider.getCredential(accesToken);
        mAuth.signInWithCredential(credential)
                .addOnSuccessListener {
                    Log.e(TAG, "BIEN" + it.user.providers)
                    /*
                    val user = User()
                    user.pk = it.user.uid
                    user.nombre_centro_deportivo = it.user.displayName
                    user.correo_electronico = it.user.correo_electronico
                    user.foto = it.user.photoUrl.toString()
                    */
                    user.correo_electronico = it.user.email!!

                    if (it.additionalUserInfo.isNewUser) {
                        user.pk = it.user.uid

                        saveUser(user, object : onApiActionListener<Unit> {
                            override fun onSucces(response: Unit) {
                                callback.onSucces(user)
                            }

                            override fun onError(error: Any?) {
                                callback.onError(error)
                            }
                        })
                    } else {
                        callback.onSucces(user)
                    }


                }.addOnFailureListener {
                    callback.onError(ManagerExcepcionFirebase.getMessageErrorFirebaseAuth(it))

                }
    }


    fun saveUser(user: User, callback: onApiActionListener<Unit>) {
        db.collection(PATH_USER).document(user.pk!!).set(user.toMapPostSave()).addOnSuccessListener {
            callback.onSucces(Unit)
        }.addOnFailureListener {
            callback.onError(ManagerExcepcionFirebase.getMessageErrorFirebaseFirestore(it))
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
                user.nombre = userAuth.displayName
                user.correo_electronico = userAuth.email!!
                user.foto = userAuth.photoUrl.toString()
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

        updateProfileData(user.nombre + " " + user.apellido, object : onApiActionListener<Unit> {
            override fun onSucces(response: Unit) {
                db.collection(PATH_USER).document(mAuth.currentUser!!.uid).update(user.toMapPost())
                        .addOnSuccessListener {

                            listener.onSucces(Unit)

                        }.addOnFailureListener {
                            listener.onError(ManagerExcepcionFirebase.getMessageErrorFirebaseFirestore(it))
                        }
            }

            override fun onError(error: Any?) {
                listener.onError(error)
            }
        })

    }


    fun updateProfileData(names: String, listener: onApiActionListener<Unit>) {

        val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(names)
                .build()

        mAuth.currentUser!!.updateProfile(profileUpdates)
                .addOnSuccessListener {
                    listener.onSucces(Unit)
                }
                .addOnFailureListener {
                    Log.e(TAG, "error" + it)
                    listener.onError(ManagerExcepcionFirebase.getMessageErrorFirebaseFirestore(it))
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
                    listener.onError(ManagerExcepcionFirebase.getMessageErrorFirebaseFirestore(it))
                }

    }


    //S1lqtUnghcdcPwPRsRPr
    fun getBasicProfile(idEnterprise: String, callback: onApiActionListener<Enterprise>) {
        db.collection(PATH_SPORT_CENTER).document(idEnterprise)
                .get()//.addOnCompleteListener(object : OnCompleteListener<QuerySnapshot>())
                .addOnSuccessListener {
                    Log.e(TAG, it.id + "getBasicProfile() => " + it.data)
                    val e = it.toObject(Enterprise::class.java)!!
                    e.pk = it.id
                    if (it.metadata.isFromCache) {
                        e.isOnline = false
                    }
                    callback.onSucces(e)
                }
                .addOnFailureListener {
                    Log.d(TAG, "Error => " + it.message)
                    callback.onError(ManagerExcepcionFirebase.getMessageErrorFirebaseFirestore(it))
                }
    }

    fun isAuthenticated(): String? {
        return mAuth.currentUser?.uid
    }

    fun getTableTimeProfile(idEnterprise: String, callback: onApiActionListener<Enterprise>) {
        db.collection(PATH_SPORT_CENTER).document(idEnterprise).collection(PATH_TABLE_TIME)
                .get()//.addOnCompleteListener(object : OnCompleteListener<QuerySnapshot>())
                .addOnSuccessListener {
                    val dayList: ArrayList<Dia> = arrayListOf()

                    it.forEach {
                        val d = it.toObject(Dia::class.java)
                        d.pk = it.id
                        dayList.add(d)
                        Log.d(TAG, "Success => Horario = " + it.toObject(Dia::class.java).nombre)
                    }
                    val enterprise = Enterprise()
                    enterprise.horarios = dayList

                    if (it.metadata.isFromCache) enterprise.isOnline = false
                    callback.onSucces(enterprise)
                }
                .addOnFailureListener {
                    Log.d(TAG, "Error => " + it.message)
                    callback.onError(ManagerExcepcionFirebase.getMessageErrorFirebaseFirestore(it))
                }
    }

    fun getCourts(idEnterprise: String, callback: onApiActionListener<Enterprise>) {
        db.collection(PATH_SPORT_CENTER).document(idEnterprise).collection(PATH_COURT)
                .get()//.addOnCompleteListener(object : OnCompleteListener<QuerySnapshot>())
                .addOnSuccessListener {
                    val courtList = ArrayList<Cancha>()
                    it.forEach {
                        val c = it.toObject(Cancha::class.java)
                        c.pk = it.id
                        courtList.add(c)
                        Log.d(TAG, "Success => Canchas = " + it.toObject(Cancha::class.java).nombre)
                    }
                    val enterprise = Enterprise()
                    enterprise.canchas = courtList

                    if (it.metadata.isFromCache) enterprise.isOnline = false

                    callback.onSucces(enterprise)
                }
                .addOnFailureListener {
                    Log.d(TAG, "Error => " + it.message)
                    callback.onError(ManagerExcepcionFirebase.getMessageErrorFirebaseFirestore(it))
                }

    }

    fun getServices(idEnterprise: String, callback: onApiActionListener<Enterprise>) {
        db.collection(PATH_SPORT_CENTER).document(idEnterprise).collection(PATH_SERVICIE)
                .whereEqualTo("activado", true)
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

                    if (it.metadata.isFromCache) enterprise.isOnline = false

                    callback.onSucces(enterprise)
                }
                .addOnFailureListener {
                    Log.d(TAG, "Error => " + it.message)
                    callback.onError(ManagerExcepcionFirebase.getMessageErrorFirebaseFirestore(it))
                }

    }

    fun getContacts(idEnterprise: String, callback: onApiActionListener<Enterprise>) {
        db.collection(PATH_SPORT_CENTER).document(idEnterprise).collection(PATH_SOCIAL_NETWORK)
                .whereEqualTo("activado", true)
                .get()//.addOnCompleteListener(object : OnCompleteListener<QuerySnapshot>())
                .addOnSuccessListener {
                    val redSocialList = ArrayList<RedSocial>()
                    it.forEach {
                        redSocialList.add(it.toObject(RedSocial::class.java))
                        Log.d(TAG, "Success => RedSocial = " + it.toObject(RedSocial::class.java).nombre)
                    }
                    val enterprise = Enterprise()
                    enterprise.redes_social = redSocialList
                    if (it.metadata.isFromCache) enterprise.isOnline = false
                    callback.onSucces(enterprise)
                }
                .addOnFailureListener {
                    Log.d(TAG, "Error => " + it.message)
                    callback.onError(ManagerExcepcionFirebase.getMessageErrorFirebaseFirestore(it))
                }
    }

    fun getLikes(idUser: String, idEnterprise: String, callback: onApiActionListener<Boolean>) {
        db.collection(PATH_SPORT_CENTER)
                .document(idEnterprise)
                .collection(PATH_LIKES).document(idUser)
                .get()
                .addOnSuccessListener {
                    callback.onSucces(it.exists())
                }
                .addOnFailureListener {
                    callback.onError(ManagerExcepcionFirebase.getMessageErrorFirebaseFirestore(it))
                }
    }

    fun removeLike(idUser: String, idEnterprise: String, callback: onApiActionListener<Int>) {
        val ref = db.collection(PATH_SPORT_CENTER)
                .document(idEnterprise)

        val refEnterprise = db.collection(PATH_SPORT_CENTER)
                .document(idEnterprise)
                .collection(PATH_LIKES)
                .document(idUser)

        val refUser = db.collection(PATH_USER)
                .document(idUser)
                .collection(PATH_LIKES)
                .document(idEnterprise)

        db.runTransaction { it ->
            val e = it.get(ref).toObject(Enterprise::class.java)!!
            val likes = e.me_gustas - 1

            val hashMapLikes = HashMap<String, Any>()
            hashMapLikes[PATH_LIKES] = likes
            it.update(ref, hashMapLikes)

            it.delete(refEnterprise)
            it.delete(refUser)
            likes
        }.addOnSuccessListener {
            callback.onSucces(it)
        }.addOnFailureListener {
            Log.e(TAG, "ERROR en el like")
            callback.onError(ManagerExcepcionFirebase.getMessageErrorFirebaseFirestore(it))
        }
    }

    fun setLike(idUser: String, idEnterprise: String, callback: onApiActionListener<Int>) {
        val ref = db.collection(PATH_SPORT_CENTER)
                .document(idEnterprise)

        val refEnterprise = db.collection(PATH_SPORT_CENTER)
                .document(idEnterprise)
                .collection(PATH_LIKES)
                .document(idUser)

        val refUser = db.collection(PATH_USER)
                .document(idUser)
                .collection(PATH_LIKES)
                .document(idEnterprise)

        db.runTransaction { it ->
            val e = it.get(ref).toObject(Enterprise::class.java)!!
            val likes = e.me_gustas + 1

            val hashMapLikes = HashMap<String, Any>()
            hashMapLikes[PATH_LIKES] = likes
            it.update(ref, hashMapLikes)

            val hashMapDate = HashMap<String, Any>()
            hashMapDate[PATH_DATE_LIKES] = FieldValue.serverTimestamp()

            it.set(refEnterprise, hashMapDate)
            it.set(refUser, hashMapDate)
            likes
        }.addOnSuccessListener {
            callback.onSucces(it)
        }.addOnFailureListener {
            Log.e(TAG, "ERROR en el like")
            callback.onError(ManagerExcepcionFirebase.getMessageErrorFirebaseFirestore(it))
        }
    }

    fun getPublication(pkPublication: String, callback: onApiActionListener<Publication>) {
        db.collection(PATH_PUBLICATIONS).document(pkPublication)
                .get()//.addOnCompleteListener(object : OnCompleteListener<QuerySnapshot>())
                .addOnSuccessListener {
                    Log.e(TAG, it.id + "getPublication() => " + it.data)
                    val p = it.toObject(Publication::class.java)!!
                    p.pk = it.id
                    if (it.metadata.isFromCache) p.isOnline = false
                    callback.onSucces(p)
                }
                .addOnFailureListener {
                    Log.d(TAG, "Error => " + it.message)
                    callback.onError(ManagerExcepcionFirebase.getMessageErrorFirebaseFirestore(it))
                }
    }

    fun getTeam(pkTeam: String, callback: onApiActionListener<Team>) {
        db.collection(PATH_TEAMS).document(pkTeam)
                .get()//.addOnCompleteListener(object : OnCompleteListener<QuerySnapshot>())
                .addOnSuccessListener {
                    Log.e(TAG, it.id + "getTeam() => " + it.data)
                    val p = it.toObject(Team::class.java)!!
                    p.pk = it.id
                    callback.onSucces(p)
                }
                .addOnFailureListener {
                    Log.d(TAG, "Error => " + it.message)
                    callback.onError(ManagerExcepcionFirebase.getMessageErrorFirebaseFirestore(it))
                }
    }


    fun getSearchName(query: String, listener: onApiActionListener<SearchCentersName>) {
        var parametros = HashMap<String, String>()
        parametros.put("query", query)

        handlerSearchName = Handler()
        runnableSearchName = Runnable {
            fuctions.getHttpsCallable(PATH_SEARCH_CENTER_SPORT_BY_NAME)
                    .call(parametros)
                    .addOnSuccessListener {
                        val searchCentersName = SearchCentersName()
                        val listSportCenter = ArrayList<Enterprise>()

                        Log.e("search", it.data.toString())


                        var data = it.data as ArrayList<HashMap<String, Any>>
                        data.forEach { item ->
                            val entrepise = gson.fromJson(gson.toJson(item), Enterprise::class.java)
                            listSportCenter.add(entrepise)
                            Log.e(TAG, "error ${entrepise.nombre}")
                        }
                        searchCentersName.results = listSportCenter

                        listener.onSucces(searchCentersName)

                    }
                    .addOnFailureListener {
                        Log.e(TAG, "error $it")
                        listener.onError("Posible problema de conexión")
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

    fun getAllSites(callback: onApiActionListener<Pair<List<Enterprise>, Boolean>>) {
        db.collection(PATH_SPORT_CENTER).get()
                .addOnSuccessListener {
                    val enterprises = ArrayList<Enterprise>()
                    it.forEach {
                        val e = it.toObject(Enterprise::class.java)
                        e.pk = it.id
                        enterprises.add(e)
                    }
                    callback.onSucces(Pair(enterprises, it.metadata.isFromCache))
                }
                .addOnFailureListener {
                    callback.onError("Posible problema de conexion, intentelo nuevamente")
                }
    }

    fun getHome(callback: RealTimeListener<Publication>) {
        pulistener = db.collection(PATH_PUBLICATIONS).addSnapshotListener { querySnapshot, e ->
            if (e != null) {
                Log.e(TAG, "listen:error", e)
                callback.omError(e)
            }

            if (querySnapshot!!.documentChanges.isNotEmpty()) {

                querySnapshot.documentChanges.forEach {
                    when (it.type) {
                        DocumentChange.Type.ADDED -> {
                            val pu = it.document.toObject(Publication::class.java)
                            pu.pk = it.document.id
                            callback.addDocument(pu)
                        }
                        DocumentChange.Type.MODIFIED -> {
                            val pu = it.document.toObject(Publication::class.java)
                            pu.pk = it.document.id
                            callback.updateDocument(pu)
                        }
                        DocumentChange.Type.REMOVED -> {
                            val pu = it.document.toObject(Publication::class.java)
                            pu.pk = it.document.id
                            callback.removeDocument(pu)
                        }
                    }
                }
            } else {
                callback.emptyNode("No hay publicaciones")
            }

        }

    }

    fun getSitesScore(callback: onApiActionListener<Pair<List<Enterprise>, Boolean>>) {
        db.collection(PATH_SPORT_CENTER).orderBy(PATH_LIKES, Query.Direction.DESCENDING).get()
                .addOnSuccessListener {
                    val enterprises = ArrayList<Enterprise>()
                    it.forEach {
                        val e = it.toObject(Enterprise::class.java)
                        e.pk = it.id
                        enterprises.add(e)
                    }
                    callback.onSucces(Pair(enterprises, it.metadata.isFromCache))
                }
                .addOnFailureListener {
                    callback.onError(ManagerExcepcionFirebase.getMessageErrorFirebaseFirestore(it))
                }

    }

    fun updatePhoto(photo: String, callback: onApiActionListener<String>) {

        val bmp = BitmapFactory.decodeFile(photo)
        val bos = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, 20, bos)
        val data = bos.toByteArray()


        storage.child(STORAGE_USER_PHOTO_PATH).child(mAuth.currentUser!!.uid).putBytes(data)
                .addOnFailureListener {
                    callback.onError(ManagerExcepcionFirebase.getMessageErrorStorage(it))
                }
                .addOnSuccessListener {
                    it.storage.downloadUrl.addOnSuccessListener { uri ->

                        db.collection(PATH_USER).document(getUid()).update("foto", uri.toString()).addOnSuccessListener {

                            val profileUpdate: UserProfileChangeRequest = UserProfileChangeRequest.Builder()
                                    .setPhotoUri(uri).build()

                            mAuth.currentUser!!.updateProfile(profileUpdate)
                                    .addOnSuccessListener { Log.e(TAG, "Se actualizo la foto") }
                                    .addOnFailureListener { Log.e(TAG, it.toString()) }
                            callback.onSucces(uri.toString())

                        }.addOnFailureListener { callback.onError(ManagerExcepcionFirebase.getMessageErrorStorage(it)) }

                    }.addOnFailureListener { callback.onError(ManagerExcepcionFirebase.getMessageErrorFirebaseFirestore(it)) }

                }
    }


    fun removelistener() {
        if (pulistener != null) {
            pulistener!!.remove()
        }
    }

    fun getSitesLocation(parametros: Map<String, Any>, callback: onApiActionListener<List<Enterprise>>) {
        fuctions.getHttpsCallable(PATH_FILTER_DISTANCE_CENTER_SPORT)
                .call(parametros)
                .addOnSuccessListener {

                    Log.e(TAG, "data " + it.data.toString())

                    val listSportCenter = ArrayList<Enterprise>()


                    val data = it.data as ArrayList<HashMap<String, Any>>
                    data.forEach { item ->
                        val entrepise = gson.fromJson(gson.toJson(item), Enterprise::class.java)
                        listSportCenter.add(entrepise)
                        Log.e(TAG, "error ${entrepise.nombre}")
                    }

                    listSportCenter.sortBy {
                        it.distancia
                    }
                    callback.onSucces(listSportCenter)


                }
                .addOnFailureListener {
                    callback.onError("Posible problema de conexión, intentelo nuevamente")

                }

    }

    fun getItemReserved(fecha: String, idEnterprise: String, pkCancha: String, callback: onApiActionListener<List<Reservation>>) {
        db.collection(PATH_SPORT_CENTER).document(idEnterprise).collection(PATH_RESERVATION).whereEqualTo("canchas.id_cancha", pkCancha).whereEqualTo("fecha_reserva", fecha).get()
                .addOnSuccessListener {
                    Log.e(TAG, "item reserva succes")

                    it.documents.forEach {

                    }

                    callback.onSucces(ArrayList<Reservation>())
                }
                .addOnFailureListener {
                    Log.e(TAG, "item reserva error" + it.toString())
                    callback.onError(ManagerExcepcionFirebase.getMessageErrorFirebaseFirestore(it))
                }

    }

    fun getSitesOpen(parametros: HashMap<String, String>, callback: onApiActionListener<List<Enterprise>>) {
        fuctions.getHttpsCallable(PATH_FILTER_OPEN_CENTER_SPORT)
                .call(parametros)
                .addOnSuccessListener {
                    val listSportCenter = ArrayList<Enterprise>()

                    Log.e("open", it.data.toString())

                    var data = it.data as ArrayList<HashMap<String, Any>>
                    data.forEach { item ->
                        val entrepise = gson.fromJson(gson.toJson(item), Enterprise::class.java)
                        listSportCenter.add(entrepise)
                        Log.e(TAG, "nombre_centro_deportivo ${entrepise.nombre}")
                    }

                    callback.onSucces(listSportCenter)

                }
                .addOnFailureListener {
                    callback.onError("Posible problema de conexión, intentelo nuevamente")

                }
    }

    fun getEncuentros(callback: RealTimeListener<ItemCalendar>) {

        db.collection(PATH_ENCUENTROS).addSnapshotListener { querySnapshot, e ->
            if (e != null) {
                Log.e(TAG, "listen:error", e)
                callback.omError(e)
            }

            if (querySnapshot!!.documentChanges.isNotEmpty()) {

                querySnapshot.documentChanges.forEach {
                    when (it.type) {
                        DocumentChange.Type.ADDED -> {
                            val item = it.document.toObject(ItemCalendar::class.java)
                            item.pk = it.document.id
                            callback.addDocument(item)
                        }
                        DocumentChange.Type.MODIFIED -> {
                            val item = it.document.toObject(ItemCalendar::class.java)
                            item.pk = it.document.id
                            callback.updateDocument(item)
                        }
                        DocumentChange.Type.REMOVED -> {
                            val item = it.document.toObject(ItemCalendar::class.java)
                            item.pk = it.document.id
                            callback.removeDocument(item)
                        }
                    }
                }
            } else {
                callback.emptyNode("No hay publicaciones")
            }

        }
    }

    fun getTeams(callback: onApiActionListener<List<Team>>) {

        db.collection(PATH_TEAMS).get()
                .addOnSuccessListener {
                    val teams = ArrayList<Team>()
                    it.forEach {
                        val team = it.toObject(Team::class.java)
                        team.pk = it.id
                        teams.add(team)
                    }
                    callback.onSucces(teams)

                }
                .addOnFailureListener {
                    callback.onError(ManagerExcepcionFirebase.getMessageErrorFirebaseFirestore(it))
                }
    }

}