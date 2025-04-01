package com.example.resumegenerator.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp



class SuccessSnackbarVisuals(
    private val pdfPath: String,
    override val actionLabel: String? = null,
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
}


@Composable
fun SuccessSnackbar(
    visuals: SuccessSnackbarVisuals,
    modifier: Modifier = Modifier
) {
    Snackbar(
        modifier = modifier.padding(8.dp),
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
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