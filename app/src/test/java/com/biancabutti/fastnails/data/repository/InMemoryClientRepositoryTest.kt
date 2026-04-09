package com.biancabutti.fastnails.data.repository

import com.biancabutti.fastnails.domain.model.Client
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class InMemoryClientRepositoryTest {

    private lateinit var repository: InMemoryClientRepository

    @Before
    fun setUp() {
        repository = InMemoryClientRepository()
    }

    @Test
    fun createClientReturnsCreatedClient() = runTest {
        val client = sampleClient()

        val result = repository.createClient(client)

        assertTrue(result.isSuccess)
        assertEquals(client, result.getOrNull())
    }

    @Test
    fun getClientByIdReturnsClientWhenItExists() = runTest {
        val client = sampleClient(id = "client-1")
        repository.createClient(client)

        val result = repository.getClientById("client-1")

        assertTrue(result.isSuccess)
        assertEquals(client, result.getOrNull())
    }

    @Test
    fun getClientByIdReturnsFailureWhenNotFound() = runTest {
        val result = repository.getClientById("non-existent-id")

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is NoSuchElementException)
    }

    @Test
    fun getClientsReturnsAllCreatedClients() = runTest {
        val client1 = sampleClient(id = "client-1")
        val client2 = sampleClient(id = "client-2")
        repository.createClient(client1)
        repository.createClient(client2)

        val result = repository.getClients()

        assertTrue(result.isSuccess)
        assertEquals(listOf(client1, client2), result.getOrNull())
    }

    @Test
    fun getClientsReturnsEmptyListWhenNoClientsExist() = runTest {
        val result = repository.getClients()

        assertTrue(result.isSuccess)
        assertEquals(emptyList<Client>(), result.getOrNull())
    }

    private fun sampleClient(id: String = "client-1") = Client(
        id = id,
        name = "Jane Doe",
        email = "jane@example.com",
        phone = "+1234567890"
    )
}
