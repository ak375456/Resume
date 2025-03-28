package com.example.resumegenerator.home.presentation

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.resumegenerator.editor.presentation.EditorState
import com.example.resumegenerator.editor.presentation.EditorViewModel
import com.itextpdf.forms.PdfAcroForm
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.PdfWriter
import java.io.File

@Composable
fun EditorScreen(
    templatePath: String,
    viewModel: EditorViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    // Pass the templatePath to ViewModel when it changes
    LaunchedEffect(templatePath) {
        viewModel.setTemplatePath(templatePath)
    }

    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isSuccess) {
        LaunchedEffect(Unit) {
            onBack()
        }
    }

    EditorContent(
        uiState = uiState,
        onFieldValueChange = viewModel::updateFieldValue,
        onGenerateClick = viewModel::generatePdf
    )
}
@Composable
private fun EditorContent(
    uiState: EditorState,
    onFieldValueChange: (String, String) -> Unit,
    onGenerateClick: () -> Unit
) {
    Column(Modifier.padding(16.dp)) {
        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else {
            uiState.error?.let { error ->
                Text(text = error, color = MaterialTheme.colorScheme.error)
            }

            uiState.fields.keys.forEach { fieldName ->
                TextField(
                    value = uiState.fields[fieldName] ?: "",
                    onValueChange = { onFieldValueChange(fieldName, it) },
                    label = { Text(fieldName.removeSuffix("Field")) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                )
            }

            Button(
                onClick = onGenerateClick,
                modifier = Modifier.padding(top = 16.dp),
                enabled = !uiState.isGenerating
            ) {
                if (uiState.isGenerating) {
                    CircularProgressIndicator()
                }
                Text("Generate PDF")
            }
        }
    }
}



