package com.aitec.sitesport.entities

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

@SuppressLint("ParcelCreator")
class Group {
    var grupo: String = ""
    var genero: String = ""
    var disciplina: String = ""
    var type: Int = GROUP_ITEM
    var teams: ArrayList<Team> = arrayListOf()

    companion object {
        const val TEAM_ITEM = 0
        const val GENDER_ITEM = 1
        const val GROUP_ITEM = 2

    }
}