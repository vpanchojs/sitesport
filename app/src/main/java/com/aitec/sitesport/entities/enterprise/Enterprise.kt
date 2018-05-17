package com.aitec.sitesport.entities.enterprise

import android.os.Parcel
import android.os.Parcelable
import com.aitec.sitesport.entities.Address

class Enterprise() : Parcelable {

    // OBJETO INICIAL
    lateinit var pk: String
    lateinit var nombres: String
    lateinit var urldetalle: String
    var servicios: Servicios = Servicios()
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
    //var telefonos: List<Telefono> = arrayListOf()
    var telefono: String = ""
    var numero_canchas: String = "0"
    var redes_sociales: List<RedSocial> = arrayListOf()
    var canchas: List<Cancha> = arrayListOf()
    var horario: List<Horario> = arrayListOf()

    /*var abierto: Boolean = false
    var likes: Int = 0
    //var categoria: List<Categoria>? = arrayListOf()
    //var horario: List<Horario>? = arrayListOf()
    var hora: List<Hora>? = arrayListOf()*/

    constructor(parcel: Parcel) : this() {
        pk = parcel.readString()
        nombres = parcel.readString()
        urldetalle = parcel.readString()
        servicios = parcel.readParcelable(Servicios::class.java.classLoader)
        foto_perfil = parcel.readString()
        direccion = parcel.readParcelable(Address::class.java.classLoader)
        me_gusta = parcel.readByte() != 0.toByte()
        puntuacion = parcel.readInt()
        distance = parcel.readFloat()
        abierto = parcel.readByte() != 0.toByte()
        telefono = parcel.readString()
        idMarker = parcel.readString()
        parcel.readList(redes_sociales, RedSocial::class.java.classLoader)
        parcel.readList(canchas, Cancha::class.java.classLoader)
        parcel.readList(horario, Horario::class.java.classLoader)

        /*descripcion = parcel.readString()
        abierto = if (parcel.readInt() == 0) false else true
        numero_canchas = parcel.readString()
        likes = parcel.readInt()
        parcel.readList(fotos, Foto::class.java.classLoader)
        parcel.readList(telefonos, Telefono::class.java.classLoader)*/
        //parcel.readList(categoria, Categoria::class.java.classLoader)
        //parcel.readList(precio, Precio::class.java.classLoader)
        //parcel.readList(horario, Horario::class.java.classLoader)
        // parcel.readList(hora, Hora::class.java.classLoader)*/
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(pk)
        parcel.writeString(nombres)
        parcel.writeString(urldetalle)
        parcel.writeParcelable(servicios, flags)
        parcel.writeString(foto_perfil)
        parcel.writeParcelable(direccion, flags)
        parcel.writeByte(if (me_gusta) 1 else 0)
        parcel.writeInt(puntuacion)
        parcel.writeFloat(distance)
        parcel.writeByte(if (abierto) 1 else 0)
        parcel.writeString(telefono)
        parcel.writeString(idMarker)
        parcel.writeList(redes_sociales)
        parcel.writeList(canchas)
        parcel.writeList(horario)

        /*parcel.writeString(descripcion)
        parcel.writeValue(abierto)
        parcel.writeString(numero_canchas)
        parcel.writeInt(likes)
        parcel.writeList(fotos)
        parcel.writeList(telefonos)*/
        //parcel.writeList(categoria)
        // parcel.writeList(precio)
        //parcel.writeList(horario)
        //parcel.writeList(hora)*/
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
