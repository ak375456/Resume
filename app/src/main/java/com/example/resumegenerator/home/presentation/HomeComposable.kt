package com.example.resumegenerator.home.presentation


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.resumegenerator.home.presentation.util.components.DynamicTemplateRepository
import com.example.resumegenerator.home.presentation.util.components.ExpandableCategory
import kotlinx.coroutines.launch

@Composable
fun HomeComposable(navController: NavHostController) {
    val context = LocalContext.current
    val templates by remember {
        mutableStateOf(DynamicTemplateRepository(context).getTemplates())
    }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Handle back navigation with success message
    val savedPdfPath = navController
        .currentBackStackEntry
        ?.savedStateHandle
        ?.get<String>("generatedPdfPath")

    LaunchedEffect(savedPdfPath) {
        savedPdfPath?.let { path ->
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = "CV saved to: $path",
                    duration = SnackbarDuration.Short
                )
                // Clear the saved state after showing
                navController.currentBackStackEntry?.savedStateHandle?.remove<String>("generatedPdfPath")
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { androidx.compose.material3.SnackbarHost(snackbarHostState) }
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
