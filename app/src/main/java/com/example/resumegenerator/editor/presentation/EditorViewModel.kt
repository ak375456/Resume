package com.example.resumegenerator.editor.presentation


import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itextpdf.forms.PdfAcroForm
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.PdfWriter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


@HiltViewModel
class EditorViewModel @Inject constructor(
    private val context: Context
) : ViewModel() {

    private var _templatePath: String = "" // Initialize with empty string
    private val _uiState = MutableStateFlow(EditorState())
    val uiState: StateFlow<EditorState> = _uiState

    fun setTemplatePath(path: String) {
        _templatePath = path
        loadTemplateFields()
    }

    private fun loadTemplateFields() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val fields = detectPdfFields(context, _templatePath)
                _uiState.value = _uiState.value.copy(
                    fields = fields.keys.associateWith { "" },
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to load template",
                    isLoading = false
                )
            }
        }
    }

    fun updateFieldValue(fieldName: String, value: String) {
        _uiState.value = _uiState.value.copy(
            fields = _uiState.value.fields.toMutableMap().apply {
                put(fieldName, value)
            }
        )
    }

    fun generatePdf() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isGenerating = true)
                editPdf(context, _templatePath, _uiState.value.fields)
                _uiState.value = _uiState.value.copy(
                    isGenerating = false,
                    isSuccess = true
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isGenerating = false,
                    error = "Failed to generate PDF"
                )
            }
        }
    }

    // Move your PDF functions here as private methods
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
}
