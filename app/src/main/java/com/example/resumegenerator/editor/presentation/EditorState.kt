package com.example.resumegenerator.editor.presentation


data class EditorState(
    val fields: Map<String, String> = emptyMap(),
    val isLoading: Boolean = false,
    val isGenerating: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val generatedPdfPath: String? = null,
    val showSuccessSnackbar: Boolean = false // Add this
)
