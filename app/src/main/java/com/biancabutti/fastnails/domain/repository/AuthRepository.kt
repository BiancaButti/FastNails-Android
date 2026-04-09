package com.biancabutti.fastnails.domain.repository

import com.biancabutti.fastnails.domain.model.User
import kotlinx.coroutines.flow.Flow

sealed interface SessionState {
    data object Loading : SessionState
    data class Active(val user: User) : SessionState
    data object Inactive : SessionState
}

interface AuthRepository {
    val sessionState: Flow<SessionState>
    suspend fun currentUser(): User?
    suspend fun signIn(email: String, password: String): Result<Unit>
    suspend fun signOut()
}
