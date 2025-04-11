package com.example.resumegenerator.editor.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lightbulb
import com.composables.icons.lucide.Lucide
import com.example.resumegenerator.components.TipAlertDialog
import com.example.resumegenerator.editor.presentation.Summary
import com.example.resumegenerator.ui.theme.CVAppColors

@Composable
fun SummarySection(
    summary: Summary,
    onSummaryChange: (Summary) -> Unit,
    isDarkTheme: Boolean,
) {
    var showHelpDialog by remember { mutableStateOf(false) }

    val summaryTips = listOf(
        "Keep it concise (3-5 bullet points ideal)",
        "Highlight your key qualifications and achievements",
        "Tailor to the specific job you're applying for",
        "Use strong action verbs (e.g., 'Led', 'Developed', 'Managed')",
        "Quantify achievements when possible (e.g., 'Increased sales by 30%')"
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkTheme) CVAppColors.Components.Cards.backgroundDark
            else CVAppColors.Components.Cards.backgroundLight
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Professional Summary",
                    style = MaterialTheme.typography.labelMedium,
                    color = if (isDarkTheme) CVAppColors.Dark.textSecondary
                    else CVAppColors.Light.textSecondary
                )

                IconButton(
                    onClick = { showHelpDialog = true },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Lucide.Lightbulb,
                        contentDescription = "Help",
                    )
                }
            }

            BulletPointHandler(
                text = summary.summary,
                onTextChange = { updatedText ->
                    onSummaryChange(summary.copy(summary = updatedText))
                },
                isDarkTheme = isDarkTheme,
                placeholder = "Add your professional summary"
            )
        }
    }
    TipAlertDialog(
        title = "Summary Writing Tips",
        tips = summaryTips,
        showDialog = showHelpDialog,
        onDismiss = { showHelpDialog = false },
        isDarkTheme = isDarkTheme
    )
}