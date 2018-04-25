package com.aitec.sitesport.entities.enterprise

import android.os.Parcel
import android.os.Parcelable

class Dia() : Parcelable{

    var nombre: String = ""
    var horas: List<Hora> = arrayListOf()

    constructor(parcel: Parcel) : this() {
        nombre = parcel.readString()
        horas = parcel.createTypedArrayList(Hora)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeTypedList(horas)
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