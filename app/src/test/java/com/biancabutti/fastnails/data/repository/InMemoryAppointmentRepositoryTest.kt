package com.biancabutti.fastnails.data.repository

import com.biancabutti.fastnails.domain.model.Appointment
import com.biancabutti.fastnails.domain.model.AppointmentStatus
import com.biancabutti.fastnails.domain.model.ServiceType
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class InMemoryAppointmentRepositoryTest {

    private lateinit var repository: InMemoryAppointmentRepository

    @Before
    fun setUp() {
        repository = InMemoryAppointmentRepository()
    }

    @Test
    fun createAppointmentReturnsCreatedAppointment() = runTest {
        val appointment = sampleAppointment()

        val result = repository.createAppointment(appointment)

        assertTrue(result.isSuccess)
        assertEquals(appointment, result.getOrNull())
    }

    @Test
    fun getAppointmentByIdReturnsAppointmentWhenItExists() = runTest {
        val appointment = sampleAppointment(id = "appointment-1")
        repository.createAppointment(appointment)

        val result = repository.getAppointmentById("appointment-1")

        assertTrue(result.isSuccess)
        assertEquals(appointment, result.getOrNull())
    }

    @Test
    fun getAppointmentByIdReturnsFailureWhenNotFound() = runTest {
        val result = repository.getAppointmentById("non-existent-id")

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is NoSuchElementException)
    }

    @Test
    fun getAppointmentsByClientReturnsOnlyMatchingAppointments() = runTest {
        val appointmentForClient1 = sampleAppointment(id = "appointment-1", clientId = "client-1")
        val anotherAppointmentForClient1 = sampleAppointment(id = "appointment-2", clientId = "client-1")
        val appointmentForClient2 = sampleAppointment(id = "appointment-3", clientId = "client-2")
        repository.createAppointment(appointmentForClient1)
        repository.createAppointment(anotherAppointmentForClient1)
        repository.createAppointment(appointmentForClient2)

        val result = repository.getAppointmentsByClient("client-1")

        assertTrue(result.isSuccess)
        assertEquals(listOf(appointmentForClient1, anotherAppointmentForClient1), result.getOrNull())
    }

    @Test
    fun getAppointmentsByClientReturnsEmptyListWhenNoAppointmentsExist() = runTest {
        val result = repository.getAppointmentsByClient("client-1")

        assertTrue(result.isSuccess)
        assertEquals(emptyList<Appointment>(), result.getOrNull())
    }

    private fun sampleAppointment(
        id: String = "appointment-1",
        clientId: String = "client-1"
    ) = Appointment(
        id = id,
        clientId = clientId,
        manicuristId = "manicurist-1",
        serviceType = ServiceType.HANDS,
        status = AppointmentStatus.REQUESTED,
        scheduledAt = "2026-04-08T10:00:00Z"
    )
}
