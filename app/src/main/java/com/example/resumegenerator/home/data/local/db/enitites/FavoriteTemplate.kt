package com.example.resumegenerator.home.data.local.db.enitites

import androidx.room.Entity
import androidx.room.PrimaryKey

// data/local/db/entities/FavoriteTemplate.kt
@Entity(tableName = "favorite_templates")
data class FavoriteTemplate(
    @PrimaryKey val id: Int,
    val name: String,
    val thumbnailResId: Int,
    val pdfAssetPath: String
)