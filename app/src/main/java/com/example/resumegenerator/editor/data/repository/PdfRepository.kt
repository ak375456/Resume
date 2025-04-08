package com.example.resumegenerator.editor.data.repository


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
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Singleton

@Singleton
class HtmlPdfRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val templateRepo: HtmlTemplateRepository
) {
    fun generatePdf(templateName: String, data: Map<String, String>): File? {
        return try {
            // Create output directory
            val documentsDir = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                context.getString(R.string.app_name)
            ).apply { if (!exists()) mkdirs() }

            // Create output file
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                .format(Date())
            val outputFile = File(documentsDir, "Resume_$timestamp.pdf")

            // Get HTML template
            val htmlTemplate = templateRepo.getTemplate(templateName)

            // Replace placeholders
            var filledHtml = htmlTemplate
            data.forEach { (key, value) ->
                filledHtml = filledHtml.replace("{{$key}}", value)
            }

            // Generate PDF
            FileOutputStream(outputFile).use { os ->
                PdfRendererBuilder().apply {
                    useFastMode()
                    withHtmlContent(filledHtml, null)
                    toStream(os)
                    run()
                }
            }

            Log.d("PDF_GEN", "PDF generated at ${outputFile.absolutePath}")
            outputFile
        } catch (e: Exception) {
            Log.e("PDF_GEN", "Generation failed", e)
            null
        }
    }
}



@Singleton
class HtmlTemplateRepository @Inject constructor(
    private val context: Context
) {
    fun getTemplate(name: String): String {
        return try {
            context.assets.open("templates/$name.html")
                .bufferedReader()
                .use { it.readText() }
        } catch (e: IOException) {
            // Fallback template
            """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { font-family: Arial, sans-serif; margin: 20px; }
                    .header { text-align: center; margin-bottom: 20px; }
                    .name { font-size: 24px; font-weight: bold; }
                </style>
            </head>
            <body>
                <div class="header">
                    <div class="name">{{name}}</div>
                </div>
            </body>
            </html>
            """.trimIndent()
        }
    }
}
