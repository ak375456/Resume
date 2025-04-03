package com.example.resumegenerator.di


import android.content.Context
import com.example.resumegenerator.editor.data.repository.PdfRepository
import com.example.resumegenerator.home.data.local.db.AppDatabase
import com.example.resumegenerator.home.data.local.db.dao.FavoriteTemplateDao
import com.example.resumegenerator.home.data.repository.FavoriteTemplateRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideFavoriteTemplateDao(db: AppDatabase): FavoriteTemplateDao {
        return db.favoriteTemplateDao()
    }

    @Provides
    @Singleton
    fun provideFavoriteTemplateRepository(dao: FavoriteTemplateDao): FavoriteTemplateRepository {
        return FavoriteTemplateRepository(dao)
    }


    @Provides
    fun providePdfRepository(@ApplicationContext context: Context): PdfRepository {
        return PdfRepository(context)
    }
}