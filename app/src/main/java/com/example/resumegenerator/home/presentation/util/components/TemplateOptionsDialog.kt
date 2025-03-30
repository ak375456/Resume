package com.example.resumegenerator.home.presentation.util.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.resumegenerator.home.presentation.util.models.Template

@Composable
fun TemplateOptionsDialog(
    template: Template,
    onDismiss: () -> Unit,

    onCreateCv: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showFullPreview by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier,
        title = { Text(text = template.name) },
        text = {
            Column {
                Image(
                    painter = painterResource(id = template.thumbnailRes),
                    contentDescription = "Template Preview",
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 200.dp)
                        .padding(bottom = 16.dp),
                    contentScale = ContentScale.Fit
                )
            }
        },
        confirmButton = {
            Button(onClick = onCreateCv) {
                Text("Create CV")
            }
        },
        dismissButton = {
            Button(onClick = { showFullPreview = true }) {
                Text("View Template")
            }
        }
    )

    if (showFullPreview) {
        FullScreenPreview(
            template = template,
            onDismiss = { showFullPreview = false }
        )
    }
}