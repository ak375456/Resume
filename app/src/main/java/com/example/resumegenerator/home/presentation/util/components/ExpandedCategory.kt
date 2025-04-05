package com.example.resumegenerator.home.presentation.util.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Plus
import com.example.resumegenerator.home.presentation.util.models.Template
import com.example.resumegenerator.home.presentation.util.models.TemplateCategory
import com.example.resumegenerator.ui.theme.CVAppColors
import kotlin.collections.chunked
import kotlin.collections.forEach

@Composable
fun ExpandableCategory(
    category: TemplateCategory,
    isExpanded: Boolean,
    selectedTemplate: Template?,
    onCategoryClick: () -> Unit,
    onTemplateSelected: (Template) -> Unit,
    onFavoriteClick: (Template) -> Unit,
    navController: NavHostController,
) {
    val isDarkTheme = isSystemInDarkTheme()

    Column(modifier = Modifier.fillMaxWidth()) {
        Surface(
            onClick = onCategoryClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            color = if (isDarkTheme) CVAppColors.Components.Cards.backgroundDark
            else CVAppColors.Components.Cards.backgroundLight,
            shadowElevation = if (isDarkTheme) 0.dp else 2.dp,
            tonalElevation = if (isDarkTheme) 2.dp else 1.dp
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp),
                    color = if (isDarkTheme) CVAppColors.Components.Text.h1Dark
                    else CVAppColors.Components.Text.h1Light
                )
                Icon(
                    Lucide.Plus,

                    "plus",
                    tint = if (isDarkTheme) CVAppColors.Components.Icons.primaryDark
                    else CVAppColors.Components.Icons.primaryLight,
                    modifier = Modifier.padding(end = 16.dp),
                )
            }
        }

        AnimatedVisibility(visible = isExpanded) {
            Column(
                modifier = Modifier.padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                category.templates.chunked(2).forEach { rowTemplates ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        rowTemplates.forEach { template ->
                            TemplateCard(
                                template = template,
                                isSelected = selectedTemplate == template,
                                modifier = Modifier.weight(1f),
                                onOptionSelected = onTemplateSelected,
                                onFavoriteClick = onFavoriteClick,
                                navController = navController
                            )
                        }
                        if (rowTemplates.size % 2 != 0) {
                            Spacer(Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}