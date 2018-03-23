package com.aitec.sitesport.entities

import com.aitec.sitesport.entities.enterprise.Enterprise

/**
 * Created by victor on 20/3/18.
 */
class SearchCentersName {
    var count: Int = 0
    lateinit var results: List<Enterprise>
    var previous: Int = 0
    var next: Int = 0

}