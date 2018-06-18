package com.aitec.sitesport.entities.enterprise

import android.os.Parcel
import android.os.Parcelable
import com.aitec.sitesport.entities.Address

class Enterprise() : Parcelable {

    // OBJETO INICIAL
    lateinit var pk: String
    lateinit var nombres: String
    var foto_perfil: String = ""
    var direccion: Address? = null
    var me_gusta: Boolean = false
    var puntuacion: Int = 0
    var distance: Float = 0f
    var abierto: Boolean = false
    var idMarker: String = ""

    // OBJETO URL DETALLE
    var descripcion: String = ""
    var fotos: List<Foto> = arrayListOf()
    var telefono: String = ""
    var canchas: ArrayList<Cancha> = arrayListOf()
    var servicios: ArrayList<Servicio> = arrayListOf()
    var redesSociales: ArrayList<RedSocial> = arrayListOf()
    var horario: List<Dia> = arrayListOf()

    var likes: Int = 0
    var isOnline: Boolean = true

    var isQualified = false

    constructor(parcel: Parcel) : this() {
        pk = parcel.readString()
        nombres = parcel.readString()
        foto_perfil = parcel.readString()
        me_gusta = parcel.readByte() != 0.toByte()
        puntuacion = parcel.readInt()
        distance = parcel.readFloat()
        abierto = parcel.readByte() != 0.toByte()
        isOnline = parcel.readByte() != 0.toByte()
        telefono = parcel.readString()
        idMarker = parcel.readString()
        parcel.readList(horario, Dia::class.java.classLoader)
        parcel.readList(canchas, Cancha::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(pk)
        parcel.writeString(nombres)
        parcel.writeString(foto_perfil)
        parcel.writeByte(if (me_gusta) 1 else 0)
        parcel.writeInt(puntuacion)
        parcel.writeFloat(distance)
        parcel.writeByte(if (abierto) 1 else 0)
        parcel.writeByte(if (isOnline) 1 else 0)
        parcel.writeString(telefono)
        parcel.writeString(idMarker)
        parcel.writeList(horario)
        parcel.writeList(canchas)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Enterprise> {

        override fun createFromParcel(parcel: Parcel): Enterprise {
            return Enterprise(parcel)
        }

        override fun newArray(size: Int): Array<Enterprise?> {
            return arrayOfNulls(size)
        }
    }


}
