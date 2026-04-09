package com.biancabutti.fastnails.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.biancabutti.fastnails.domain.repository.AuthRepository
import com.biancabutti.fastnails.domain.repository.SessionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface AuthState {
    data object Loading : AuthState
    data object Authenticated : AuthState
    data object Unauthenticated : AuthState
}

class AppFlowViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        observeSessionState()
    }

    private fun observeSessionState() {
        viewModelScope.launch {
            // Read initial session via currentSessionOrNull() before the flow emits
            authRepository.currentUser()?.let {
                _authState.value = AuthState.Authenticated
            }
            // Subscribe to sessionStatus flow for reactive auth state updates
            authRepository.sessionState.collect { sessionState ->
                _authState.value = when (sessionState) {
                    is SessionState.Active -> AuthState.Authenticated
                    SessionState.Inactive -> AuthState.Unauthenticated
                    SessionState.Loading -> _authState.value
                }
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            authRepository.signIn(email, password)
                .onFailure { _errorMessage.value = it.message }
            _isLoading.value = false
        }
    }

    fun clearAuthError() {
        _errorMessage.value = null
    }

    fun navigateToForgotPassword() {
        // TODO: wire up navigation
    }

    fun navigateToRegister() {
        // TODO: wire up navigation
    }

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
        }
    }
}

class AppFlowViewModelFactory(
    private val authRepository: AuthRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AppFlowViewModel(authRepository) as T
    }
}
