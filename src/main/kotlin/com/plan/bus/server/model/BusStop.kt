package com.plan.bus.server.model

import javax.persistence.*

/**
 * Created by Michal on 2016-08-31.
 */
@Entity
@Table(name = "BPS_STOPS")
data class BusStop(@Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name = "STOP_ID") val id: Long? = null,
                   @Column(name = "STOP_NAME") val name: String = "",
                   @Column(name = "STOP_SYMBOL") val symbol: String = "",
                   @Column(name = "STOP_FIRST_LETTER") val firstLetter: String = "") {
}