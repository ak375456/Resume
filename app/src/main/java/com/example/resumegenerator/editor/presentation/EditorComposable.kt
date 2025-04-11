package com.example.resumegenerator.editor.presentation

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Trash
import com.example.resumegenerator.components.SuccessSnackbar
import com.example.resumegenerator.components.SuccessSnackbarVisuals
import com.example.resumegenerator.editor.presentation.components.BulletPointHandler
import com.example.resumegenerator.editor.presentation.components.EducationItem
import com.example.resumegenerator.editor.presentation.components.ExperienceItem
import com.example.resumegenerator.editor.presentation.components.PersonalInfoSection
import com.example.resumegenerator.editor.presentation.components.SectionHeader
import com.example.resumegenerator.ui.theme.CVAppColors
import com.example.util.textFieldColors
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun EditorScreen(
    templateName: String,
    viewModel: EditorViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val isDarkTheme = isSystemInDarkTheme()
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current // Get the context
    val focusManager = LocalFocusManager.current

    LaunchedEffect(templateName) {
        viewModel.setTemplate(templateName)
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
            onPersonalInfoChange = { field, value -> viewModel.updatePersonalInfo(field, value) },
            onExperienceChange = viewModel::updateExperience,
            onEducationChange = viewModel::updateEducation,
            onSkillChange = viewModel::updateSkills,
            onSummaryChange = viewModel::updateSummary,
            onAddExperience = viewModel::addExperience,
            onAddEducation = viewModel::addEducation,
            onGenerateClick = viewModel::generatePdf,
            modifier = Modifier.padding(innerPadding),
            viewModel = viewModel
        )
    }
}

@Composable
private fun EditorContent(
    uiState: EditorState,
    onPersonalInfoChange: (String, String) -> Unit,
    onExperienceChange: (Experience) -> Unit,
    onEducationChange: (Education) -> Unit,
    onSkillChange: (List<String>) -> Unit,
    onSummaryChange: (String) -> Unit,
    onAddExperience: () -> Unit,
    onAddEducation: () -> Unit,
    onGenerateClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditorViewModel = hiltViewModel()
) {
    val isDarkTheme = isSystemInDarkTheme()
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally),
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

            // Personal Information Section
            SectionHeader(title = "Personal Information")
            PersonalInfoSection(
                personalInfo = uiState.personalInfo,
                onValueChange = onPersonalInfoChange,
                isDarkTheme = isDarkTheme
            )
            // Education Section
            SectionHeader(
                title = "Education",
                actionText = "Add Education",
                onActionClick = onAddEducation
            )
            uiState.education.forEach { education ->
                EducationItem(
                    education = education,
                    onValueChange = onEducationChange,
                    isDarkTheme = isDarkTheme,
                    onRemove = { viewModel.removeEducation(education.id) },
                    viewModel = viewModel
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // Experience Section
        SectionHeader(
            title = "Work Experience",
            actionText = "Add Experience",
            onActionClick = onAddExperience
        )
        uiState.experiences.forEach { experience ->
            ExperienceItem(
                experience = experience,
                onValueChange = onExperienceChange,
                isDarkTheme = isDarkTheme,
                onRemove = {
                    viewModel.removeExperience(experience.id)
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
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



fun openPdf(context: Context, pdfFile: File) {
    try {
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
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
