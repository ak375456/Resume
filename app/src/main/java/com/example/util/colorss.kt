package com.example.util

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
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
fun switchColors(isDarkTheme: Boolean): SwitchColors {
    return SwitchDefaults.colors(
        // Thumb colors
        checkedThumbColor = if (isDarkTheme) CVAppColors.Components.Switch.thumbCheckedDark
        else CVAppColors.Components.Switch.thumbCheckedLight,
        uncheckedThumbColor = if (isDarkTheme) CVAppColors.Components.Switch.thumbUncheckedDark
        else CVAppColors.Components.Switch.thumbUncheckedLight,

        // Track colors
        checkedTrackColor = if (isDarkTheme) CVAppColors.Components.Switch.trackCheckedDark
        else CVAppColors.Components.Switch.trackCheckedLight,
        uncheckedTrackColor = if (isDarkTheme) CVAppColors.Components.Switch.trackUncheckedDark
        else CVAppColors.Components.Switch.trackUncheckedLight,

        // Border colors (when applicable)
        checkedBorderColor = if (isDarkTheme) CVAppColors.Components.Switch.borderCheckedDark
        else CVAppColors.Components.Switch.borderCheckedLight,
        uncheckedBorderColor = if (isDarkTheme) CVAppColors.Components.Switch.borderUncheckedDark
        else CVAppColors.Components.Switch.borderUncheckedLight,

        // Disabled states
        disabledCheckedThumbColor = if (isDarkTheme) CVAppColors.Components.Switch.thumbCheckedDark.copy(alpha = 0.38f)
        else CVAppColors.Components.Switch.thumbCheckedLight.copy(alpha = 0.38f),
        disabledUncheckedThumbColor = if (isDarkTheme) CVAppColors.Components.Switch.thumbUncheckedDark.copy(alpha = 0.38f)
        else CVAppColors.Components.Switch.thumbUncheckedLight.copy(alpha = 0.38f),
        disabledCheckedTrackColor = if (isDarkTheme) CVAppColors.Components.Switch.trackCheckedDark.copy(alpha = 0.12f)
        else CVAppColors.Components.Switch.trackCheckedLight.copy(alpha = 0.12f),
        disabledUncheckedTrackColor = if (isDarkTheme) CVAppColors.Components.Switch.trackUncheckedDark.copy(alpha = 0.12f)
        else CVAppColors.Components.Switch.trackUncheckedLight.copy(alpha = 0.12f),
        disabledCheckedBorderColor = if (isDarkTheme) CVAppColors.Components.Switch.borderCheckedDark.copy(alpha = 0.12f)
        else CVAppColors.Components.Switch.borderCheckedLight.copy(alpha = 0.12f),
        disabledUncheckedBorderColor = if (isDarkTheme) CVAppColors.Components.Switch.borderUncheckedDark.copy(alpha = 0.12f)
        else CVAppColors.Components.Switch.borderUncheckedLight.copy(alpha = 0.12f)
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