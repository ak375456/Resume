package com.example.resumegenerator.home.presentation.util.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.resumegenerator.home.presentation.util.models.Template
import com.example.resumegenerator.home.presentation.util.models.TemplateCategory
import kotlin.collections.chunked
import kotlin.collections.forEach

@Composable
fun ExpandableCategory(
    category: TemplateCategory,
    isExpanded: Boolean,
    onCategoryClick: () -> Unit,
    onTemplateClick: (Template) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Category Header (Clickable)
        Surface(
            onClick = onCategoryClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            color = Color.LightGray
        ) {
            Text(
                text = category.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )
        }

        // Replace LazyVerticalGrid with Column+Row grid
        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                // Split templates into rows of 2
                category.templates.chunked(2).forEach { rowTemplates ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        rowTemplates.forEach { template ->
                            TemplateCard(
                                template = template,
                                modifier = Modifier.weight(1f),
                                onClick = { onTemplateClick(template) }
                            )
                        }
                        // Add empty spacer if odd number of items
                        if (rowTemplates.size % 2 != 0) {
                            Spacer(Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}