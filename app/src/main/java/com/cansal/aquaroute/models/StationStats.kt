package com.cansal.aquaroute.models

data class StationStats(
    val ownerEmail: String,
    val ordersPending: Int,
    val ordersCompleted: Int,
    val ownerJugsInStock: Int,
)
