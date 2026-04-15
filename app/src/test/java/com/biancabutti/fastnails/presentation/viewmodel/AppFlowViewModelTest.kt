package com.biancabutti.fastnails.presentation.viewmodel

import com.biancabutti.fastnails.domain.model.User
import com.biancabutti.fastnails.domain.repository.AuthRepository
import com.biancabutti.fastnails.domain.repository.SessionState
import com.biancabutti.fastnails.presentation.navigation.AppCoordinator
import com.biancabutti.fastnails.presentation.navigation.AppRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AppFlowViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loginSuccessDoesNotSetErrorMessage() = runTest {
        val viewModel = AppFlowViewModel(FakeAuthRepository(), AppCoordinator())

        viewModel.login("user@test.com", "password123")

        assertNull(viewModel.errorMessage.value)
    }

    @Test
    fun loginFailureSetsErrorMessage() = runTest {
        val repository = FakeAuthRepository(signInResult = Result.failure(Exception("Credenciais inválidas")))
        val viewModel = AppFlowViewModel(repository, AppCoordinator())

        viewModel.login("user@test.com", "password123")

        assertEquals("Credenciais inválidas", viewModel.errorMessage.value)
    }

    @Test
    fun isLoadingIsFalseAfterLoginCompletes() = runTest {
        val viewModel = AppFlowViewModel(FakeAuthRepository(), AppCoordinator())

        viewModel.login("user@test.com", "password123")

        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun clearAuthErrorSetsErrorMessageToNull() = runTest {
        val repository = FakeAuthRepository(signInResult = Result.failure(Exception("Erro")))
        val viewModel = AppFlowViewModel(repository, AppCoordinator())
        viewModel.login("user@test.com", "password123")

        viewModel.clearAuthError()

        assertNull(viewModel.errorMessage.value)
    }

    @Test
    fun navigateToForgotPasswordUpdatesCoordinatorRoute() = runTest {
        val viewModel = AppFlowViewModel(FakeAuthRepository(), AppCoordinator())

        viewModel.navigateToForgotPassword()

        assertEquals(AppRoute.ForgotPassword, viewModel.coordinator.currentRoute.value)
    }

    @Test
    fun navigateToRegisterUpdatesCoordinatorRoute() = runTest {
        val viewModel = AppFlowViewModel(FakeAuthRepository(), AppCoordinator())

        viewModel.navigateToRegister()

        assertEquals(AppRoute.Register, viewModel.coordinator.currentRoute.value)
    }

    @Test
    fun signOutCallsRepository() = runTest {
        val repository = FakeAuthRepository()
        val viewModel = AppFlowViewModel(repository, AppCoordinator())

        viewModel.signOut()

        assertTrue(repository.signOutCalled)
    }

    @Test
    fun activeSessionStateNavigatesToHome() = runTest {
        val sessionFlow = MutableStateFlow<SessionState>(SessionState.Loading)
        val coordinator = AppCoordinator()
        AppFlowViewModel(FakeAuthRepository(sessionStateFlow = sessionFlow), coordinator)

        sessionFlow.value = SessionState.Active(User(id = "1", email = "a@b.com"))

        assertEquals(AppRoute.Home, coordinator.currentRoute.value)
    }

    @Test
    fun inactiveSessionStateNavigatesToLogin() = runTest {
        val sessionFlow = MutableStateFlow<SessionState>(SessionState.Loading)
        val coordinator = AppCoordinator()
        AppFlowViewModel(FakeAuthRepository(sessionStateFlow = sessionFlow), coordinator)

        sessionFlow.value = SessionState.Inactive

        assertEquals(AppRoute.Login, coordinator.currentRoute.value)
    }

    private class FakeAuthRepository(
        private val signInResult: Result<Unit> = Result.success(Unit),
        private val sessionStateFlow: MutableStateFlow<SessionState> = MutableStateFlow(SessionState.Loading)
    ) : AuthRepository {

        var signOutCalled = false
            private set

        override val sessionState: Flow<SessionState> = sessionStateFlow

        override suspend fun currentUser(): User? = null

        override suspend fun signIn(email: String, password: String): Result<Unit> = signInResult

        override suspend fun signOut() {
            signOutCalled = true
        }
    }
}
