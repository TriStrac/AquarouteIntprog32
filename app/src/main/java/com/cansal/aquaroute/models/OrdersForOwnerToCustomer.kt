package com.cansal.aquaroute.models

data class OrdersForOwnerToCustomer(
    val customerName: String,
    val customerEmail: String,
    val orderAmount: Int,
    val customerAddress: String,
    val ownerName: String,
    val ownerEmail: String,
    val orderPickupStatus: String,
    val orderDeliveryStatus: String,
)
