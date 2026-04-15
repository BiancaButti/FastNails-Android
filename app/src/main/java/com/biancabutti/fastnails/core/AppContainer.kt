package com.biancabutti.fastnails.core

import com.biancabutti.fastnails.data.repository.SupabaseAuthRepository
import com.biancabutti.fastnails.domain.repository.AuthRepository
import com.biancabutti.fastnails.presentation.navigation.AppCoordinator
import io.github.jan.supabase.SupabaseClient

class AppContainer(supabaseClient: SupabaseClient) {
    val coordinator: AppCoordinator = AppCoordinator()
    val authRepository: AuthRepository = SupabaseAuthRepository(supabaseClient)
}
