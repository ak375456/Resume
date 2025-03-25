package com.example.resumegenerator.home.presentation.util.models

data class TemplateCategory(
    // "Business", "Tech", "Marketing", "Finance", "Education", "Lifestyle",
    val name: String,
    val templates: List<Template>
)