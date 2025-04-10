package com.example.resumegenerator.editor.presentation.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
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

    // We apply the bullets only for display
    val displayValue = remember(value, useBulletPoints) {
        if (useBulletPoints) {
            value.lines().joinToString("\n") { line ->
                if (line.isNotBlank() && !line.startsWith(bullet)) "$bullet$line" else line
            }
        } else {
            value
        }
    }

    val onTextChange = { text: String ->
        if (useBulletPoints) {
            // Remove bullet prefix before saving to model
            val cleanText = text.lines().joinToString("\n") { line ->
                line.removePrefix(bullet)
            }
            onValueChange(cleanText)
        } else {
            onValueChange(text)
        }
    }

    OutlinedTextField(
        value = displayValue,
        onValueChange = onTextChange,
        modifier = modifier,
        enabled = enabled,
        label = label,
        colors = textFieldColors(isDarkTheme),
        keyboardOptions = KeyboardOptions(
            imeAction = androidx.compose.ui.text.input.ImeAction.Default
        ),
        visualTransformation = VisualTransformation.None,
        maxLines = 5
    )
}

