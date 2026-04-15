package com.biancabutti.fastnails

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.biancabutti.fastnails.presentation.screen.RootScreen
import com.biancabutti.fastnails.presentation.viewmodel.AppFlowViewModel
import com.biancabutti.fastnails.presentation.viewmodel.AppFlowViewModelFactory
import com.biancabutti.fastnails.ui.theme.FastNailsTheme

class MainActivity : ComponentActivity() {

    private val viewModel: AppFlowViewModel by viewModels {
        val appContainer = (application as FastNailsApplication).appContainer
        AppFlowViewModelFactory(appContainer.authRepository, appContainer.coordinator)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FastNailsTheme {
                RootScreen(viewModel = viewModel)
            }
        }
    }
}
