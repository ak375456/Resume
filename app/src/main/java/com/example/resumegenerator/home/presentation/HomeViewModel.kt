// home/presentation/HomeViewModel.kt
package com.example.resumegenerator.home.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resumegenerator.home.data.repository.FavoriteTemplateRepository
import com.example.resumegenerator.home.presentation.util.models.Template
import com.example.resumegenerator.home.presentation.util.models.TemplateCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val favoriteRepository: FavoriteTemplateRepository
) : ViewModel() {
    // Track expanded categories
    private val _expandedCategories = mutableStateMapOf<String, Boolean>()
    val expandedCategories: Map<String, Boolean> get() = _expandedCategories

    // Track selected template per category
    private val _selectedTemplates = mutableStateMapOf<String, Template?>()
    val selectedTemplates: Map<String, Template?> get() = _selectedTemplates


    private val _favoriteTemplates = MutableStateFlow<List<Template>>(emptyList())
    val favoriteTemplates: StateFlow<List<Template>> = _favoriteTemplates.asStateFlow()

    private val _allTemplates = mutableStateListOf<TemplateCategory>()
    val allTemplates: List<TemplateCategory> get() = _allTemplates

    fun toggleCategory(categoryName: String) {
        _expandedCategories[categoryName] = _expandedCategories[categoryName] != true
    }

    fun selectTemplate(categoryName: String, template: Template?) {
        _selectedTemplates[categoryName] = if (_selectedTemplates[categoryName] == template) null else template
    }

    init {
        viewModelScope.launch {
            loadFavorites()
        }
    }

    private suspend fun loadFavorites() {
        _favoriteTemplates.value = favoriteRepository.getAllFavorites()
    }

    fun setTemplates(templates: List<TemplateCategory>) {
        _allTemplates.clear()
        _allTemplates.addAll(templates)
    }

    fun toggleFavorite(template: Template) {
        viewModelScope.launch {
            if (template.isFavorite) {
                favoriteRepository.removeFavorite(template)
            } else {
                favoriteRepository.addFavorite(template)
            }
            loadFavorites()
        }
    }

}