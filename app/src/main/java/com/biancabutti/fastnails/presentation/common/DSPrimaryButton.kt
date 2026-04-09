package com.biancabutti.fastnails.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.contentDescription

@Composable
fun DSPrimaryButton(
    title: String,
    isEnabled: Boolean,
    color: Color,
    modifier: Modifier = Modifier,
    accessibilityHint: String? = null,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = isEnabled,
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (accessibilityHint != null) Modifier.semantics { contentDescription = accessibilityHint }
                else Modifier
            ),
        colors = ButtonDefaults.buttonColors(containerColor = color)
    ) {
        Text(title)
    }
}
