package com.biancabutti.fastnails.data.repository

import com.biancabutti.fastnails.domain.model.Client
import com.biancabutti.fastnails.domain.repository.ClientRepository

class InMemoryClientRepository : ClientRepository {

    private val clients = mutableListOf<Client>()

    override suspend fun createClient(client: Client): Result<Client> {
        clients.add(client)
        return Result.success(client)
    }

    override suspend fun getClientById(clientId: String): Result<Client> {
        val client = clients.find { it.id == clientId }
        return if (client != null) {
            Result.success(client)
        } else {
            Result.failure(NoSuchElementException("Client not found: $clientId"))
        }
    }

    override suspend fun getClients(): Result<List<Client>> {
        return Result.success(clients.toList())
    }
}
