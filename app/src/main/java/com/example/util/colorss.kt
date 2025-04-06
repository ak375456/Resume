package com.example.util

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import com.example.resumegenerator.ui.theme.CVAppColors

@Composable
 fun textFieldColors(isDarkTheme: Boolean): TextFieldColors {
    return TextFieldDefaults.colors(
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
        else CVAppColors.Components.Fields.outlineLight
    )
}

@Composable
 fun buttonColors(isDarkTheme: Boolean): ButtonColors {
    return ButtonDefaults.buttonColors(
        containerColor = if (isDarkTheme) CVAppColors.Components.Buttons.primaryBackgroundDark
        else CVAppColors.Components.Buttons.primaryBackgroundLight,
        contentColor = if (isDarkTheme) CVAppColors.Components.Buttons.primaryContentDark
        else CVAppColors.Components.Buttons.primaryContentLight
    )
}