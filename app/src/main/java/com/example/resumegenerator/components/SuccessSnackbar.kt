package com.example.resumegenerator.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import com.example.resumegenerator.ui.theme.CVAppColors
import java.io.File

class SuccessSnackbarVisuals(
    private val pdfPath: String,
    override val actionLabel: String? = "Open CV",
    override val withDismissAction: Boolean = false,
    override val duration: SnackbarDuration = SnackbarDuration.Short
) : SnackbarVisuals {
    val fileName: String = pdfPath.substringAfterLast("/")

    val displayPath: String = run {
        val docsIndex = pdfPath.indexOf("/Documents/")
        if (docsIndex > 0) "Documents/${pdfPath.substringAfter("/Documents/")}"
        else fileName
    }

    override val message: String
        get() = "CV Generated Successfully: $displayPath"

    val pdfFile: File
        get() = File(pdfPath)
}

@Composable
fun SuccessSnackbar(
    visuals: SuccessSnackbarVisuals,
    onActionPerformed: (SuccessSnackbarVisuals) -> Unit,
    modifier: Modifier = Modifier
) {
    val isDarkTheme = isSystemInDarkTheme()
    Snackbar(
        modifier = modifier.padding(8.dp),
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        action = {
            visuals.actionLabel?.let { label ->
                TextButton(
                    onClick = { onActionPerformed(visuals) },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = if (isDarkTheme) CVAppColors.Components.Buttons.primaryContentDark
                        else CVAppColors.Components.Buttons.primaryContentLight
                    )
                ) {
                    Text(label)
                }
            }
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                modifier = modifier.padding(end = 8.dp)
            )
            Column(modifier = modifier.weight(1f)) {
                Text(
                    text = "CV Generated Successfully",
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = visuals.displayPath,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

