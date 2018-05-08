package com.aitec.sitesport.entities

import android.os.Parcel
import android.os.Parcelable

class Reservation() : Parcelable {

    var pk: String = ""
    var site: String = ""
    var court: String = ""
    var reservationDate: String = ""
    var gameDate: String = ""
    var type: Int = 0
    var isConsumed: Boolean = false
    var head: String = ""

    constructor(parcel: Parcel) : this() {
        site = parcel.readString()
        pk = parcel.readString()
        court = parcel.readString()
        reservationDate = parcel.readString()
        gameDate = parcel.readString()
        type = parcel.readInt()
        isConsumed = parcel.readByte() != 0.toByte()
        head = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(site)
        parcel.writeString(pk)
        parcel.writeString(court)
        parcel.writeString(reservationDate)
        parcel.writeString(gameDate)
        parcel.writeInt(type)
        parcel.writeByte(if (isConsumed) 1 else 0)
        parcel.writeString(head)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Reservation> {
        override fun createFromParcel(parcel: Parcel): Reservation {
            return Reservation(parcel)
        }

        override fun newArray(size: Int): Array<Reservation?> {
            return arrayOfNulls(size)
        }

        const val RESERVATION = 0
        const val HEAD = 1
    }

}
