package com.example.resumegenerator.editor.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.resumegenerator.ui.theme.CVAppColors
import com.example.util.textFieldColors




@Composable
fun BulletPointHandler(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean
) {
    val bullet = "• "
    var textFieldValue by remember { mutableStateOf(TextFieldValue(text)) }

    // Handle external text changes
    LaunchedEffect(text) {
        if (textFieldValue.text != text) {
            textFieldValue = TextFieldValue(text)
        }
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Bullet point button
        IconButton(
            onClick = {
                val currentText = textFieldValue.text
                val selection = textFieldValue.selection
                val newText = currentText.substring(0, selection.start) +
                        bullet + currentText.substring(selection.start)
                textFieldValue = textFieldValue.copy(
                    text = newText,
                    selection = TextRange(selection.start + bullet.length)
                )
                onTextChange(newText)
            },
            modifier = Modifier.size(48.dp)
        ) {
            Text(
                text = "•",
                style = MaterialTheme.typography.titleLarge,
                color = if (!isDarkTheme) CVAppColors.Light.textPrimary
                else CVAppColors.Dark.textPrimary
            )
        }

        // The actual text field
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = { newValue ->
                textFieldValue = newValue
                onTextChange(newValue.text)
            },
            modifier = Modifier.weight(1f),
            colors = textFieldColors(isDarkTheme),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Default
            ),
            visualTransformation = VisualTransformation.None,
            maxLines = 5
        )
    }
}