package com.aitec.sitesport.domain

import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
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


}