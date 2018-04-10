package com.aitec.sitesport.entities.enterprise

import android.os.Parcel
import android.os.Parcelable
import com.aitec.sitesport.entities.Address

class Enterprise() : Parcelable {

    lateinit var pk: String
    lateinit var nombres: String
    lateinit var address: Address
    lateinit var urldetalle: String
    lateinit var foto_perfil: String
    var idMarker: Long = 0


    var descripcion: String = ""
    var abierto: Boolean = false
    var numero_canchas: String = "0"
    var likes: Int = 0
    var fotos: List<Fotos>? = arrayListOf()
    var telefonos: List<Telefonos>? = arrayListOf()
    var red_social: List<RedSocial>? = arrayListOf()
    //var categoria: List<Categoria>? = arrayListOf()
    var precio: List<Precio>? = arrayListOf()
    //var horario: List<Horario>? = arrayListOf()
    var hora: List<Hora>? = arrayListOf()

    constructor(parcel: Parcel) : this() {
        pk = parcel.readString()
        nombres = parcel.readString()
        address = parcel.readParcelable(Address::class.java.classLoader)
        urldetalle = parcel.readString()
        foto_perfil = parcel.readString()
        descripcion = parcel.readString()
        abierto = if (parcel.readInt() == 0) false else true
        numero_canchas = parcel.readString()
        likes = parcel.readInt()
        parcel.readList(fotos, Fotos::class.java.classLoader)
        parcel.readList(telefonos, Telefonos::class.java.classLoader)
        parcel.readList(red_social, RedSocial::class.java.classLoader)
        //parcel.readList(categoria, Categoria::class.java.classLoader)
        parcel.readList(precio, Precio::class.java.classLoader)
        //parcel.readList(horario, Horario::class.java.classLoader)
        parcel.readList(hora, Hora::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(pk)
        parcel.writeString(nombres)
        parcel.writeParcelable(address, flags)
        parcel.writeString(urldetalle)
        parcel.writeString(foto_perfil)
        parcel.writeString(descripcion)
        parcel.writeValue(abierto)
        parcel.writeString(numero_canchas)
        parcel.writeInt(likes)
        parcel.writeList(fotos)
        parcel.writeList(telefonos)
        parcel.writeList(red_social)
        //parcel.writeList(categoria)
        parcel.writeList(precio)
        //parcel.writeList(horario)
        parcel.writeList(hora)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Enterprise> {

        //val DEFAULT = Enterprise("James", 40)

        override fun createFromParcel(parcel: Parcel): Enterprise {
            return Enterprise(parcel)
        }

        override fun newArray(size: Int): Array<Enterprise?> {
            return arrayOfNulls(size)
        }
    }


}
