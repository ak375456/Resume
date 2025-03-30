package com.example.resumegenerator.home.presentation


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.resumegenerator.home.presentation.util.components.DynamicTemplateRepository
import com.example.resumegenerator.home.presentation.util.components.ExpandableCategory

@Composable
fun HomeComposable(navController: NavHostController) {
    val context = LocalContext.current
    val templates by remember {
        mutableStateOf(DynamicTemplateRepository(context).getTemplates())
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        val expandedCategories = remember { mutableStateMapOf<String, Boolean>() }

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            items(templates) { category ->
                ExpandableCategory(
                    category = category,
                    isExpanded = expandedCategories[category.name] == true,
                    onCategoryClick = {
                        expandedCategories[category.name] =
                            expandedCategories[category.name] != true
                    },

                    navController = navController
                )
            }
        }
    }
}
