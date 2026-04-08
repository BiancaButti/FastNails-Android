package com.biancabutti.fastnails.domain.usecase

import com.biancabutti.fastnails.domain.model.Appointment
import com.biancabutti.fastnails.domain.model.AppointmentStatus
import com.biancabutti.fastnails.domain.model.ServiceType
import com.biancabutti.fastnails.domain.repository.AppointmentRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CreateAppointmentUseCaseTest {
    @Test
    fun returnsFailureWhenClientIdIsBlank() = runTest {
        val repository = FakeAppointmentRepository()
        val useCase = CreateAppointmentUseCase(repository)

        val result = useCase(sampleAppointment(clientId = ""))

        assertTrue(result.isFailure)
        assertEquals(0, repository.createCalls)
    }

    @Test
    fun returnsFailureWhenStatusIsNotRequested() = runTest {
        val repository = FakeAppointmentRepository()
        val useCase = CreateAppointmentUseCase(repository)

        val result = useCase(sampleAppointment(status = AppointmentStatus.CONFIRMED))

        assertTrue(result.isFailure)
        assertEquals(0, repository.createCalls)
    }

    @Test
    fun delegatesCreationWhenAppointmentIsValid() = runTest {
        val appointment = sampleAppointment()
        val repository = FakeAppointmentRepository(result = Result.success(appointment))
        val useCase = CreateAppointmentUseCase(repository)

        val result = useCase(appointment)

        assertTrue(result.isSuccess)
        assertEquals(appointment, result.getOrNull())
        assertEquals(1, repository.createCalls)
        assertEquals(appointment, repository.lastCreatedAppointment)
    }

    @Test
    fun propagatesRepositoryFailure() = runTest {
        val repositoryError = IllegalStateException("Unable to create appointment.")
        val repository = FakeAppointmentRepository(result = Result.failure(repositoryError))
        val useCase = CreateAppointmentUseCase(repository)

        val result = useCase(sampleAppointment())

        assertTrue(result.isFailure)
        assertFalse(result.exceptionOrNull() == null)
        assertEquals(repositoryError, result.exceptionOrNull())
        assertEquals(1, repository.createCalls)
    }

    private fun sampleAppointment(
        clientId: String = "client-1",
        status: AppointmentStatus = AppointmentStatus.REQUESTED
    ) = Appointment(
        id = "appointment-1",
        clientId = clientId,
        manicuristId = "manicurist-1",
        serviceType = ServiceType.HANDS,
        status = status,
        scheduledAt = "2026-04-08T10:00:00Z"
    )

    private class FakeAppointmentRepository(
        private val result: Result<Appointment> = Result.success(sampleRepositoryAppointment())
    ) : AppointmentRepository {
        var createCalls = 0
            private set

        var lastCreatedAppointment: Appointment? = null
            private set

        override suspend fun createAppointment(appointment: Appointment): Result<Appointment> {
            createCalls += 1
            lastCreatedAppointment = appointment
            return result
        }

        override suspend fun getAppointmentById(appointmentId: String): Result<Appointment> {
            throw UnsupportedOperationException("Not needed for this test.")
        }

        override suspend fun getAppointmentsByClient(clientId: String): Result<List<Appointment>> {
            throw UnsupportedOperationException("Not needed for this test.")
        }

        companion object {
            private fun sampleRepositoryAppointment() = Appointment(
                id = "appointment-1",
                clientId = "client-1",
                manicuristId = "manicurist-1",
                serviceType = ServiceType.HANDS,
                status = AppointmentStatus.REQUESTED,
                scheduledAt = "2026-04-08T10:00:00Z"
            )
        }
    }
}