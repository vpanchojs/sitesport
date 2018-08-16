package com.aitec.sitesport.entities.enterprise

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import java.util.HashMap

class Cancha() : Parcelable {

    var pk: String = ""
    var nombre: String = ""
    var precio_dia: Float = 0f
    var precio_noche: Float = 0f
    var numero_jugadores: String = ""
    var hora_intermedia: Int = 0
    var piso: String = ""
    //var fotos: ArrayList<String>? = arrayListOf()
    var fotos: String = ""


    constructor(parcel: Parcel) : this() {
        pk = parcel.readString()
        nombre = parcel.readString()
        precio_dia = parcel.readFloat()
        precio_noche = parcel.readFloat()
        numero_jugadores = parcel.readString()
        piso = parcel.readString()
        hora_intermedia = parcel.readInt()
        fotos = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(pk)
        parcel.writeString(nombre)
        parcel.writeFloat(precio_dia)
        parcel.writeFloat(precio_noche)
        parcel.writeString(numero_jugadores)
        parcel.writeString(piso)
        parcel.writeInt(hora_intermedia)
        parcel.writeString(fotos)
    }


    @Exclude
    fun toMapPostReservation(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result["nombre"] = nombre
        result["pk"] = pk
        return result
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cancha> {
        override fun createFromParcel(parcel: Parcel): Cancha {
            return Cancha(parcel)
        }

        override fun newArray(size: Int): Array<Cancha?> {
            return arrayOfNulls(size)
        }
    }

}