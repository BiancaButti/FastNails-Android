package com.biancabutti.fastnails.domain.repository

import com.biancabutti.fastnails.domain.model.Appointment

interface AppointmentRepository {
    suspend fun createAppointment(appointment: Appointment): Result<Appointment>

    suspend fun getAppointmentById(appointmentId: String): Result<Appointment>

    suspend fun getAppointmentsByClient(clientId: String): Result<List<Appointment>>
}