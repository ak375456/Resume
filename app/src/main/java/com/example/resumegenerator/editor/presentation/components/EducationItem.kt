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
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Trash
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
    viewModel: EditorViewModel = hiltViewModel()
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
                    value = education.degree,
                    onValueChange = { onValueChange(education.copy(degree = it)) },
                    label = { Text("Degree") },
                    modifier = Modifier.weight(1f),
                    colors = textFieldColors(isDarkTheme)
                )

                IconButton(onClick = onRemove) {
                    Icon(Lucide.Trash, contentDescription = "Remove Education")
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
                    value = education.startDateAndEndData,
                    onValueChange = { onValueChange(education.copy(startDateAndEndData = it)) },
                    label = { Text("Start Date And End Date (MMM YYYY)") },
                    modifier = Modifier.weight(1f),
                    colors = textFieldColors(isDarkTheme)
                )

            }
        }
    }
}