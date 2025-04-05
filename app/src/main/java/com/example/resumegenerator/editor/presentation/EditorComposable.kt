package com.example.resumegenerator.editor.presentation

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.resumegenerator.components.SuccessSnackbar
import com.example.resumegenerator.components.SuccessSnackbarVisuals
import com.example.resumegenerator.ui.theme.CVAppColors
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun EditorScreen(
    templatePath: String,
    viewModel: EditorViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val isDarkTheme = isSystemInDarkTheme()
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current // Get the context
    val focusManager = LocalFocusManager.current

    LaunchedEffect(templatePath) {
        viewModel.setTemplatePath(templatePath)
    }

    LaunchedEffect(uiState.showSuccessSnackbar) {
        if (uiState.showSuccessSnackbar && uiState.generatedPdfPath != null) {
            focusManager.clearFocus()
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
        containerColor = if (isDarkTheme) CVAppColors.Dark.background
        else CVAppColors.Light.background,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                if (data.visuals is SuccessSnackbarVisuals) {
                    SuccessSnackbar(
                        visuals = data.visuals as SuccessSnackbarVisuals,
                        onActionPerformed = { successVisuals ->
                            openPdf(context, successVisuals.pdfFile)
                        }
                    )
                } else {
                    Snackbar(
                        containerColor = if (isDarkTheme) CVAppColors.Components.Error.backgroundDark
                        else CVAppColors.Components.Error.backgroundLight,
                        contentColor = if (isDarkTheme) CVAppColors.Components.Error.textDark
                        else CVAppColors.Components.Error.textLight
                    ) {
                        Text(data.visuals.message)
                    }
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
    val isDarkTheme = isSystemInDarkTheme()

    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator(
                color = if (isDarkTheme) CVAppColors.Components.Status.progressDark
                else CVAppColors.Components.Status.progressLight
            )
        } else {
            uiState.error?.let { error ->
                Text(
                    text = error,
                    color = if (isDarkTheme) CVAppColors.Components.Error.textDark
                    else CVAppColors.Components.Error.textLight
                )
            }

            uiState.fields.keys.forEach { fieldName ->
                TextField(
                    value = uiState.fields[fieldName] ?: "",
                    onValueChange = { onFieldValueChange(fieldName, it) },
                    label = {
                        Text(
                            text = fieldName.removeSuffix("Field"),
                            color = if (isDarkTheme) CVAppColors.Components.Fields.labelDark
                            else CVAppColors.Components.Fields.labelLight
                        )
                    },
                    colors = TextFieldDefaults.colors(

                        focusedContainerColor = if (isDarkTheme) CVAppColors.Components.Fields.backgroundDark
                        else CVAppColors.Components.Fields.backgroundLight,
                        unfocusedContainerColor = if (isDarkTheme) CVAppColors.Components.Fields.backgroundDark
                        else CVAppColors.Components.Fields.backgroundLight,
                        focusedTextColor = if (isDarkTheme) CVAppColors.Components.Fields.textDark
                        else CVAppColors.Components.Fields.textLight,
                        unfocusedTextColor = if (isDarkTheme) CVAppColors.Components.Fields.textDark
                        else CVAppColors.Components.Fields.textLight,
                        focusedLabelColor = if (isDarkTheme) CVAppColors.Components.Fields.focusedOutlineDark
                        else CVAppColors.Components.Fields.focusedOutlineLight,
                        unfocusedLabelColor = if (isDarkTheme) CVAppColors.Components.Fields.labelDark
                        else CVAppColors.Components.Fields.labelLight,
                        focusedIndicatorColor = if (isDarkTheme) CVAppColors.Components.Fields.focusedOutlineDark
                        else CVAppColors.Components.Fields.focusedOutlineLight,
                        unfocusedIndicatorColor = if (isDarkTheme) CVAppColors.Components.Fields.outlineDark
                        else CVAppColors.Components.Fields.outlineLight,
                        cursorColor = if (isDarkTheme) CVAppColors.Components.Fields.focusedOutlineDark
                        else CVAppColors.Components.Fields.focusedOutlineLight,
                        focusedPlaceholderColor = if (isDarkTheme) CVAppColors.Components.Fields.placeholderDark
                        else CVAppColors.Components.Fields.placeholderLight,
                        unfocusedPlaceholderColor = if (isDarkTheme) CVAppColors.Components.Fields.placeholderDark
                        else CVAppColors.Components.Fields.placeholderLight
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                )
            }

            Button(
                onClick = onGenerateClick,
                modifier = Modifier.padding(top = 16.dp),
                enabled = !uiState.isGenerating,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isDarkTheme) CVAppColors.Components.Buttons.primaryBackgroundDark
                    else CVAppColors.Components.Buttons.primaryBackgroundLight,
                    contentColor = if (isDarkTheme) CVAppColors.Components.Buttons.primaryContentDark
                    else CVAppColors.Components.Buttons.primaryContentLight,
                    disabledContainerColor = if (isDarkTheme) CVAppColors.Components.Buttons.disabledBackgroundDark
                    else CVAppColors.Components.Buttons.disabledBackgroundLight,
                    disabledContentColor = if (isDarkTheme) CVAppColors.Components.Buttons.disabledContentDark
                    else CVAppColors.Components.Buttons.disabledContentLight
                )
            ) {
                if (uiState.isGenerating) {
                    CircularProgressIndicator(
                        color = if (isDarkTheme) CVAppColors.Components.Buttons.primaryContentDark
                        else CVAppColors.Components.Buttons.primaryContentLight,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
                Text("Generate PDF")
            }
        }
    }
}

fun openPdf(context: Context, pdfFile: File) {
    try {
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider", // Replace with your actual FileProvider authority
            pdfFile
        )
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(uri, "application/pdf")
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        context.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
        // Handle the error, e.g., show a toast message
    }
}
