package com.example.resumegenerator.editor.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import coil.util.Logger
import com.example.resumegenerator.editor.data.repository.PdfRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(
    private val pdfRepository: PdfRepository,
    application: Application
) : AndroidViewModel(application) {

    private var _templatePath: String = ""
    private val _uiState = MutableStateFlow(EditorState())
    val uiState: StateFlow<EditorState> = _uiState

    fun setTemplatePath(path: String) {
        _templatePath = path
        _uiState.value = _uiState.value.copy(
            personalInfo = mapOf(
                "nameField" to "",
                "desiredRole" to "",
                "numberField" to "",
                "emailField" to "",
                "linkedinField" to "",
                "githubField" to ""
            )
        )
    }



    fun updatePersonalInfo(field: String, value: String) {
        _uiState.value = _uiState.value.copy(
            personalInfo = _uiState.value.personalInfo.toMutableMap().apply {
                put(field, value)
            }
        )
    }

    fun updateExperience(experience: Experience) {
        _uiState.value = _uiState.value.copy(
            experiences = _uiState.value.experiences.map {
                if (it.id == experience.id) experience else it
            }
        )
    }

    fun addExperience() {
        _uiState.value = _uiState.value.copy(
            experiences = _uiState.value.experiences + Experience()
        )
    }

    fun removeExperience(id: String) {
        _uiState.value = _uiState.value.copy(
            experiences = _uiState.value.experiences.filter { it.id != id }
        )
    }

    fun updateEducation(education: Education) {
        _uiState.value = _uiState.value.copy(
            education = _uiState.value.education.map {
                if (it.id == education.id) education else it
            }
        )
    }

    fun addEducation() {
        _uiState.value = _uiState.value.copy(
            education = _uiState.value.education + Education()
        )
    }

    fun updateSkills(skills: List<String>) {
        _uiState.value = _uiState.value.copy(
            skills = skills
        )
    }

    fun updateSummary(summary: String) {
        _uiState.value = _uiState.value.copy(
            summary = summary
        )
    }

    fun generatePdf() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isGenerating = true)

                // Build the fields map correctly
                val fields = mapOf(
                    "nameField" to (_uiState.value.personalInfo["nameField"] ?: ""),
                    "desiredRole" to (_uiState.value.personalInfo["desiredRole"] ?: ""),
                    "numberField" to (_uiState.value.personalInfo["numberField"] ?: ""),
                    "emailField" to (_uiState.value.personalInfo["emailField"] ?: ""),
                    "linkedinField" to (_uiState.value.personalInfo["linkedinField"] ?: ""),
                    "githubField" to (_uiState.value.personalInfo["githubField"] ?: ""),
                )

                Log.d("PDF_DEBUG", "Fields being sent: $fields")

                val generatedFile = pdfRepository.generatePdf(_templatePath, fields)
                _uiState.value = _uiState.value.copy(
                    isGenerating = false,
                    generatedPdfPath = generatedFile?.absolutePath ?: "",
                    showSuccessSnackbar = true
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isGenerating = false,
                    error = "Failed to generate PDF: ${e.message}"
                )
                Log.e("PDF_ERROR", "Generation failed", e)
            }
        }
    }

    fun dismissSnackbar() {
        _uiState.value = _uiState.value.copy(showSuccessSnackbar = false)
    }
}