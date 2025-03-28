package com.example.resumegenerator.editor.presentation


data class EditorState(
    val fields: Map<String, String> = emptyMap(),
    val isLoading: Boolean = true,
    val isGenerating: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)