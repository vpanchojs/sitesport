package com.aitec.sitesport.entities.enterprise

import android.os.Parcel
import android.os.Parcelable

class Horario() : Parcelable {

    var nombre: String = ""
    var es_general: Boolean = false
    var pk: String = ""
    var dias: List<Dia> = arrayListOf()

    constructor(parcel: Parcel) : this() {
        nombre = parcel.readString()
        es_general = parcel.readByte() != 0.toByte()
        pk = parcel.readString()
        dias = parcel.createTypedArrayList(Dia)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeByte(if (es_general) 1 else 0)
        parcel.writeString(pk)
        parcel.writeTypedList(dias)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Horario> {
        override fun createFromParcel(parcel: Parcel): Horario {
            return Horario(parcel)
        }

        override fun newArray(size: Int): Array<Horario?> {
            return arrayOfNulls(size)
        }
    }

}