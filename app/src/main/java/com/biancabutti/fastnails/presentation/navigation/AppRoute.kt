package com.biancabutti.fastnails.presentation.navigation

sealed interface AppRoute {
    data object Splash : AppRoute
    data object Login : AppRoute
    data object Register : AppRoute
    data object ForgotPassword : AppRoute
    data object ResetPassword : AppRoute
    data object Home : AppRoute
}
