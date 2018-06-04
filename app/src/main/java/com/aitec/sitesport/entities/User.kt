package com.aitec.sitesport.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import java.util.*

@Entity(tableName = "user")
class User() : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var pk: String? = null
    var names: String? = null
    @ColumnInfo(name = "last_name")
    var lastName: String? = null
    var photo: String? = null
    var phone: String? = null
    var email: String? = null
    var dni: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
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
        result["dni"] = dni!!
        result["phone"] = phone!!
        return result
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
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