package com.biancabutti.fastnails.data.repository

import com.biancabutti.fastnails.domain.model.User
import com.biancabutti.fastnails.domain.repository.AuthRepository
import com.biancabutti.fastnails.domain.repository.SessionState
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SupabaseAuthRepository(private val supabase: SupabaseClient) : AuthRepository {

    override val sessionState: Flow<SessionState> = supabase.auth.sessionStatus
        .map { status ->
            when (status) {
                is SessionStatus.Authenticated -> {
                    val user = status.session.user
                    if (user != null) {
                        SessionState.Active(User(id = user.id, email = user.email))
                    } else {
                        SessionState.Loading
                    }
                }
                is SessionStatus.NotAuthenticated -> SessionState.Inactive
                else -> SessionState.Loading
            }
        }

    override suspend fun currentUser(): User? {
        val session = supabase.auth.currentSessionOrNull() ?: return null
        return session.user?.let { User(id = it.id, email = it.email) }
    }

    override suspend fun signIn(email: String, password: String): Result<Unit> = runCatching {
        supabase.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
    }

    override suspend fun signOut() {
        supabase.auth.signOut()
    }
}
