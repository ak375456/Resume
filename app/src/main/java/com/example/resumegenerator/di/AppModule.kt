package com.example.resumegenerator.di


import android.content.Context
import com.example.resumegenerator.editor.data.repository.HtmlTemplateRepository
import com.example.resumegenerator.editor.data.repository.PdfGenerator
import com.example.resumegenerator.home.data.local.db.AppDatabase
import com.example.resumegenerator.home.data.local.db.dao.FavoriteTemplateDao
import com.example.resumegenerator.home.data.repository.FavoriteTemplateRepository
import com.example.resumegenerator.home.presentation.util.components.DynamicTemplateRepository
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
    @Singleton
    fun providePdfGenerator(
        @ApplicationContext context: Context
    ): PdfGenerator {
        return PdfGenerator(context)
    }

    @Provides
    @Singleton
    fun provideHtmlTemplateRepository(
        @ApplicationContext context: Context
    ): HtmlTemplateRepository {
        return HtmlTemplateRepository(context)
    }

    @Provides
    fun provideDynamicTemplateRepository(
        @ApplicationContext context: Context
    ): DynamicTemplateRepository {
        return DynamicTemplateRepository(context)
    }
}