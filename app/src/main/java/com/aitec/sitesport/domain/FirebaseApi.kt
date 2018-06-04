package com.aitec.sitesport.domain

import android.net.Uri
import android.util.Log
import com.aitec.sitesport.domain.listeners.onApiActionListener
import com.aitec.sitesport.entities.User
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference


class FirebaseApi(var db: FirebaseFirestore, var mAuth: FirebaseAuth, var storage: StorageReference) {

    companion object {
        const val TAG = "FirebaseApi"
        const val PATH_USER = "users"

    }

    var mAuthListener: FirebaseAuth.AuthStateListener? = null

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

}