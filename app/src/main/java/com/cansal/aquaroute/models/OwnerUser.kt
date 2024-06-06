package com.cansal.aquaroute.models

data class OwnerUser(
    val email: String? = "",
    val password: String? = "",
    val stationName: String? = "",
    val region: String? = "",
    val street: String? = "",
    val unit: String? = "",
    val phone: String? = "",
    val name: String? = "",
    val type: String? = ""
)
