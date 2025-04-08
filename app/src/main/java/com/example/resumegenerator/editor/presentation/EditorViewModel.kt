package com.example.resumegenerator.editor.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.resumegenerator.editor.data.repository.HtmlPdfRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(
    private val pdfRepository: HtmlPdfRepository,
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

                val data = buildMap {
                    // Personal info
                    put("name", _uiState.value.personalInfo["nameField"] ?: "")
                    put("email", _uiState.value.personalInfo["emailField"] ?: "")
                    put("phone", _uiState.value.personalInfo["numberField"] ?: "")

                    // Education
                    _uiState.value.education.forEachIndexed { index, edu ->
                        put("edu_${index}_degree", edu.degree)
                        put("edu_${index}_institution", edu.institution)
                        put("edu_${index}_dates", edu.startDateAndEndData)
                    }

                    // Add other sections as needed
                }

                val generatedFile = pdfRepository.generatePdf(_templateName, data)

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

    fun dismissSnackbar() {
        _uiState.value = _uiState.value.copy(showSuccessSnackbar = false)
    }
}