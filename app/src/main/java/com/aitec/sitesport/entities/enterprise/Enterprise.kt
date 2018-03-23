package com.aitec.sitesport.entities.enterprise

import android.os.Parcel
import android.os.Parcelable

class Enterprise() : Parcelable {

    lateinit var pk: String
    lateinit var nombres: String
    var latitud: Double = 0.0
    var longitud: Double = 0.0
    var distancia: Double = 0.0
    var idMarker: Long = 0L
    var descripcion : String = ""
    var abierto: Boolean = false
    var fotos: List<Fotos>? = null
    var telefonos: List<Telefonos>? = null
    var red_social: List<RedSocial>? = null
    var categoria: List<Categoria>? = null
    var precio: List<Precio>? = null
    var horario: List<Horario>? = null
    var hora: List<Hora>? = null

constructor(parcel: Parcel) : this() {
        pk = parcel.readString()
        nombres = parcel.readString()
        latitud = parcel.readDouble()
        longitud = parcel.readDouble()
        distancia = parcel.readDouble()
        idMarker = parcel.readLong()
        descripcion = parcel.readString()
        abierto = if (parcel.readInt() == 0) false else true;
        parcel.readList(fotos, Fotos::class.java.classLoader)
        parcel.readList(telefonos, Telefonos::class.java.classLoader)
        parcel.readList(red_social, RedSocial::class.java.classLoader)
        parcel.readList(categoria, Categoria::class.java.classLoader)
        parcel.readList(precio, Precio::class.java.classLoader)
        parcel.readList(horario, Horario::class.java.classLoader)
        parcel.readList(hora, Hora::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(pk)
        parcel.writeString(nombres)
        parcel.writeDouble(latitud)
        parcel.writeDouble(longitud)
        parcel.writeDouble(distancia)
        parcel.writeLong(idMarker)
        parcel.writeString(descripcion)
        parcel.writeValue(abierto)
        parcel.writeList(fotos)
        parcel.writeList(telefonos)
        parcel.writeList(red_social)
        parcel.writeList(categoria)
        parcel.writeList(precio)
        parcel.writeList(horario)
        parcel.writeList(hora)
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
