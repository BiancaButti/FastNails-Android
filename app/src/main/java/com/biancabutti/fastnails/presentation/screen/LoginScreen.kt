package com.biancabutti.fastnails.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.biancabutti.fastnails.R
import com.biancabutti.fastnails.core.validation.isValidEmail
import com.biancabutti.fastnails.core.validation.isValidPassword
import com.biancabutti.fastnails.presentation.common.DSFormSecureField
import com.biancabutti.fastnails.presentation.common.DSFormTextField
import com.biancabutti.fastnails.presentation.common.DSOrDivider
import com.biancabutti.fastnails.presentation.common.DSPrimaryButton
import com.biancabutti.fastnails.presentation.viewmodel.AppFlowViewModel
import com.biancabutti.fastnails.ui.theme.AppPink

@Composable
fun LoginScreen(viewModel: AppFlowViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf<String?>(null) }

    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()

    val emailInvalidError = stringResource(R.string.login_validation_email_invalid)
    val signInHint = stringResource(R.string.login_button_sign_in_accessibility_hint)
    val disabledHint = stringResource(R.string.common_button_disabled)

    val isSignInEnabled = email.isValidEmail && password.isValidPassword && !isLoading
    val forgotPasswordHint = stringResource(R.string.login_button_forgot_password_accessibility_hint)
    val signUpHint = stringResource(R.string.login_link_sign_up_accessibility_hint)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // MARK: - Logo
        // TODO: replace ic_launcher_foreground with the actual fastnails_logo drawable
        LogoSection()

        // MARK: - Email
        DSFormTextField(
            label = stringResource(R.string.login_field_email),
            placeholder = stringResource(R.string.login_field_email_placeholder),
            value = email,
            onValueChange = { newValue ->
                email = newValue
                emailError = if (newValue.isNotEmpty() && !newValue.isValidEmail) emailInvalidError else null
                viewModel.clearAuthError()
            },
            errorMessage = emailError,
            keyboardType = KeyboardType.Email
        )

        // MARK: - Password
        DSFormSecureField(
            label = stringResource(R.string.login_field_password),
            placeholder = stringResource(R.string.login_field_password_placeholder),
            value = password,
            onValueChange = { newValue ->
                password = newValue
                viewModel.clearAuthError()
            },
            isVisible = showPassword,
            onVisibilityToggle = { showPassword = !showPassword }
        )

        // MARK: - Forgot Password
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.weight(1f))
            TextButton(
                onClick = { viewModel.navigateToForgotPassword() },
                modifier = Modifier.semantics { contentDescription = forgotPasswordHint }
            ) {
                Text(
                    text = stringResource(R.string.login_button_forgot_password),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // MARK: - Sign In
        DSPrimaryButton(
            title = stringResource(R.string.login_button_sign_in),
            isEnabled = isSignInEnabled,
            color = AppPink,
            accessibilityHint = if (isSignInEnabled) signInHint else disabledHint,
            onClick = { viewModel.login(email, password) }
        )

        // MARK: - Loading / Error feedback
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = AppPink)
            }
            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Red
                )
            }
        }

        // MARK: - Divider
        DSOrDivider()

        // MARK: - Sign Up
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.login_link_no_account),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            TextButton(
                onClick = { viewModel.navigateToRegister() },
                modifier = Modifier.semantics { contentDescription = signUpHint }
            ) {
                Text(
                    text = stringResource(R.string.login_link_sign_up),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
                    ),
                    color = AppPink
                )
            }
        }
    }
}

@Composable
private fun LogoSection() {
    // TODO: replace with Image(painter = painterResource(R.drawable.fastnails_logo), ...)
    //       once the asset is added to res/drawable/
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineLarge,
            color = AppPink
        )
    }
}
