package com.aitec.sitesport.entities

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Yavac on 13/3/2018.
 */
class TableTime(var lunes : String,
                var martes : String,
                var miercoles : String,
                var jueves : String,
                var vieres : String,
                var sabado : String,
                var domingo : String) : Parcelable{

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(lunes)
        parcel.writeString(martes)
        parcel.writeString(miercoles)
        parcel.writeString(jueves)
        parcel.writeString(vieres)
        parcel.writeString(sabado)
        parcel.writeString(domingo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TableTime> {
        override fun createFromParcel(parcel: Parcel): TableTime {
            return TableTime(parcel)
        }

        override fun newArray(size: Int): Array<TableTime?> {
            return arrayOfNulls(size)
        }
    }

}