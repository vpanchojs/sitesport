package com.aitec.sitesport.entities

import android.os.Parcel
import android.os.Parcelable
import com.aitec.sitesport.entities.enterprise.Cancha
import com.aitec.sitesport.entities.enterprise.Enterprise
import com.google.firebase.firestore.Exclude
import java.util.*

class Reservation() : Parcelable {

    var pk: String = ""
    var fecha_reserva: String = ""
    var hora_reserva: Int = 0
    var horas_juego: Int = 0
    var estado: Int = -1
    var observaciones = ""
    var fecha_registro: Date? = null
    var cliente: User? = null
    var cancha: Cancha? = null
    var precio: Double = 0.0
    var centro_deportivo: Enterprise? = null
    var type: Int= 0


    @Exclude
    fun toMapPost(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result["fecha_reserva"] = fecha_reserva
        result["hora_reserva"] = hora_reserva
        result["horas_juego"] = horas_juego
        result["estado"] = estado
        result["observaciones"] = observaciones
        result["fecha_registro"] = fecha_registro!!
        result["precio"] = precio
        result["centro_deportivo"] = centro_deportivo!!.toMapPostReservation()
        result["cliente"] = cliente!!.toMapPostReservation()
        result["cancha"] = cancha!!.toMapPostReservation()
        return result
    }

    @Exclude
    fun toMapPostSportCenter(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result["fecha_reserva"] = fecha_reserva
        result["hora_reserva"] = hora_reserva
        result["horas_juego"] = horas_juego
        result["estado"] = estado
        result["observaciones"] = observaciones
        return result
    }

    @Exclude
    fun toMapPostUser(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result["fecha_reserva"] = fecha_reserva
        result["hora_reserva"] = hora_reserva
        result["horas_juego"] = horas_juego
        result["precio"] = precio
        result["estado"] = estado
        result["observaciones"] = observaciones
        result["centro_deportivo"] = centro_deportivo!!.toMapPostReservation()
        result["cancha"] = cancha!!.toMapPostReservation()
        return result
    }


    constructor(parcel: Parcel) : this() {
        pk = parcel.readString()
        fecha_reserva = parcel.readString()
        hora_reserva = parcel.readInt()
        horas_juego = parcel.readInt()
        estado = parcel.readInt()
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
        parcel.writeInt(estado)
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

        const val OCUPADO = 2
        const val DISPONIBLE = 3
        const val PAGADO = 4
        const val NO_PAGADO = 5
    }


}
