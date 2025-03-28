package com.example.resumegenerator

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.resumegenerator.screens.SetupNavGraph
import com.example.resumegenerator.ui.theme.ResumeGeneratorTheme
import com.itextpdf.forms.PdfAcroForm
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.PdfWriter
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ResumeGeneratorTheme {
                val navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }
}



fun editPdf(context: Context, userName: String) {
    val assetManager = context.assets
    val inputStream = assetManager.open("business/modern.pdf")
    val outputFile = File(context.filesDir, "edited_cv.pdf")

    PdfReader(inputStream).use { reader ->
        PdfWriter(outputFile).use { writer ->
            val pdfDoc = PdfDocument(reader, writer)
            val form = PdfAcroForm.getAcroForm(pdfDoc, true)

            // Set the value of the form field named "NameField"
            form.getField("NameField")?.setValue(userName)
            println(userName)

            pdfDoc.close()
        }
    }
}