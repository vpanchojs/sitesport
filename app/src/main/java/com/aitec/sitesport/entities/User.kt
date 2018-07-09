package com.aitec.sitesport.entities

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import java.util.*

class User() : Parcelable {
    var pk: String? = null
    var nombre: String? = null
    var apellido: String = ""
    var foto: String = ""
    var telefono: String = ""
    var correo_electronico: String = ""
    var cedula: String = ""
    var valido = false
    var numero_reservaciones: Int = 0

    constructor(parcel: Parcel) : this() {
        pk = parcel.readString()
        nombre = parcel.readString()
        apellido = parcel.readString()
        foto = parcel.readString()
        telefono = parcel.readString()
        correo_electronico = parcel.readString()
        cedula = parcel.readString()
    }


    @Exclude
    fun toMapPostReservation(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result["nombre"] = nombre!!
        result["telefono"] = telefono
        result["cedula"] = cedula
        return result
    }


    @Exclude
    fun toMapPost(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result["nombre"] = nombre!!
        result["apellido"] = apellido
        result["foto"] = foto
        result["cedula"] = cedula
        result["telefono"] = telefono
        return result
    }

    @Exclude
    fun toMapPostSave(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result["nombre"] = nombre!!
        result["apellido"] = apellido
        result["foto"] = foto
        result["correo_electronico"] = correo_electronico
        return result
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(pk)
        parcel.writeString(nombre)
        parcel.writeString(apellido)
        parcel.writeString(foto)
        parcel.writeString(telefono)
        parcel.writeString(correo_electronico)
        parcel.writeString(cedula)
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