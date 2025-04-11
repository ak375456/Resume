package com.example.resumegenerator.editor.presentation

import java.util.UUID



data class EditorState(
    val personalInfo: Map<String, String> = emptyMap(),
    val experiences: List<Experience> = emptyList(),
    val education: List<Education> = emptyList(),
    val skills: List<String> = emptyList(),
    val summary: Summary = Summary(),
    val isLoading: Boolean = false,
    val isGenerating: Boolean = false,
    val error: String? = null,
    val generatedPdfPath: String? = null,
    val showSuccessSnackbar: Boolean = false
)

data class Summary(
    val id: String = UUID.randomUUID().toString(),
    val summary: String = ""
)


data class Education(
    val id: String = UUID.randomUUID().toString(),
    val degree: String = "",
    val institution: String = "",
    val startDate: String = "",
    val endDate: String = ""
)

data class Experience(
    val id: String = UUID.randomUUID().toString(),
    val jobTitle: String = "",
    val company: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val description: String = "",
)

