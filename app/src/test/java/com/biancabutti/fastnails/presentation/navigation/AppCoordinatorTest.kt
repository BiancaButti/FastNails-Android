package com.biancabutti.fastnails.presentation.navigation

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AppCoordinatorTest {

    private lateinit var coordinator: AppCoordinator

    @Before
    fun setUp() {
        coordinator = AppCoordinator()
    }

    @Test
    fun initialRouteIsSplash() {
        assertEquals(AppRoute.Splash, coordinator.currentRoute.value)
    }

    @Test
    fun initialDirectionIsForward() {
        assertEquals(NavigationDirection.Forward, coordinator.direction.value)
    }

    @Test
    fun navigateToLoginSetsLoginRouteAndForwardDirection() {
        coordinator.navigateToLogin()

        assertEquals(AppRoute.Login, coordinator.currentRoute.value)
        assertEquals(NavigationDirection.Forward, coordinator.direction.value)
    }

    @Test
    fun navigateToHomeSetsHomeRouteAndForwardDirection() {
        coordinator.navigateToHome()

        assertEquals(AppRoute.Home, coordinator.currentRoute.value)
        assertEquals(NavigationDirection.Forward, coordinator.direction.value)
    }

    @Test
    fun navigateToRegisterSetsRegisterRoute() {
        coordinator.navigateToRegister()

        assertEquals(AppRoute.Register, coordinator.currentRoute.value)
    }

    @Test
    fun navigateToForgotPasswordSetsForgotPasswordRoute() {
        coordinator.navigateToForgotPassword()

        assertEquals(AppRoute.ForgotPassword, coordinator.currentRoute.value)
    }

    @Test
    fun navigateToResetPasswordSetsResetPasswordRoute() {
        coordinator.navigateToResetPassword()

        assertEquals(AppRoute.ResetPassword, coordinator.currentRoute.value)
    }

    @Test
    fun navigateBackSetsTargetRouteAndBackwardDirection() {
        coordinator.navigateToHome()

        coordinator.navigateBack(to = AppRoute.Login)

        assertEquals(AppRoute.Login, coordinator.currentRoute.value)
        assertEquals(NavigationDirection.Backward, coordinator.direction.value)
    }
}
