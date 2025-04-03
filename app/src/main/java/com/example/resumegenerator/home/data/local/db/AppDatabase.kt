package com.example.resumegenerator.home.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.resumegenerator.home.data.local.db.dao.FavoriteTemplateDao
import com.example.resumegenerator.home.data.local.db.enitites.FavoriteTemplate

// data/local/db/AppDatabase.kt
@Database(
    entities = [FavoriteTemplate::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteTemplateDao(): FavoriteTemplateDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}