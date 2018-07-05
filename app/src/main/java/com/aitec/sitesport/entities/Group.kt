package com.aitec.sitesport.entities

import android.annotation.SuppressLint
import com.google.firebase.firestore.Exclude
import java.util.*

@SuppressLint("ParcelCreator")
class Group {
    var grupo: String = ""
    var genero: String = ""
    var disciplina: String = ""
    var type: Int = GROUP_ITEM
    var teams: ArrayList<Team> = arrayListOf()

    @Exclude
    lateinit var hashTeams: List<Map<String, Any>>

    companion object {
        const val TEAM_ITEM = 0
        const val GENDER_ITEM = 1
        const val GROUP_ITEM = 2

    }

    @Exclude
    fun toMapPost(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result["grupo"] = grupo
        result["genero"] = genero
        result["disciplina"] = disciplina
        result["teams"] = hashTeams
        return result
    }
}