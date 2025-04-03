package com.example.resumegenerator.home.presentation



import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.resumegenerator.home.presentation.util.components.DynamicTemplateRepository
import com.example.resumegenerator.home.presentation.util.components.ExpandableCategory
import com.example.resumegenerator.home.presentation.util.models.TemplateCategory

@Composable
fun HomeComposable(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val templates by remember {
        mutableStateOf(DynamicTemplateRepository(context).getTemplates())
    }
    val favoriteTemplates by viewModel.favoriteTemplates.collectAsState()

    LaunchedEffect(templates) {
        viewModel.setTemplates(templates.map { category ->
            category.copy(templates = category.templates.map { template ->
                template.copy(isFavorite = favoriteTemplates.any { it.id == template.id })
            })
        })
    }

    val allCategories = remember(templates, favoriteTemplates) {
        val favoritesCategory = if (favoriteTemplates.isNotEmpty()) {
            TemplateCategory(
                name = "Favorites",
                templates = favoriteTemplates
            )
        } else null

        listOfNotNull(favoritesCategory) + templates
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            items(allCategories) { category ->
                ExpandableCategory(
                    category = category,
                    isExpanded = viewModel.expandedCategories[category.name] == true,
                    selectedTemplate = viewModel.selectedTemplates[category.name],
                    onCategoryClick = { viewModel.toggleCategory(category.name) },
                    onTemplateSelected = { template ->
                        viewModel.selectTemplate(category.name, template)
                    },
                    onFavoriteClick = { template ->
                        viewModel.toggleFavorite(template)
                    },
                    navController = navController
                )
            }
        }
    }
}
