package com.example.resumegenerator.home.presentation

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    // Add other fields as needed

    Column (modifier = Modifier.padding(16.dp)) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        // Add more fields as needed...

        Button(
            onClick = {
                editPdf(
                    context = context,
                    templatePath = templatePath,
                    fields = listOf(
                        "NameField" to name,
                        "RoleField" to email,
                        "DetailField" to email
                        // Add other field mappings
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Generate PDF")
        }
    }
}

fun editPdf(
    context: Context,
    templatePath: String,
    fields: List<Pair<String, String>>
) {
    val outputFile = File(context.filesDir, "generated_resume_${System.currentTimeMillis()}.pdf")

    context.assets.open(templatePath).use { inputStream ->
        PdfReader(inputStream).use { reader ->
            PdfWriter(outputFile).use { writer ->
                val pdfDoc = PdfDocument(reader, writer)
                val form = PdfAcroForm.getAcroForm(pdfDoc, true)

                fields.forEach { (fieldName, value) ->
                    form.getField(fieldName)?.setValue(value)
                }

                pdfDoc.close()
            }
        }
    }
}