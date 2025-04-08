package com.example.resumegenerator.editor.data.repository


import android.content.Context
import android.media.MediaScannerConnection
import android.os.Environment
import android.util.Log
import java.io.File
import javax.inject.Inject
import com.example.resumegenerator.R
import com.itextpdf.html2pdf.HtmlConverter
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Singleton


/*
 * Repository classes for handling PDF generation and template management.
 * Contains both PdfGenerator and HtmlTemplateRepository in a single file
 * as they are closely related and used together.
 */
@Singleton
class PdfGenerator @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val TAG = "PdfGenerator"
        private const val PDF_MIME_TYPE = "application/pdf"
        private const val DATE_FORMAT = "yyyyMMdd_HHmmss"
    }

    /**
     * Generates a PDF file from HTML content and saves it to the Documents directory.
     *
     * @param htmlContent The HTML content to convert to PDF
     * @return Generated PDF file or null if generation failed
     */
    fun generatePdf(htmlContent: String): File? {
        return try {
            val outputFile = createOutputFile()

            HtmlConverter.convertToPdf(htmlContent, FileOutputStream(outputFile))
            notifyMediaScanner(outputFile)

            Log.d(TAG, "PDF generated at ${outputFile.absolutePath}")
            outputFile
        } catch (e: Exception) {
            Log.e(TAG, "PDF generation failed", e)
            null
        }
    }

    /**
     * Creates the output file in the app's Documents subdirectory.
     */
    private fun createOutputFile(): File {
        val documentsDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
            context.getString(R.string.app_name)
        ).apply {
            if (!exists()) mkdirs()
        }

        val timestamp = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
            .format(Date())

        return File(documentsDir, "Resume_$timestamp.pdf")
    }

    /**
     * Notifies the media scanner about the new file to make it visible.
     */
    private fun notifyMediaScanner(file: File) {
        MediaScannerConnection.scanFile(
            context,
            arrayOf(file.absolutePath),
            arrayOf(PDF_MIME_TYPE),
            null
        )
    }
}

@Singleton
class HtmlTemplateRepository @Inject constructor(
    private val context: Context
) {
    companion object {
        private const val TEMPLATE_DIR = "templates"
        private const val FALLBACK_TEMPLATE = """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { 
                        font-family: Arial, sans-serif;
                        margin: 0;
                        padding: 20px;
                    }
                    .section { 
                        margin-bottom: 15px;
                    }
                    .section:last-child {
                        margin-bottom: 0;
                    }
                    .section-title {
                        font-weight: bold;
                        margin-bottom: 5px;
                    }
                </style>
            </head>
            <body>
                {{content}}
            </body>
            </html>
        """
    }

    /**
     * Retrieves an HTML template from the assets folder.
     *
     * @param name The name of the template (without .html extension)
     * @return The template content or a fallback template if not found
     */
    fun getTemplate(name: String): String {
        return try {
            context.assets.open("$TEMPLATE_DIR/$name.html")
                .bufferedReader()
                .use { it.readText() }
        } catch (e: Exception) {
            Log.e("HtmlTemplateRepo", "Error loading template $name, using fallback", e)
            FALLBACK_TEMPLATE.trimIndent()
        }
    }
}