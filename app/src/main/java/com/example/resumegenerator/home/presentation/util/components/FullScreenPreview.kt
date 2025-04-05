package com.example.resumegenerator.home.presentation.util.components


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.resumegenerator.home.presentation.util.models.Template
import com.example.resumegenerator.ui.theme.CVAppColors

@Composable
fun FullScreenPreview(
    template: Template,
    onDismiss: () -> Unit
) {
    val isDarkTheme = isSystemInDarkTheme()
    var resetCounter by remember { mutableIntStateOf(0) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = if (isDarkTheme) CVAppColors.Dark.background
            else CVAppColors.Light.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {
                // Header with title and close button
                Surface(
                    color = if (isDarkTheme) CVAppColors.Components.TopBar.backgroundDark
                    else CVAppColors.Components.TopBar.backgroundLight,
                    tonalElevation = 2.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = template.name,
                            style = MaterialTheme.typography.headlineSmall,
                            color = if (isDarkTheme) CVAppColors.Components.Text.h1Dark
                            else CVAppColors.Components.Text.h1Light
                        )
                        IconButton(
                            onClick = onDismiss,
                            colors = IconButtonDefaults.iconButtonColors(
                                contentColor = if (isDarkTheme) CVAppColors.Components.Icons.primaryDark
                                else CVAppColors.Components.Icons.primaryLight
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close preview"
                            )
                        }
                    }
                }

                ZoomableImage(
                    imageRes = template.thumbnailRes,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    resetTrigger = resetCounter
                )

                Button(
                    onClick = { resetCounter++ },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isDarkTheme) CVAppColors.Components.Buttons.primaryBackgroundDark
                        else CVAppColors.Components.Buttons.primaryBackgroundLight,
                        contentColor = if (isDarkTheme) CVAppColors.Components.Buttons.primaryContentDark
                        else CVAppColors.Components.Buttons.primaryContentLight
                    )
                ) {
                    Text("Reset Zoom")
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}