package com.example.resumegenerator.home.presentation.util.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

// home/presentation/util/components/TemplateCard.kt
@Composable
fun TemplateCard(
    template: Template,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onOptionSelected: (Template) -> Unit,
    navController: NavHostController,
) {
    Log.d("TemplateCard", "Rendering template: ${template.name}")
    var showFullPreview by remember { mutableStateOf(false) }

    Card(
        onClick = { onOptionSelected(template) },
        modifier = modifier
            .padding(vertical = 8.dp)
            .aspectRatio(0.75f)
    ) {
        Column {
            Image(
                painter = painterResource(template.thumbnailRes),
                contentDescription = template.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = template.name,
                modifier = Modifier.padding(8.dp)
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))

            AnimatedVisibility(visible = isSelected) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        IconButton(onClick = {
                            val encodedPath = URLEncoder.encode(template.pdfAssetPath, "UTF-8")
                            navController.navigate(Screens.Editor.createRoute(encodedPath))
                        }) {
                            androidx.compose.material3.Icon(
                                imageVector = Lucide.Plus,
                                contentDescription = "Edit",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        IconButton(onClick = {
                            showFullPreview = true
                        }) {
                            androidx.compose.material3.Icon(
                                imageVector = Lucide.View,
                                contentDescription = "View",
                                tint = MaterialTheme.colorScheme.secondary
                            )
                        }
                        IconButton(onClick = {
                            FileShareUtil.shareTemplate(
                                context = navController.context,
                                imageResId = template.thumbnailRes,
                                templateName = template.name
                            )
                        }) {
                            androidx.compose.material3.Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share",
                                tint = MaterialTheme.colorScheme.tertiary
                            )
                        }
                    }
                }
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
