package com.example.resumegenerator.home.presentation.util.models

data class Template(
    val id: Int,
    val name: String,
    val thumbnailRes: Int,
    val templateName: String,
    var isFavorite: Boolean = false
)