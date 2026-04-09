package com.biancabutti.fastnails.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.biancabutti.fastnails.presentation.viewmodel.AppFlowViewModel
import com.biancabutti.fastnails.presentation.viewmodel.AuthState

@Composable
fun RootScreen(viewModel: AppFlowViewModel) {
    val authState by viewModel.authState.collectAsStateWithLifecycle()

    when (authState) {
        AuthState.Loading -> LoadingScreen()
        AuthState.Authenticated -> HomeScreen(onSignOut = viewModel::signOut)
        AuthState.Unauthenticated -> LoginScreen(viewModel = viewModel)
    }
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
