package com.example.resumegenerator.editor.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Trash
import com.example.resumegenerator.editor.presentation.Experience
import com.example.resumegenerator.ui.theme.CVAppColors
import com.example.util.textFieldColors

@Composable
fun ExperienceItem(
    experience: Experience,
    onValueChange: (Experience) -> Unit,
    isDarkTheme: Boolean,
    onRemove: () -> Unit
) {
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
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextField(
                    value = experience.jobTitle,
                    onValueChange = { onValueChange(experience.copy(jobTitle = it)) },
                    label = { Text("Job Title") },
                    modifier = Modifier.weight(1f),
                    colors = textFieldColors(isDarkTheme)
                )

                IconButton(onClick = onRemove) {
                    Icon(Lucide.Trash, contentDescription = "Remove")
                }
            }

            TextField(
                value = experience.company,
                onValueChange = { onValueChange(experience.copy(company = it)) },
                label = { Text("Company") },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors(isDarkTheme)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = experience.startDate,
                    onValueChange = { onValueChange(experience.copy(startDate = it)) },
                    label = { Text("Start Date") },
                    modifier = Modifier.weight(1f),
                    colors = textFieldColors(isDarkTheme)
                )

                TextField(
                    value = experience.endDate,
                    onValueChange = { onValueChange(experience.copy(endDate = it)) },
                    label = { Text("End Date") },
                    modifier = Modifier.weight(1f),
                    colors = textFieldColors(isDarkTheme)
                )
            }

            // Replace the BulletPointTextField with our new handler
            Text(
                text = "Description",
                color = if (isDarkTheme) CVAppColors.Light.textTertiary
                else CVAppColors.Dark.textPrimary,
                modifier = Modifier.padding(start = 4.dp)
            )

            BulletPointHandler(
                text = experience.description,
                onTextChange = { onValueChange(experience.copy(description = it)) },
                isDarkTheme = isDarkTheme,
            )
        }
    }
}