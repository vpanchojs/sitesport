package com.aitec.sitesport.entities

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Jhony on 18 may 2018.
 */
class Publication() : Parcelable{
    var pk: String = ""
    var nombre_centro_deportivo: String = ""
    var pk_centro_deportivo: String = ""
    var foto: String = ""
    var icon: String = ""
    var titulo: String = ""
    var fecha: String = ""
    var descripcion: String = ""
    var type: Int = LINK_PUBLICATION_EVENT
    var isOnline: Boolean = true

    constructor(parcel: Parcel) : this() {
        pk = parcel.readString()
        nombre_centro_deportivo = parcel.readString()
        pk_centro_deportivo = parcel.readString()
        foto = parcel.readString()
        icon = parcel.readString()
        titulo = parcel.readString()
        fecha = parcel.readString()
        descripcion = parcel.readString()
        type = parcel.readInt()
        isOnline = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(pk)
        parcel.writeString(nombre_centro_deportivo)
        parcel.writeString(pk_centro_deportivo)
        parcel.writeString(foto)
        parcel.writeString(icon)
        parcel.writeString(titulo)
        parcel.writeString(fecha)
        parcel.writeString(descripcion)
        parcel.writeInt(type)
        parcel.writeByte(if (isOnline) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Publication> {
        override fun createFromParcel(parcel: Parcel): Publication {
            return Publication(parcel)
        }

        override fun newArray(size: Int): Array<Publication?> {
            return arrayOfNulls(size)
        }

        const val LINK_PUBLICATION_ENTERPRISE = 0
        const val LINK_PUBLICATION_EVENT = 1
        const val LINK_PUBLICATION_PROMO = 2
        const val PUBLICATION = "publication"

    }


}