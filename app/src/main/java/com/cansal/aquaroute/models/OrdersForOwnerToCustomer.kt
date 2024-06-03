package com.cansal.aquaroute.models

data class OrdersForOwnerToCustomer(
    val customerName: String,
    val customerEmail: String,
    val customerNumber: String,
    val orderAmount: String,
    val orderCost: String,
    val customerAddress: String,
    val deliveryDate: String,
    val deliveryTime: String,
    val ownerName: String,
    val ownerEmail: String,
    val orderPickupStatus: String,
    val orderDeliveryStatus: String,
)
