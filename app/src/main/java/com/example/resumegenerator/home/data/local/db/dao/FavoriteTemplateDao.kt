package com.example.resumegenerator.home.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.resumegenerator.home.data.local.db.enitites.FavoriteTemplate

// data/local/db/dao/FavoriteTemplateDao.kt
@Dao
interface FavoriteTemplateDao {
    @Insert
    suspend fun addFavorite(favorite: FavoriteTemplate)

    @Delete
    suspend fun removeFavorite(favorite: FavoriteTemplate)

    @Query("SELECT * FROM favorite_templates")
    suspend fun getAllFavorites(): List<FavoriteTemplate>

    @Query("SELECT EXISTS(SELECT * FROM favorite_templates WHERE id = :templateId)")
    suspend fun isFavorite(templateId: Int): Boolean
}