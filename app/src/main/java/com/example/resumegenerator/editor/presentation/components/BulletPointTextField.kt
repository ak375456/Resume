package com.example.resumegenerator.editor.presentation.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import com.example.util.textFieldColors


@Composable
fun BulletPointTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isDarkTheme: Boolean,
    useBulletPoints: Boolean,
    label: @Composable (() -> Unit)? = null
) {
    val bullet = "• "

    val processedValue = remember(value, useBulletPoints) {
        if (useBulletPoints && value.isNotEmpty()) {
            value.lines().joinToString("\n") { line ->
                if (line.startsWith(bullet)) line else "$bullet$line"
            }
        } else {
            value
        }
    }

    val onTextChange = { text: String ->
        if (useBulletPoints) {
            // Handle bullet point logic
            val newText = if (text.endsWith("\n")) {
                "$text$bullet"
            } else {
                text
            }
            // Remove bullets before saving to model
            onValueChange(newText.lines().joinToString("\n") {
                it.removePrefix(bullet)
            })
        } else {
            onValueChange(text)
        }
    }

    OutlinedTextField(
        value = processedValue,
        onValueChange = onTextChange,
        modifier = modifier,
        enabled = enabled,
        label = label,
        colors = textFieldColors(isDarkTheme),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Default
        ),
        visualTransformation = if (useBulletPoints) VisualTransformation.None else VisualTransformation.None,
        maxLines = 5
    )
}