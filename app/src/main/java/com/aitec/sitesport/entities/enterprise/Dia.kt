package com.aitec.sitesport.entities.enterprise

import android.os.Parcel
import android.os.Parcelable

class Dia() : Parcelable {

    var pk: String = ""
    var nombre: String = ""
    var hora_inicio: Int = 0
    var hora_fin: Int = 0
    var indice: Int = -1

    constructor(parcel: Parcel) : this() {
        pk = parcel.readString()
        nombre = parcel.readString()
        hora_inicio = parcel.readInt()
        hora_fin = parcel.readInt()
        indice = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(pk)
        parcel.writeString(nombre)
        parcel.writeInt(hora_inicio)
        parcel.writeInt(hora_fin)
        parcel.writeInt(indice)
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