package com.aitec.sitesport.entities

import android.os.Parcel
import android.os.Parcelable

class ItemReservation(var start: String, var end: String, var state: Boolean, var select: Boolean, var price: Double) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte(),
            parcel.readDouble()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(start)
        parcel.writeString(end)
        parcel.writeByte(if (state) 1 else 0)
        parcel.writeByte(if (select) 1 else 0)
        parcel.writeDouble(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItemReservation> {
        override fun createFromParcel(parcel: Parcel): ItemReservation {
            return ItemReservation(parcel)
        }

        override fun newArray(size: Int): Array<ItemReservation?> {
            return arrayOfNulls(size)
        }
    }


}