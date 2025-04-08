package com.example.resumegenerator.editor.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.resumegenerator.editor.data.repository.HtmlTemplateRepository
import com.example.resumegenerator.editor.data.repository.PdfGenerator

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(
    private val pdfGenerator: PdfGenerator,
    private val templateRepo: HtmlTemplateRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(EditorState())
    val uiState: StateFlow<EditorState> = _uiState

    private var _templateName: String = ""

    fun setTemplate(name: String) {
        _templateName = name
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
    fun removeEducation(id: String) {
        _uiState.value = _uiState.value.copy(
            education = _uiState.value.education.filter { it.id != id }
        )
    }


    fun generatePdf() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isGenerating = true)

                // Build HTML content dynamically
                val htmlContent = buildHtmlContent()

                // Generate PDF
                val generatedFile = pdfGenerator.generatePdf(htmlContent)

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
            }
        }
    }

    private fun buildHtmlContent(): String {
        val template = templateRepo.getTemplate(_templateName)

        return template
            .replace("{{name}}", _uiState.value.personalInfo["nameField"] ?: "")
            .replace("{{role}}", _uiState.value.personalInfo["desiredRole"] ?: "")
            .replace("{{phone}}", _uiState.value.personalInfo["numberField"] ?: "")
            .replace("{{email}}", _uiState.value.personalInfo["emailField"] ?: "")
            .replace("{{linkedin}}", _uiState.value.personalInfo["linkedinField"] ?: "")
            .replace("{{portfolio}}", _uiState.value.personalInfo["githubField"] ?: "")
    }

    fun dismissSnackbar() {
        _uiState.value = _uiState.value.copy(showSuccessSnackbar = false)
    }
}