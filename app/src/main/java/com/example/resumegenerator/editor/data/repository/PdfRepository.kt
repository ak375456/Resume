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

class PdfRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun detectFields(templatePath: String): Map<String, String> {
        return try {
            context.assets.open(templatePath).use { input ->
                PdfReader(input).use { reader ->
                    val form = PdfAcroForm.getAcroForm(PdfDocument(reader), false)
                    form?.formFields?.mapValues { it.value.toString() } ?: emptyMap()
                }
            }
        } catch (e: Exception) {
            Log.e("PDF_FIELDS", "Error detecting fields", e)
            emptyMap()
        }
    }

    fun generatePdf(templatePath: String, fields: Map<String, String>): File? {
        try {
            val documentsDir = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                context.getString(R.string.app_name) // Use context
            )

            if (!documentsDir.exists()) {
                documentsDir.mkdirs()
            }

            val outputFile = File(documentsDir, "CV_${System.currentTimeMillis()}.pdf")

            context.assets.open(templatePath).use { inputStream ->
                PdfReader(inputStream).use { reader ->
                    PdfWriter(outputFile).use { writer ->
                        PdfDocument(reader, writer).use { pdfDoc ->
                            val form = PdfAcroForm.getAcroForm(pdfDoc, true)
                            fields.forEach { (fieldName, value) ->
                                form?.getField(fieldName)?.setValue(value)
                            }
                            pdfDoc.close()
                        }
                    }
                }
            }

            Log.d("PDF_GENERATION", "PDF saved at: ${outputFile.absolutePath}")
            return outputFile
        } catch (e: Exception) {
            Log.e("PDF_ERROR", "Failed to generate PDF", e)
            return null
        }
    }
}
