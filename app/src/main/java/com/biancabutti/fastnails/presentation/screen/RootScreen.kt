package com.biancabutti.fastnails.presentation.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.biancabutti.fastnails.presentation.navigation.AppRoute
import com.biancabutti.fastnails.presentation.navigation.NavigationDirection
import com.biancabutti.fastnails.presentation.viewmodel.AppFlowViewModel

@Composable
fun RootScreen(viewModel: AppFlowViewModel) {
    val currentRoute by viewModel.coordinator.currentRoute.collectAsStateWithLifecycle()
    val direction by viewModel.coordinator.direction.collectAsStateWithLifecycle()
    val authNoticeMessage by viewModel.authNoticeMessage.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedContent(
            targetState = currentRoute,
            transitionSpec = {
                if (direction == NavigationDirection.Forward) {
                    (slideInHorizontally { it } + fadeIn()) togetherWith
                            (slideOutHorizontally { -it } + fadeOut())
                } else {
                    (slideInHorizontally { -it } + fadeIn()) togetherWith
                            (slideOutHorizontally { it } + fadeOut())
                }
            },
            label = "root_transition"
        ) { route ->
            when (route) {
                AppRoute.Splash -> SplashScreen()
                AppRoute.Login -> LoginScreen(viewModel = viewModel)
                AppRoute.Register -> RegisterScreen()
                AppRoute.ForgotPassword -> ForgotPasswordScreen()
                AppRoute.ResetPassword -> ResetPasswordScreen()
                AppRoute.Home -> HomeScreen(onSignOut = viewModel::signOut)
            }
        }

        // Auth notice banner — overlay sobre todas as screens
        AuthNoticeBanner(
            message = authNoticeMessage,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun AuthNoticeBanner(message: String?, modifier: Modifier = Modifier) {
    // Mantém o último texto visível durante a animação de saída
    var lastMessage by remember { mutableStateOf<String?>(null) }
    if (message != null) lastMessage = message

    AnimatedVisibility(
        visible = message != null,
        enter = slideInVertically { -it } + fadeIn(),
        exit = slideOutVertically { -it } + fadeOut(),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Color(0xEB4CAF50),
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = lastMessage ?: "",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .semantics { liveRegion = LiveRegionMode.Polite }
                )
            }
        }
    }
}
