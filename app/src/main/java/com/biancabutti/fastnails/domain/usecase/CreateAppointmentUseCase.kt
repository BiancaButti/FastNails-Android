package com.biancabutti.fastnails.domain.usecase

import com.biancabutti.fastnails.domain.model.Appointment
import com.biancabutti.fastnails.domain.model.AppointmentStatus
import com.biancabutti.fastnails.domain.repository.AppointmentRepository

class CreateAppointmentUseCase(
    private val appointmentRepository: AppointmentRepository
) {
    suspend operator fun invoke(appointment: Appointment): Result<Appointment> {
        if (appointment.clientId.isBlank()) {
            return Result.failure(IllegalArgumentException("Client id must not be blank."))
        }

        if (appointment.status != AppointmentStatus.REQUESTED) {
            return Result.failure(
                IllegalArgumentException("New appointments must start with REQUESTED status.")
            )
        }

        return appointmentRepository.createAppointment(appointment)
    }
}