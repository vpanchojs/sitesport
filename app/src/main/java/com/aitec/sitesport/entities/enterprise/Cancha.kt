package com.aitec.sitesport.entities.enterprise

import android.os.Parcel
import android.os.Parcelable

class Cancha() : Parcelable{

    var nombre: String = ""
    var dia: Float = 0f
    var noche: Float = 0f
    var numero_jugadores: String = ""
    var piso: String = ""
    var fotos: List<Foto>? = arrayListOf()


    constructor(parcel: Parcel) : this() {
        nombre = parcel.readString()
        dia = parcel.readFloat()
        noche = parcel.readFloat()
        numero_jugadores = parcel.readString()
        piso = parcel.readString()
        fotos = parcel.createTypedArrayList(Foto)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeFloat(dia)
        parcel.writeFloat(noche)
        parcel.writeString(numero_jugadores)
        parcel.writeString(piso)
        parcel.writeTypedList(fotos)
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