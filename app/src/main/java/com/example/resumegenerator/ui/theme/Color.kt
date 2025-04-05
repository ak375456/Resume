package com.example.resumegenerator.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

object CVAppColors {
    // Base palette - Light Mode
    object Light {
        // Primary brand colors
        val primary = Color(0xFF3E6BC8)         // Rich blue
        val primaryVariant = Color(0xFF2A5BB9)  // Darker blue for emphasis
        val secondary = Color(0xFF5DC99C)       // Mint green for accents
        val secondaryVariant = Color(0xFF43B889) // Deeper mint for emphasis

        // Neutral colors
        val background = Color(0xFFF8F9FC)      // Very light blue-gray
        val surface = Color(0xFFFFFFFF)         // Pure white
        val error = Color(0xFFE54C65)           // Soft red
        val onPrimary = Color(0xFFFFFFFF)       // White text on primary
        val onSecondary = Color(0xFF1D1D1D)     // Dark text on secondary
        val onBackground = Color(0xFF1D1D1D)    // Dark text on background
        val onSurface = Color(0xFF1D1D1D)       // Dark text on surface
        val onError = Color(0xFFFFFFFF)         // White text on error

        // Additional neutrals
        val textPrimary = Color(0xFF1D1D1D)     // Main text color
        val textSecondary = Color(0xFF616161)   // Secondary text
        val textTertiary = Color(0xFF9E9E9E)    // Hint text
        val divider = Color(0xFFE0E0E0)         // Subtle dividers
    }

    // Base palette - Dark Mode
    object Dark {
        // Primary brand colors
        val primary = Color(0xFF5B82D6)         // Lighter blue for dark mode
        val primaryVariant = Color(0xFF4A72C9)  // Emphasis blue for dark mode
        val secondary = Color(0xFF6BD4AA)       // Brighter mint for visibility
        val secondaryVariant = Color(0xFF52C99B) // Emphasis mint

        // Neutral colors
        val background = Color(0xFF121418)      // Very dark blue-gray
        val surface = Color(0xFF1E2128)         // Dark surface
        val error = Color(0xFFE56773)           // Soft red, slightly brighter
        val onPrimary = Color(0xFFFFFFFF)       // White text on primary
        val onSecondary = Color(0xFF1D1D1D)     // Dark text on secondary
        val onBackground = Color(0xFFE8E8E8)    // Light text on background
        val onSurface = Color(0xFFE8E8E8)       // Light text on surface
        val onError = Color(0xFFFFFFFF)         // White text on error

        // Additional neutrals
        val textPrimary = Color(0xFFE8E8E8)     // Main text color
        val textSecondary = Color(0xFFB6B6B6)   // Secondary text
        val textTertiary = Color(0xFF858585)    // Hint text
        val divider = Color(0xFF3E3E3E)         // Subtle dividers
    }

    // Component-specific colors
    object Components {
        // TopBar
        object TopBar {
            val backgroundLight = Light.surface
            val backgroundDark = Dark.surface
            val contentLight = Light.primary
            val contentDark = Dark.primary
        }

        // Buttons
        object Buttons {
            // Primary action buttons
            val primaryBackgroundLight = Light.primary
            val primaryBackgroundDark = Dark.primary
            val primaryContentLight = Light.onPrimary
            val primaryContentDark = Dark.onPrimary

            // Secondary action buttons
            val secondaryBackgroundLight = Light.secondary
            val secondaryBackgroundDark = Dark.secondary
            val secondaryContentLight = Light.onSecondary
            val secondaryContentDark = Dark.onSecondary

            // Text buttons
            val textButtonLight = Light.primary
            val textButtonDark = Dark.primary

            // Disabled state
            val disabledBackgroundLight = Color(0xFFE0E0E0)
            val disabledBackgroundDark = Color(0xFF3E3E3E)
            val disabledContentLight = Color(0xFF9E9E9E)
            val disabledContentDark = Color(0xFF707070)
        }

        // Text Fields
        object Fields {
            val backgroundLight = Light.surface
            val backgroundDark = Dark.surface
            val outlineLight = Light.divider
            val outlineDark = Dark.divider
            val focusedOutlineLight = Light.primary
            val focusedOutlineDark = Dark.primary
            val labelLight = Light.textSecondary
            val labelDark = Dark.textSecondary
            val textLight = Light.textPrimary
            val textDark = Dark.textPrimary
            val placeholderLight = Light.textTertiary
            val placeholderDark = Dark.textTertiary
        }

        // Error Messages
        object Error {
            val backgroundLight = Color(0xFFFDF2F4)  // Very light red
            val backgroundDark = Color(0xFF2D1D20)   // Very dark red
            val textLight = Light.error
            val textDark = Dark.error
            val borderLight = Light.error.copy(alpha = 0.3f)
            val borderDark = Dark.error.copy(alpha = 0.3f)
            val iconLight = Light.error
            val iconDark = Dark.error
        }

        // Cards
        object Cards {
            val backgroundLight = Light.surface
            val backgroundDark = Dark.surface
            val borderLight = Light.divider
            val borderDark = Dark.divider
            val selectedBorderLight = Light.primary.copy(alpha = 0.5f)
            val selectedBorderDark = Dark.primary.copy(alpha = 0.5f)
            val shadowLight = Color(0x1A000000)  // 10% black shadow
            val shadowDark = Color(0x1A000000)   // 10% black shadow
        }

        // Text
        object Text {
            // Headings
            val h1Light = Light.textPrimary
            val h1Dark = Dark.textPrimary
            val h2Light = Light.textPrimary
            val h2Dark = Dark.textPrimary
            val h3Light = Light.textPrimary
            val h3Dark = Dark.textPrimary

            // Body text
            val bodyLight = Light.textPrimary
            val bodyDark = Dark.textPrimary
            val body2Light = Light.textSecondary
            val body2Dark = Dark.textSecondary

            // Caption & helper text
            val captionLight = Light.textTertiary
            val captionDark = Dark.textTertiary
        }

        // LazyColumns & Lists
        object Lists {
            val backgroundLight = Light.background
            val backgroundDark = Dark.background
            val itemBackgroundLight = Light.surface
            val itemBackgroundDark = Dark.surface
            val separatorLight = Light.divider
            val separatorDark = Dark.divider
            val selectedItemLight = Light.primary.copy(alpha = 0.1f)
            val selectedItemDark = Dark.primary.copy(alpha = 0.2f)
        }

        // Icons
        object Icons {
            val primaryLight = Light.primary
            val primaryDark = Dark.primary
            val secondaryLight = Light.textSecondary
            val secondaryDark = Dark.textSecondary
            val inactiveLight = Light.textTertiary
            val inactiveDark = Dark.textTertiary
            val onPrimaryLight = Light.onPrimary
            val onPrimaryDark = Dark.onPrimary
        }

        // CV Template-specific
        object CVTemplates {
            val highlightLight = Light.primary.copy(alpha = 0.15f)
            val highlightDark = Dark.primary.copy(alpha = 0.25f)
            val accentBorderLight = Light.secondary
            val accentBorderDark = Dark.secondary
            val sectionDividerLight = Light.divider
            val sectionDividerDark = Dark.divider
            val templateIndicatorActiveLight = Light.primary
            val templateIndicatorActiveDark = Dark.primary
            val templateIndicatorInactiveLight = Light.divider
            val templateIndicatorInactiveDark = Dark.divider
        }

        // Success & Progress
        object Status {
            val successLight = Color(0xFF4CAF50)  // Green
            val successDark = Color(0xFF5EBD62)   // Brighter green for dark mode
            val progressLight = Light.primary
            val progressDark = Dark.primary
            val warningLight = Color(0xFFFFA726)  // Orange
            val warningDark = Color(0xFFFFB74D)   // Brighter orange for dark mode
        }
    }
}