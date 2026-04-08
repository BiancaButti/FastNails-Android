package com.biancabutti.fastnails.domain.repository

import com.biancabutti.fastnails.domain.model.Manicurist

interface ManicuristRepository {
    suspend fun createManicurist(manicurist: Manicurist): Result<Manicurist>

    suspend fun getManicuristById(manicuristId: String): Result<Manicurist>

    suspend fun getAvailableManicurists(): Result<List<Manicurist>>
}