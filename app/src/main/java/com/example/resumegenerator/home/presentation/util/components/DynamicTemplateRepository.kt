package com.example.resumegenerator.home.presentation.util.components

import android.content.Context
import com.example.resumegenerator.R
import com.example.resumegenerator.home.presentation.util.models.Template
import com.example.resumegenerator.home.presentation.util.models.TemplateCategory
import java.io.IOException
import javax.inject.Inject

class DynamicTemplateRepository @Inject constructor(private val context: Context) {

    fun getTemplates(): List<TemplateCategory> {
        // Look in assets/templates directory
        val categories = context.assets.list("templates")?.toList() ?: emptyList()

        return categories.mapNotNull { categoryName ->
            val templates = loadTemplatesForCategory(categoryName)
            if (templates.isNotEmpty()) {
                TemplateCategory(
                    name = categoryName.replaceFirstChar { it.uppercase() },
                    templates = templates
                )
            } else null
        }
    }

    private fun loadTemplatesForCategory(category: String): List<Template> {
        return try {
            context.assets.list("templates/$category")
                ?.filter { it.endsWith(".html") }
                ?.mapNotNull { file ->
                    val baseName = file.removeSuffix(".html")
                    Template(
                        id = "$category/$baseName".hashCode(),
                        name = generateDisplayName(baseName),
                        thumbnailRes = findPreviewResId(category, baseName),
                        templateName = "$category/$baseName"
                    )
                } ?: emptyList()
        } catch (e: IOException) {
            emptyList()
        }
    }

    private fun generateDisplayName(fileName: String): String {
        return fileName
            .replace("_", " ")
            .replaceFirstChar { it.uppercase() }
    }

    private fun findPreviewResId(category: String, baseName: String): Int {
        val previewNames = listOf(
            "${category}_${baseName}_preview",  // business_corporate_preview
            "${baseName}_preview",             // corporate_preview
            baseName,                          // corporate
            "default_preview"                  // Fallback
        )

        return previewNames.firstNotNullOfOrNull { name ->
            try {
                context.resources.getIdentifier(name, "drawable", context.packageName).takeIf { it != 0 }
            } catch (e: Exception) {
                null
            }
        } ?: R.drawable.default_preview
    }
}