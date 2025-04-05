package com.example.resumegenerator.home.presentation.util.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Plus
import com.composables.icons.lucide.View
import com.example.resumegenerator.home.presentation.util.models.Template
import com.example.resumegenerator.screens.Screens
import java.net.URLEncoder
import com.example.resumegenerator.home.presentation.util.FileShareUtil
import com.example.resumegenerator.ui.theme.CVAppColors

// home/presentation/util/components/TemplateCard.kt
@Composable
fun TemplateCard(
    template: Template,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onOptionSelected: (Template) -> Unit,
    onFavoriteClick: (Template) -> Unit,
    navController: NavHostController,
) {
    val isDarkTheme = isSystemInDarkTheme()
    var showFullPreview by remember { mutableStateOf(false) }

    Card(
        onClick = { onOptionSelected(template) },
        modifier = modifier
            .padding(vertical = 8.dp)
            .aspectRatio(0.75f),
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkTheme) CVAppColors.Components.Cards.backgroundDark
            else CVAppColors.Components.Cards.backgroundLight
        ),
        border = if (isSelected) {
            BorderStroke(
                width = 2.dp,
                color = if (isDarkTheme) CVAppColors.Components.CVTemplates.templateIndicatorActiveDark
                else CVAppColors.Components.CVTemplates.templateIndicatorActiveLight
            )
        } else {
            BorderStroke(
                width = 1.dp,
                color = if (isDarkTheme) CVAppColors.Components.Cards.borderDark
                else CVAppColors.Components.Cards.borderLight
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                Image(
                    painter = painterResource(template.thumbnailRes),
                    contentDescription = template.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = template.name,
                    modifier = Modifier.padding(8.dp),
                    color = if (isDarkTheme) CVAppColors.Components.Text.bodyDark
                    else CVAppColors.Components.Text.bodyLight,
                    style = MaterialTheme.typography.bodyMedium
                )

                HorizontalDivider(
                    color = if (isDarkTheme) CVAppColors.Components.CVTemplates.sectionDividerDark
                    else CVAppColors.Components.CVTemplates.sectionDividerLight,
                    thickness = 1.dp
                )

                AnimatedVisibility(visible = isSelected) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            IconButton(
                                onClick = {
                                    val encodedPath = URLEncoder.encode(template.pdfAssetPath, "UTF-8")
                                    navController.navigate(Screens.Editor.createRoute(encodedPath))
                                },
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = if (isDarkTheme) CVAppColors.Components.Buttons.primaryBackgroundDark
                                    else CVAppColors.Components.Buttons.primaryBackgroundLight,
                                    contentColor = if (isDarkTheme) CVAppColors.Components.Buttons.primaryContentDark
                                    else CVAppColors.Components.Buttons.primaryContentLight
                                )
                            ) {
                                Icon(
                                    imageVector = Lucide.Plus,
                                    contentDescription = "Edit"
                                )
                            }

                            IconButton(
                                onClick = { showFullPreview = true },
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = if (isDarkTheme) CVAppColors.Components.Buttons.secondaryBackgroundDark
                                    else CVAppColors.Components.Buttons.secondaryBackgroundLight,
                                    contentColor = if (isDarkTheme) CVAppColors.Components.Buttons.secondaryContentDark
                                    else CVAppColors.Components.Buttons.secondaryContentLight
                                )
                            ) {
                                Icon(
                                    imageVector = Lucide.View,
                                    contentDescription = "View"
                                )
                            }

                            IconButton(
                                onClick = {
                                    FileShareUtil.shareTemplate(
                                        context = navController.context,
                                        imageResId = template.thumbnailRes,
                                        templateName = template.name
                                    )
                                },
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = if (isDarkTheme) CVAppColors.Dark.surface
                                    else CVAppColors.Light.surface,
                                    contentColor = if (isDarkTheme) CVAppColors.Components.Icons.secondaryDark
                                    else CVAppColors.Components.Icons.secondaryLight
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = "Share"
                                )
                            }
                        }
                    }
                }
            }

            // Favorite button in top-right corner
            IconButton(
                onClick = { onFavoriteClick(template) },
                modifier = Modifier.align(Alignment.TopEnd),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = if (isDarkTheme) CVAppColors.Dark.surface.copy(alpha = 0.7f)
                    else CVAppColors.Light.surface.copy(alpha = 0.7f)
                )
            ) {
                Icon(
                    imageVector = if (template.isFavorite) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = if (template.isFavorite) "Remove from favorites" else "Add to favorites",
                    tint = if (template.isFavorite) {
                        CVAppColors.Components.Status.successLight
                    } else {
                        if (isDarkTheme) CVAppColors.Components.Icons.inactiveDark
                        else CVAppColors.Components.Icons.inactiveLight
                    }
                )
            }
        }
    }

    if (showFullPreview) {
        FullScreenPreview(
            template = template,
            onDismiss = { showFullPreview = false }
        )
    }
}