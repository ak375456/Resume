package com.example.resumegenerator.editor.presentation



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resumegenerator.editor.data.repository.PdfRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


// presentation/EditorViewModel.kt
@HiltViewModel
class EditorViewModel @Inject constructor(
    private val pdfRepository: PdfRepository
) : ViewModel() {

    private var _templatePath: String = ""
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
                val fields = pdfRepository.detectFields(_templatePath)
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
        val manipulatedValue = when (fieldName) {
            "NameField" -> value.uppercase()  // Capitalize the entire name
            "DesiredRole" -> value.lowercase() // Convert emails to lowercase
            "phone" -> value.filter { it.isDigit() } // Remove non-numeric characters
            else -> value
        }

        _uiState.value = _uiState.value.copy(
            fields = _uiState.value.fields.toMutableMap().apply {
                put(fieldName, manipulatedValue)
            }
        )
    }


    fun generatePdf() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isGenerating = true)
                pdfRepository.generatePdf(_templatePath, _uiState.value.fields)
                _uiState.value = _uiState.value.copy(
                    isGenerating = false,
                    isSuccess = true
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isGenerating = false,
                    error = "Failed to generate PDF: ${e.message}"
                )
            }
        }
    }

}