package com.cansal.aquaroute.models

data class CustomerStock (
    val customerEmail: String,
    val customerTotalJugs: Int,
    val customerFilledJugs: Int,
    val customerJugsInStations: Int,
)

