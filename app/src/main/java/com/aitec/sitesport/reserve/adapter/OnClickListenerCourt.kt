package com.aitec.sitesport.reserve.adapter

import com.aitec.sitesport.entities.Courts
import com.aitec.sitesport.entities.enterprise.Cancha

interface OnClickListenerCourt {
    fun onCheckedCourt(court: Cancha)
}