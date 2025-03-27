package com.example.resumegenerator.home.presentation

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.itextpdf.forms.PdfAcroForm
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.PdfWriter
import java.io.File

@Composable
fun EditorComposable(templatePath: String) {
    val context = LocalContext.current
    val fieldValues = remember { mutableStateMapOf<String, String>() }

    LaunchedEffect(templatePath) {
        detectPdfFields(context, templatePath).keys.forEach { fieldName ->
            fieldValues[fieldName] = ""
        }
    }

    Column(Modifier.padding(16.dp)) {
        fieldValues.keys.forEach { fieldName ->
            TextField(
                value = fieldValues[fieldName] ?: "",
                onValueChange = { fieldValues[fieldName] = it },
                label = { Text(fieldName.removeSuffix("Field")) },
                modifier = Modifier.fillMaxWidth().padding(4.dp)
            )
        }

        Button(
            onClick = {
                editPdf(context, templatePath, fieldValues)  // Using your original editPdf
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Generate PDF")
        }
    }
}

fun editPdf(
    context: Context,
    templatePath: String,
    fields: Map<String, String>  // Changed from List<Pair> to Map
) {
    val outputFile = File(context.filesDir, "generated_resume_${System.currentTimeMillis()}.pdf")

    try {
        context.assets.open(templatePath).use { inputStream ->
            PdfReader(inputStream).use { reader ->
                PdfWriter(outputFile).use { writer ->
                    PdfDocument(reader, writer).use { pdfDoc ->
                        val form = PdfAcroForm.getAcroForm(pdfDoc, true)

                        // Debug: Log all detected fields
                        Log.d("PDF_DEBUG", "All fields in template: ${form?.formFields?.keys}")

                        // Fill each field
                        fields.forEach { (fieldName, value) ->
                            form?.getField(fieldName)?.apply {
                                setValue(value)
                                Log.d("PDF_DEBUG", "Set field: $fieldName = $value")
                            } ?: Log.w("PDF_DEBUG", "Field not found: $fieldName")
                        }

                        // Finalize PDF
                        pdfDoc.close()
                        Log.d("PDF_DEBUG", "PDF saved to: ${outputFile.absolutePath}")
                    }
                }
            }
        }
    } catch (e: Exception) {
        Log.e("PDF_ERROR", "Failed to edit PDF", e)
        // You might want to show an error to the user here
    }
}

fun detectPdfFields(context: Context, templatePath: String): Map<String, String> {
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