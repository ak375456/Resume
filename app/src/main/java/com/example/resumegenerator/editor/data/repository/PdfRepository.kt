package com.example.resumegenerator.editor.data.repository

import android.content.Context
import android.util.Log
import com.itextpdf.forms.PdfAcroForm
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.PdfWriter
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

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

    fun generatePdf(templatePath: String, fields: Map<String, String>): File {
        val outputFile = File(context.filesDir, "resume_${System.currentTimeMillis()}.pdf")

        try {
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
            return outputFile
        } catch (e: Exception) {
            Log.e("PDF_ERROR", "Failed to generate PDF", e)
            throw e // Or handle it differently
        }
    }
}