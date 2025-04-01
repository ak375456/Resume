package com.example.resumegenerator.home.presentation.util.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Plus
import com.example.resumegenerator.home.presentation.util.models.Template
import com.example.resumegenerator.home.presentation.util.models.TemplateCategory
import kotlin.collections.chunked
import kotlin.collections.forEach

@Composable
fun ExpandableCategory(
    category: TemplateCategory,
    isExpanded: Boolean,
    onCategoryClick: () -> Unit,
    navController: NavHostController,
) {
    var selectedTemplate by remember { mutableStateOf<Template?>(null) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Surface(
            onClick = onCategoryClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically

            ){
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )
                Icon(
                    Lucide.Plus,
                    "plus"
                )
            }
        }

        AnimatedVisibility(visible = isExpanded) {
            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                category.templates.chunked(2).forEach { rowTemplates ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        rowTemplates.forEach { template ->
                            TemplateCard(
                                template = template,
                                isSelected = selectedTemplate == template, // Check if this template is selected
                                modifier = Modifier.weight(1f),
                                onOptionSelected = {
                                    selectedTemplate = if (selectedTemplate == it) null else it
                                },
                                navController = navController
                            )
                        }

                        // Fill remaining space for uneven rows
                        if (rowTemplates.size % 2 != 0) {
                            Spacer(Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}
