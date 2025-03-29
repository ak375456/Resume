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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.resumegenerator.home.presentation.util.models.Template
import com.example.resumegenerator.home.presentation.util.models.TemplateCategory
import com.example.resumegenerator.screens.Screens
import java.net.URLEncoder
import kotlin.collections.chunked
import kotlin.collections.forEach

@Composable
fun ExpandableCategory(
    category: TemplateCategory,
    isExpanded: Boolean,
    onCategoryClick: () -> Unit,
    onTemplateClick: (Template) -> Unit,
    navController: NavHostController
) {
    var selectedTemplate by remember { mutableStateOf<Template?>(null) }
    Column(modifier = Modifier.fillMaxWidth()) {
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
                                modifier = Modifier.weight(1f),
                                onOptionSelected = { selectedTemplate = it }
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

    selectedTemplate?.let { template ->
        TemplateOptionsDialog(
            template = template,
            onDismiss = { selectedTemplate = null },

            onCreateCv = {
                val encodedPath = URLEncoder.encode(template.pdfAssetPath, "UTF-8")
                navController.navigate(Screens.Editor.createRoute(encodedPath))
                selectedTemplate = null
            }
        )
    }

}