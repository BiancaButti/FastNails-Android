package com.biancabutti.fastnails.domain.model

data class Manicurist(
    val id: String,
    val name: String,
    val serviceTypes: List<ServiceType>,
    val isAvailable: Boolean,
    val serviceArea: String
)