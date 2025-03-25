package com.example.resumegenerator.home.presentation

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.resumegenerator.home.presentation.util.components.DynamicTemplateRepository
import com.example.resumegenerator.home.presentation.util.components.ExpandableCategory
import com.example.resumegenerator.screens.Screens
import com.itextpdf.forms.PdfAcroForm
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.PdfWriter
import java.io.File
import java.net.URLEncoder

@Composable
fun HomeComposable(navController: NavHostController) {
    val context = LocalContext.current
    val templates by remember {
        mutableStateOf(DynamicTemplateRepository(context).getTemplates())
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        val expandedCategories = remember { mutableStateMapOf<String, Boolean>() }

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            items(templates) { category ->
                ExpandableCategory(
                    category = category,
                    isExpanded = expandedCategories[category.name] == true,
                    onCategoryClick = {
                        expandedCategories[category.name] = expandedCategories[category.name] != true
                    },
                    onTemplateClick = { template ->
                        val encodedPath = URLEncoder.encode(template.pdfAssetPath, "UTF-8")
                        navController.navigate(Screens.Editor.createRoute(encodedPath))
                    }
                )
            }
        }
    }
}

fun editPdf(context: Context, userName: String) {
    val assetManager = context.assets
    val inputStream = assetManager.open("resume_form.pdf") // Your LibreOffice PDF
    val outputFile = File(context.filesDir, "filled_resume.pdf")

    PdfReader(inputStream).use { reader ->
        PdfWriter(outputFile).use { writer ->
            val pdfDoc = PdfDocument(reader, writer)
            val form = PdfAcroForm.getAcroForm(pdfDoc, true)

            // Fill the field (match "NameField" with LibreOffice field name)
            form.getField("NameField")?.setValue(userName)

            pdfDoc.close()
        }
    }
}
