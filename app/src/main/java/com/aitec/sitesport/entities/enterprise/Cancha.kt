package com.aitec.sitesport.entities.enterprise

import android.os.Parcel
import android.os.Parcelable
import com.aitec.sitesport.entities.Rate

class Cancha() : Parcelable{

    var pk: String = ""
    var nombre: String = ""
    var precio_dia: Float = 0f
    var precio_noche: Float = 0f
    var numero_jugadores: String = ""
    var piso: String = ""
    var hora_intermedia: String = ""
    var fotos: ArrayList<String>? = arrayListOf()


    constructor(parcel: Parcel) : this() {
        pk = parcel.readString()
        nombre = parcel.readString()
        precio_dia = parcel.readFloat()
        precio_noche = parcel.readFloat()
        numero_jugadores = parcel.readString()
        piso = parcel.readString()
        hora_intermedia = parcel.readString()
        parcel.readList(fotos, String::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(pk)
        parcel.writeString(nombre)
        parcel.writeFloat(precio_dia)
        parcel.writeFloat(precio_noche)
        parcel.writeString(numero_jugadores)
        parcel.writeString(piso)
        parcel.writeString(hora_intermedia)
        parcel.writeList(fotos)
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