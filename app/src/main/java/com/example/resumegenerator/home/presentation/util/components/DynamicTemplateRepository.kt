package com.example.resumegenerator.home.presentation.util.components

import android.content.Context
import com.example.resumegenerator.R
import com.example.resumegenerator.home.presentation.util.models.Template
import com.example.resumegenerator.home.presentation.util.models.TemplateCategory
import java.io.IOException
import javax.inject.Inject

class DynamicTemplateRepository @Inject constructor(private val context: Context) {

    fun getTemplates(): List<TemplateCategory> {
        // Get all category directories in assets
        val categories = context.assets.list("")?.filter {
            // Only include directories and ignore special folders
            !it.contains(".") && it != "images" && it != "fonts"
        } ?: emptyList()

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
            context.assets.list(category)
                ?.filter { it.endsWith(".pdf") }
                ?.mapNotNull { pdfFile ->
                    Template(
                        id = "$category/$pdfFile".hashCode(),
                        name = generateDisplayName(pdfFile),
                        thumbnailRes = findPreviewResId(category, pdfFile),
                        pdfAssetPath = "$category/$pdfFile"
                    )
                } ?: emptyList()
        } catch (e: IOException) {
            emptyList()
        }
    }

    private fun generateDisplayName(pdfFile: String): String {
        return pdfFile
            .removeSuffix(".pdf")
            .replace("_", " ")
            .replaceFirstChar { it.uppercase() }
    }

    private fun findPreviewResId(category: String, pdfFile: String): Int {
        val baseName = pdfFile.removeSuffix(".pdf")
        val previewNames = listOf(
            "${category}_${baseName}_preview",  // tech_cv1_preview
            "${baseName}_preview",             // cv1_preview
            baseName,                          // cv1
            "a"                                // Fallback to a.jpg
        )

        return previewNames.firstNotNullOfOrNull { name ->
            try {
                context.resources.getIdentifier(name, "drawable", context.packageName).takeIf { it != 0 }
            } catch (e: Exception) {
                null
            }
        } ?: R.drawable.default_preview
    }
    fun getTemplatePath(category: String, educationCount: Int): String {
        val educationType = if (educationCount <= 1) "single_education" else "multiple_educations"
        val templates = context.assets.list("$category/$educationType")?.filter { it.endsWith(".pdf") }
        return "$category/$educationType/${templates?.firstOrNull() ?: "template1.pdf"}"
    }
}