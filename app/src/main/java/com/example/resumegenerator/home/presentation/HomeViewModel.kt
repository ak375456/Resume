// home/presentation/HomeViewModel.kt
package com.example.resumegenerator.home.presentation

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import com.example.resumegenerator.editor.data.repository.PdfRepository
import com.example.resumegenerator.home.presentation.util.models.Template
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
) : ViewModel() {
    // Track expanded categories
    private val _expandedCategories = mutableStateMapOf<String, Boolean>()
    val expandedCategories: Map<String, Boolean> get() = _expandedCategories

    // Track selected template per category
    private val _selectedTemplates = mutableStateMapOf<String, Template?>()
    val selectedTemplates: Map<String, Template?> get() = _selectedTemplates

    fun toggleCategory(categoryName: String) {
        _expandedCategories[categoryName] = _expandedCategories[categoryName] != true
    }

    fun selectTemplate(categoryName: String, template: Template?) {
        _selectedTemplates[categoryName] = if (_selectedTemplates[categoryName] == template) null else template
    }
}