package com.biancabutti.fastnails.domain.repository

import com.biancabutti.fastnails.domain.model.Client

interface ClientRepository {
    suspend fun createClient(client: Client): Result<Client>

    suspend fun getClientById(clientId: String): Result<Client>

    suspend fun getClients(): Result<List<Client>>
}