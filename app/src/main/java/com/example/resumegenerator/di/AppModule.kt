package com.example.resumegenerator.di


import android.content.Context
import com.example.resumegenerator.editor.data.repository.PdfRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun providePdfRepository(@ApplicationContext context: Context): PdfRepository {
        return PdfRepository(context)
    }
}