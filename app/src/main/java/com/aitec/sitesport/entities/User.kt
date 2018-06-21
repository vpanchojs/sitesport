package com.aitec.sitesport.entities

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import java.util.*

class User() : Parcelable {
    var pk: String? = null
    var names: String? = null
    var lastName: String = ""
    var photo: String = ""
    var phone: String = ""
    var email: String = ""
    var dni: String? = ""

    constructor(parcel: Parcel) : this() {
        pk = parcel.readString()
        names = parcel.readString()
        lastName = parcel.readString()
        photo = parcel.readString()
        phone = parcel.readString()
        email = parcel.readString()
        dni = parcel.readString()
    }


    @Exclude
    fun toMapPost(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result["names"] = names!!
        result["lastName"] = lastName
        result["photo"] = photo!!
        result["dni"] = dni!!
        result["phone"] = phone!!
        return result
    }

    @Exclude
    fun toMapPostSave(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result["names"] = names!!
        result["lastName"] = lastName
        result["photo"] = photo!!
        result["email"] = email!!
        return result
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(pk)
        parcel.writeString(names)
        parcel.writeString(lastName)
        parcel.writeString(photo)
        parcel.writeString(phone)
        parcel.writeString(email)
        parcel.writeString(dni)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}