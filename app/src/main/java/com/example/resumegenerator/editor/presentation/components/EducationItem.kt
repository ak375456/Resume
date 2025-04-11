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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.BadgeHelp
import com.composables.icons.lucide.Lightbulb
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Trash
import com.example.resumegenerator.components.TipAlertDialog
import com.example.resumegenerator.editor.presentation.EditorViewModel
import com.example.resumegenerator.editor.presentation.Education
import com.example.resumegenerator.ui.theme.CVAppColors
import com.example.util.textFieldColors

@Composable
fun EducationItem(
    education: Education,
    onValueChange: (Education) -> Unit,
    isDarkTheme: Boolean,
    onRemove: () -> Unit,
    viewModel: EditorViewModel = hiltViewModel(),
) {
    var showHelpDialog by remember { mutableStateOf(false) }
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
                verticalAlignment = Alignment.CenterVertically  // Add this for vertical alignment
            ) {
                TextField(
                    value = education.degree,
                    onValueChange = { onValueChange(education.copy(degree = it)) },
                    label = { Text("Degree") },
                    modifier = Modifier.weight(1f),
                    colors = textFieldColors(isDarkTheme)
                )

                // Group the icons in a nested Row for consistent spacing
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)  // Consistent spacing between icons
                ) {
                    IconButton(onClick = onRemove) {
                        Icon(Lucide.Trash, contentDescription = "Remove Education")
                    }

                    IconButton(onClick = { showHelpDialog = true }) {  // Remove size modifier
                        Icon(
                            imageVector = Lucide.Lightbulb,
                            contentDescription = "Help"
                        )
                    }
                }
            }

            TextField(
                value = education.institution,
                onValueChange = { onValueChange(education.copy(institution = it)) },
                label = { Text("Institution") },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors(isDarkTheme)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = education.startDate,
                    onValueChange = { onValueChange(education.copy(startDate = it)) },
                    label = { Text("Start Date(MMM-YYYY)") },
                    modifier = Modifier.weight(1f),
                    colors = textFieldColors(isDarkTheme)
                )
                TextField(
                    value = education.endDate,
                    onValueChange = { onValueChange(education.copy(endDate = it)) },
                    label = { Text("End Date (MMM-YYYY)") },
                    modifier = Modifier.weight(1f),
                    colors = textFieldColors(isDarkTheme)
                )

            }
        }
        val bulletTips = listOf(
            "Start with your most recent degree and list older degrees in descending order",
            "Mention relevant coursework",
            "83% of employers focus on education section for entry-level positions",
            "For experienced professionals, keep education brief and prioritize work experience",

        )

        TipAlertDialog(
            title = "Education Tips",
            tips = bulletTips,
            showDialog = showHelpDialog,
            onDismiss = { showHelpDialog = false },
            isDarkTheme = isDarkTheme
        )
    }
}
