package com.biancabutti.fastnails.presentation.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppCoordinator {

    private val _currentRoute = MutableStateFlow<AppRoute>(AppRoute.Splash)
    val currentRoute: StateFlow<AppRoute> = _currentRoute.asStateFlow()

    private val _direction = MutableStateFlow(NavigationDirection.Forward)
    val direction: StateFlow<NavigationDirection> = _direction.asStateFlow()

    fun navigateToLogin() = navigate(AppRoute.Login)
    fun navigateToHome() = navigate(AppRoute.Home)
    fun navigateToRegister() = navigate(AppRoute.Register)
    fun navigateToForgotPassword() = navigate(AppRoute.ForgotPassword)
    fun navigateToResetPassword() = navigate(AppRoute.ResetPassword)
    fun navigateBack(to: AppRoute) = navigate(to, NavigationDirection.Backward)

    private fun navigate(
        route: AppRoute,
        direction: NavigationDirection = NavigationDirection.Forward
    ) {
        _direction.value = direction
        _currentRoute.value = route
    }
}
