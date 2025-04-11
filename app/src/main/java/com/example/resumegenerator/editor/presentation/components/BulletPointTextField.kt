package com.example.resumegenerator.editor.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.icons.lucide.BadgeHelp
import com.composables.icons.lucide.IndentDecrease
import com.composables.icons.lucide.Lucide
import com.example.resumegenerator.components.TipAlertDialog
import com.example.resumegenerator.ui.theme.CVAppColors
import com.example.util.textFieldColors




@Composable
fun BulletPointHandler(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean,
    label: String? = null,
    placeholder: String = "Description"
) {
    val bullet = "• "
    var textFieldValue by remember { mutableStateOf(TextFieldValue(text, TextRange(text.length))) }
    var showHelpDialog by remember { mutableStateOf(false) }

    // Handle external text changes
    LaunchedEffect(text) {
        // Only update if the text has changed significantly (not due to user typing)
        if (text != textFieldValue.text && !textFieldValue.text.contains(text) && !text.contains(textFieldValue.text)) {
            textFieldValue = TextFieldValue(text, TextRange(text.length))
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            label?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelMedium,
                    color = if (isDarkTheme) CVAppColors.Dark.textSecondary
                    else CVAppColors.Light.textSecondary
                )
            }

            // Combined buttons in a row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.weight(1f)
            ) {
                IconButton(
                    onClick = { showHelpDialog = true },
                    modifier = Modifier.size(24.dp)
                ) {
                    androidx.compose.material3.Icon(
                        imageVector = Lucide.BadgeHelp,
                        contentDescription = "Help",
                        tint = if (isDarkTheme) CVAppColors.Dark.textTertiary
                        else CVAppColors.Light.textTertiary
                    )
                }

                TextButton(
                    onClick = {
                        val currentText = textFieldValue.text
                        val selection = textFieldValue.selection
                        val newText = currentText.substring(0, selection.start) +
                                bullet + currentText.substring(selection.start)
                        textFieldValue = TextFieldValue(
                            text = newText,
                            selection = TextRange(selection.start + bullet.length)
                        )
                        onTextChange(newText)
                    },
                    modifier = Modifier.padding(start = 4.dp),
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = if (isDarkTheme) CVAppColors.Dark.primary
                        else CVAppColors.Light.primary
                    )
                ) {
                    Text("•", style = TextStyle(fontSize = 24.sp))
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = textFieldValue,
            onValueChange = { newValue ->
                textFieldValue = newValue
                onTextChange(newValue.text)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors(isDarkTheme),
            placeholder = {
                Text(
                    placeholder,
                    color = if (isDarkTheme) CVAppColors.Dark.textTertiary
                    else CVAppColors.Light.textTertiary
                )
            },
            singleLine = false,
            minLines = 4,
            maxLines = 7,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = if (isDarkTheme) CVAppColors.Dark.textPrimary
                else CVAppColors.Light.textPrimary
            )
        )

        // Help dialog
        // Use the reusable dialog component
        val bulletTips = listOf(
            "Start each point with a strong action verb",
            "Keep points concise and impactful",
            "Focus on achievements and results",
            "Use bullet points (•) for 75%+ of your experience descriptions",
            "ATS systems scan bullets 40% faster than paragraphs"
        )

        TipAlertDialog(
            title = "Bullet Points Tips",
            tips = bulletTips,
            showDialog = showHelpDialog,
            onDismiss = { showHelpDialog = false },
            isDarkTheme = isDarkTheme
        )
    }
}