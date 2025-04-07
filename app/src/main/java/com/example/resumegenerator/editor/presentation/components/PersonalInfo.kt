package com.example.resumegenerator.editor.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.util.textFieldColors

@Composable
fun PersonalInfoSection(
    personalInfo: Map<String, String>,
    onValueChange: (String, String) -> Unit,
    isDarkTheme: Boolean,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = personalInfo["nameField"] ?: "",
            onValueChange = { onValueChange("nameField", it) },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors(isDarkTheme)
        )

        TextField(
            value = personalInfo["desiredRole"] ?: "",
            onValueChange = { onValueChange("desiredRole", it) },
            label = { Text("Desired Role") },
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors(isDarkTheme)
        )
        TextField(
            value = personalInfo["numberField"] ?: "",
            onValueChange = { onValueChange("numberField", it) },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors(isDarkTheme)
        )
        TextField(
            value = personalInfo["emailField"] ?: "",
            onValueChange = { onValueChange("emailField", it) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors(isDarkTheme)
        )
        TextField(
            value = personalInfo["linkedinField"] ?: "",
            onValueChange = { onValueChange("linkedinField", it) },
            label = { Text("LinkedIn profile Link") },
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors(isDarkTheme)
        )
        TextField(
            value = personalInfo["githubField"] ?: "",
            onValueChange = { onValueChange("githubField", it) },
            label = { Text("Portfolio (github/behance)") },
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors(isDarkTheme)
        )

        // Add more personal info fields as needed
    }
}