package com.aitec.sitesport.entities.enterprise

import android.os.Parcel
import android.os.Parcelable
import com.aitec.sitesport.entities.Direccion

class Enterprise() : Parcelable {

    // atributos
    lateinit var pk: String
    lateinit var nombre: String
    var descripcion: String = ""
    var foto_perfil: String = ""
    var telefono: String = ""
    var activo: Boolean = false
    var me_gustas: Int = 0
    var direccion: Direccion? = null

    // colecciones
    var horarios: List<Dia> = arrayListOf()
    var canchas: ArrayList<Cancha> = arrayListOf()
    var redes_social: ArrayList<RedSocial> = arrayListOf()
    var servicios: ArrayList<Servicio> = arrayListOf()

    // variables de uso local
    var distancia: Float = 0f
    var abierto: Boolean = false
    var idMarker: String = ""
    var isOnline: Boolean = true
    var isQualified = false
    var viewType: Int = 0

    constructor(parcel: Parcel) : this() {
        pk = parcel.readString()
        nombre = parcel.readString()
        foto_perfil = parcel.readString()
        distancia = parcel.readFloat()
        abierto = parcel.readByte() != 0.toByte()
        isOnline = parcel.readByte() != 0.toByte()
        telefono = parcel.readString()
        idMarker = parcel.readString()
        parcel.readList(horarios, Dia::class.java.classLoader)
        parcel.readList(canchas, Cancha::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(pk)
        parcel.writeString(nombre)
        parcel.writeString(foto_perfil)
        parcel.writeFloat(distancia)
        parcel.writeByte(if (abierto) 1 else 0)
        parcel.writeByte(if (isOnline) 1 else 0)
        parcel.writeString(telefono)
        parcel.writeString(idMarker)
        parcel.writeList(horarios)
        parcel.writeList(canchas)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Enterprise> {
        const val TYPE_ENTREPISE = 0
        const val TYPE_SPACE = 1

        override fun createFromParcel(parcel: Parcel): Enterprise {
            return Enterprise(parcel)
        }

        override fun newArray(size: Int): Array<Enterprise?> {
            return arrayOfNulls(size)
        }
    }


}
