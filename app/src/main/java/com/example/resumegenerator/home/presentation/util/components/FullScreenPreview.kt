package com.example.resumegenerator.home.presentation.util.components


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

@Composable
fun FullScreenPreview(
    template: Template,
    onDismiss: () -> Unit
) {
    var resetCounter by remember { mutableIntStateOf(0) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Top
            ) {
                // Header with title and close button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp), // Add padding to the header
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = template.name,
                        style = MaterialTheme.typography.headlineSmall
                    )

                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close preview"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp)) // Add spacing after the header

                // Zoomable Image
                ZoomableImage(
                    imageRes = template.thumbnailRes,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    resetTrigger = resetCounter > 0
                )

                Spacer(modifier = Modifier.height(16.dp)) // Add spacing before the button

                // Reset Zoom Button
                Button(
                    onClick = { resetCounter++ },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Reset Zoom")
                }

                Spacer(modifier = Modifier.height(36.dp)) // Add spacing at the bottom
            }
        }
    }
}