package com.example.resumegenerator.home.data.repository

import com.example.resumegenerator.home.data.local.db.dao.FavoriteTemplateDao
import com.example.resumegenerator.home.data.local.db.enitites.FavoriteTemplate
import com.example.resumegenerator.home.presentation.util.models.Template
import javax.inject.Inject

// data/repository/FavoriteTemplateRepository.kt
class FavoriteTemplateRepository @Inject constructor(
    private val dao: FavoriteTemplateDao
) {
    suspend fun addFavorite(template: Template) {
        dao.addFavorite(template.toFavoriteEntity())
    }

    suspend fun removeFavorite(template: Template) {
        dao.removeFavorite(template.toFavoriteEntity())
    }

    suspend fun getAllFavorites(): List<Template> {
        return dao.getAllFavorites().map { it.toTemplate() }
    }

    suspend fun isFavorite(templateId: Int): Boolean {
        return dao.isFavorite(templateId)
    }

    private fun Template.toFavoriteEntity(): FavoriteTemplate {
        return FavoriteTemplate(
            id = this.id,
            name = this.name,
            thumbnailResId = this.thumbnailRes,
            pdfAssetPath = this.templateName
        )
    }

    private fun FavoriteTemplate.toTemplate(): Template {
        return Template(
            id = this.id,
            name = this.name,
            thumbnailRes = this.thumbnailResId,
            templateName = this.pdfAssetPath,
            isFavorite = true
        )
    }
}