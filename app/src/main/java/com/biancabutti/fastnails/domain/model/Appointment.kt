package com.biancabutti.fastnails.domain.model

data class Appointment(
    val id: String,
    val clientId: String,
    val manicuristId: String?,
    val serviceType: ServiceType,
    val status: AppointmentStatus,
    val scheduledAt: String?
)

enum class ServiceType {
    HANDS,
    FEET
}

enum class AppointmentStatus {
    REQUESTED,
    CONFIRMED,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED
}