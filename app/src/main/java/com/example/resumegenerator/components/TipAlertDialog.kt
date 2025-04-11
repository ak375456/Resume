package com.example.resumegenerator.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.resumegenerator.ui.theme.CVAppColors

@Composable
fun TipAlertDialog(
    title: String,
    tips: List<String>,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    isDarkTheme: Boolean
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    title,
                    color = if (isDarkTheme) CVAppColors.Dark.textPrimary
                    else CVAppColors.Light.textPrimary
                )
            },
            text = {
                Column {
                    tips.forEachIndexed { index, tip ->
                        if (index > 0) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        Text(
                            "• $tip",
                            color = if (isDarkTheme) CVAppColors.Dark.textSecondary
                            else CVAppColors.Light.textSecondary
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = onDismiss,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = if (isDarkTheme) CVAppColors.Dark.primary
                        else CVAppColors.Light.primary
                    )
                ) {
                    Text("Got it")
                }
            },
            containerColor = if (isDarkTheme) CVAppColors.Dark.surface
            else CVAppColors.Light.surface
        )
    }
}