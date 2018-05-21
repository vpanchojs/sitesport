package com.aitec.sitesport.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "user")
class User() {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var pk: String? = null
    var names: String? = null
    @ColumnInfo(name = "last_name")
    var lastName: String? = null
    var photo: String? = null
    var phone: String? = null
    var email: String? = null
    var dni: String? = null
}