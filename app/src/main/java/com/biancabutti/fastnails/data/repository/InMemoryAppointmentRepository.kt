package com.biancabutti.fastnails.data.repository

import com.biancabutti.fastnails.domain.model.Appointment
import com.biancabutti.fastnails.domain.repository.AppointmentRepository

class InMemoryAppointmentRepository : AppointmentRepository {

    private val appointments = mutableListOf<Appointment>()

    override suspend fun createAppointment(appointment: Appointment): Result<Appointment> {
        appointments.add(appointment)
        return Result.success(appointment)
    }

    override suspend fun getAppointmentById(appointmentId: String): Result<Appointment> {
        val appointment = appointments.find { it.id == appointmentId }
        return if (appointment != null) {
            Result.success(appointment)
        } else {
            Result.failure(NoSuchElementException("Appointment not found: $appointmentId"))
        }
    }

    override suspend fun getAppointmentsByClient(clientId: String): Result<List<Appointment>> {
        val clientAppointments = appointments.filter { it.clientId == clientId }
        return Result.success(clientAppointments)
    }
}
