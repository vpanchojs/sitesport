package com.aitec.sitesport.entities

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import java.util.*

@SuppressLint("ParcelCreator")
class Team() : Parcelable {
    var pk = ""
    var foto: String = ""
    var descripcion: String = ""
    var nombre: String = "equipo"
    var deportes: List<Sport>? = null
    var jugadores: List<String> = arrayListOf()
    var grupo: String = ""
    var marcador: Int = 0

    var pj: Int = 0
    var pu: Int = 0
    var dif: Int = 0


    @Exclude
    fun toMapPost(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result["pj"] = pj
        result["pu"] = pu
        result["dif"] = dif
        result["nombre"] = nombre
        result["pk"] = pk

        return result
    }


    constructor(parcel: Parcel) : this() {
        pk = parcel.readString()
        foto = parcel.readString()
        descripcion = parcel.readString()
        nombre = parcel.readString()
        jugadores = parcel.createStringArrayList()
        grupo = parcel.readString()
        marcador = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(pk)
        parcel.writeString(foto)
        parcel.writeString(descripcion)
        parcel.writeString(nombre)
        parcel.writeStringList(jugadores)
        parcel.writeString(grupo)
        parcel.writeInt(marcador)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Team> {

        override fun createFromParcel(parcel: Parcel): Team {
            return Team(parcel)
        }

        override fun newArray(size: Int): Array<Team?> {
            return arrayOfNulls(size)
        }
    }
}