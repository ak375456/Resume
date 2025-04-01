package com.example.resumegenerator.editor.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.resumegenerator.components.SuccessSnackbar
import com.example.resumegenerator.components.SuccessSnackbarVisuals
import kotlinx.coroutines.launch

@Composable
fun EditorScreen(
    templatePath: String,
    viewModel: EditorViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(templatePath) {
        viewModel.setTemplatePath(templatePath)
    }

    LaunchedEffect(uiState.showSuccessSnackbar) {
        if (uiState.showSuccessSnackbar && uiState.generatedPdfPath != null) {
            scope.launch {
                val visuals = SuccessSnackbarVisuals(
                    pdfPath = uiState.generatedPdfPath!!,
                    duration = SnackbarDuration.Short
                )
                snackbarHostState.showSnackbar(visuals)
                viewModel.dismissSnackbar()
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                if (data.visuals is SuccessSnackbarVisuals) {
                    SuccessSnackbar(visuals = data.visuals as SuccessSnackbarVisuals)
                } else {
                    Snackbar(data)
                }
            }
        }
    ) { innerPadding ->
        EditorContent(
            uiState = uiState,
            onFieldValueChange = viewModel::updateFieldValue,
            onGenerateClick = viewModel::generatePdf,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
@Composable
private fun EditorContent(
    uiState: EditorState,
    onFieldValueChange: (String, String) -> Unit,
    onGenerateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
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



