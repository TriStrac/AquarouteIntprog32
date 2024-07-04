package com.cansal.aquaroute.models

data class LoginRequest (
    val email:String,
    val password:String,
)

data class LoginResponse(
    val additionalProp1: String,
    val additionalProp2: String,
    val additionalProp3: String
)