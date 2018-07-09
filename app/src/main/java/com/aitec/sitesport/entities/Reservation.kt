package com.aitec.sitesport.entities

import android.os.Parcel
import android.os.Parcelable
import com.aitec.sitesport.entities.enterprise.Cancha
import com.aitec.sitesport.entities.enterprise.Enterprise
import java.util.*

class Reservation() : Parcelable {

    var pk: String = ""
    var fecha_reserva: String = ""
    var hora_reserva: Int = 0
    var horas_juego: Int = 0
    var estado: String = ""
    var observaciones = ""
    var fecha_registro: Date? = null
    var cliente: User? = null
    var cancha: Cancha? = null
    var precio: Double = 0.0
    var centro_deportivo: Enterprise? = null

    constructor(parcel: Parcel) : this() {
        pk = parcel.readString()
        fecha_reserva = parcel.readString()
        hora_reserva = parcel.readInt()
        horas_juego = parcel.readInt()
        estado = parcel.readString()
        observaciones = parcel.readString()
        cliente = parcel.readParcelable(User::class.java.classLoader)
        cancha = parcel.readParcelable(Cancha::class.java.classLoader)
        precio = parcel.readDouble()
        centro_deportivo = parcel.readParcelable(Enterprise::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(pk)
        parcel.writeString(fecha_reserva)
        parcel.writeInt(hora_reserva)
        parcel.writeInt(horas_juego)
        parcel.writeString(estado)
        parcel.writeString(observaciones)
        parcel.writeParcelable(cliente, flags)
        parcel.writeParcelable(cancha, flags)
        parcel.writeDouble(precio)
        parcel.writeParcelable(centro_deportivo, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Reservation> {
        const val RESERVATION = 0
        const val HEAD = 1
        override fun createFromParcel(parcel: Parcel): Reservation {
            return Reservation(parcel)
        }

        override fun newArray(size: Int): Array<Reservation?> {
            return arrayOfNulls(size)
        }
    }


}
