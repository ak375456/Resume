package com.example.resumegenerator.editor.data.repository

import android.app.Application
import android.content.Context
import android.os.Environment
import android.util.Log
import com.itextpdf.forms.PdfAcroForm
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.PdfWriter
import java.io.File
import javax.inject.Inject
import com.example.resumegenerator.R
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PdfRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun generatePdf(templatePath: String, fields: Map<String, String>): File? {
        return try {
            // Create app-specific directory in Documents
            val documentsDir = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                context.getString(R.string.app_name)
            ).apply {
                if (!exists()) mkdirs()
            }

            // Generate timestamped filename
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                .format(Date())
            val outputFile = File(documentsDir, "CV_$timestamp.pdf")
            Log.d("PDF_REPO", "Received fields: $fields")
            // Generate PDF
            context.assets.open(templatePath).use { inputStream ->
                PdfReader(inputStream).use { reader ->
                    PdfWriter(outputFile).use { writer ->
                        PdfDocument(reader, writer).use { pdfDoc ->
                            PdfAcroForm.getAcroForm(pdfDoc, true)?.let { form ->
                                fields.forEach { (fieldName, value) ->
                                    form.getField(fieldName)?.setValue(value)
                                }
                            }
                            pdfDoc.close()
                        }
                    }
                }
            }

            // Return simplified path info
            outputFile.apply {
                Log.d("PDF_SAVED", "Saved to: ${absolutePath}")
            }
        } catch (e: Exception) {
            Log.e("PDF_ERROR", "Generation failed", e)
            null
        }
    }
}
