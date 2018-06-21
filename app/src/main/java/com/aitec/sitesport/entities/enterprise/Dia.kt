package com.aitec.sitesport.entities.enterprise

import android.os.Parcel
import android.os.Parcelable

class Dia() : Parcelable {

    var pk: String = ""
    var nombre: String = ""
    var hora_inicio: String = ""
    var hora_fin: String = ""
    var numero: Int = -1

    constructor(parcel: Parcel) : this() {
        pk = parcel.readString()
        nombre = parcel.readString()
        hora_inicio = parcel.readString()
        hora_fin = parcel.readString()
        numero = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(pk)
        parcel.writeString(nombre)
        parcel.writeString(hora_inicio)
        parcel.writeString(hora_fin)
        parcel.writeInt(numero)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Dia> {
        override fun createFromParcel(parcel: Parcel): Dia {
            return Dia(parcel)
        }

        override fun newArray(size: Int): Array<Dia?> {
            return arrayOfNulls(size)
        }
    }


}