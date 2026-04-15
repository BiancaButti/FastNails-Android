package com.biancabutti.fastnails.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.biancabutti.fastnails.domain.repository.AuthRepository
import com.biancabutti.fastnails.domain.repository.SessionState
import com.biancabutti.fastnails.presentation.navigation.AppCoordinator
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class AppFlowViewModel(
    private val authRepository: AuthRepository,
    val coordinator: AppCoordinator
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _authNoticeMessage = MutableStateFlow<String?>(null)
    val authNoticeMessage: StateFlow<String?> = _authNoticeMessage.asStateFlow()

    private var authNoticeJob: Job? = null

    init {
        observeSessionState()
    }

    private fun observeSessionState() {
        viewModelScope.launch {
            authRepository.sessionState
                .distinctUntilChanged()
                .collect { sessionState ->
                    when (sessionState) {
                        is SessionState.Active -> coordinator.navigateToHome()
                        SessionState.Inactive -> coordinator.navigateToLogin()
                        SessionState.Loading -> Unit
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

    fun navigateToForgotPassword() = coordinator.navigateToForgotPassword()

    fun navigateToRegister() = coordinator.navigateToRegister()

    fun signOut() {
        viewModelScope.launch {
            runCatching { authRepository.signOut() }
                .onFailure { _errorMessage.value = it.message }
        }
    }

    fun showAuthNotice(message: String) {
        authNoticeJob?.cancel()
        authNoticeJob = viewModelScope.launch {
            _authNoticeMessage.value = message
            delay(3000)
            _authNoticeMessage.value = null
        }
    }
}

class AppFlowViewModelFactory(
    private val authRepository: AuthRepository,
    private val coordinator: AppCoordinator
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (!modelClass.isAssignableFrom(AppFlowViewModel::class.java)) {
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
        return AppFlowViewModel(authRepository, coordinator) as T
    }
}
