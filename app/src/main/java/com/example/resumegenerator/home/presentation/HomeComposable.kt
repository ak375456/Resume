package com.example.resumegenerator.home.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.resumegenerator.R

@Composable
fun HomeComposable(navController: NavHostController) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        val expandedCategories = remember { mutableStateMapOf<String, Boolean>() }
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            val categories = listOf(
                TemplateCategory(
                    name = "Business",
                    templates = listOf(
                        Template(1, "Corporate", R.drawable.test, "assets/cv1.pdf"),
                    )
                ),
            )

            items(categories) { category ->
                ExpandableCategory(
                    category = category,
                    isExpanded = expandedCategories[category.name] == true,
                    onCategoryClick = {
                        expandedCategories[category.name] = !(expandedCategories[category.name] ?: false)
                    }
                )
            }
        }
    }
}

@Composable
fun ExpandableCategory(
    category: TemplateCategory,
    isExpanded: Boolean,
    onCategoryClick: () -> Unit,
) {
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

        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Column(Modifier.padding(horizontal = 8.dp)) {
                category.templates.chunked(2).forEach { rowTemplates ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        rowTemplates.forEach { template ->
                            TemplateCard(
                                template = template,
                                modifier = Modifier.weight(1f)
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

@Composable
fun TemplateCard(
    template: Template,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .aspectRatio(0.75f)
    ) {
        Column {
            Image(
                painter = painterResource(template.thumbnailRes),
                contentDescription = template.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = template.name,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}