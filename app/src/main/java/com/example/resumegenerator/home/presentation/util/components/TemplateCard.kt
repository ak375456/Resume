package com.example.resumegenerator.home.presentation.util.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    onFavoriteClick: (Template) -> Unit,
    navController: NavHostController,
) {
    var showFullPreview by remember { mutableStateOf(false) }

    Card(
        onClick = { onOptionSelected(template) },
        modifier = modifier
            .padding(vertical = 8.dp)
            .aspectRatio(0.75f)
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
                                Icon(
                                    imageVector = Lucide.Plus,
                                    contentDescription = "Edit",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                            IconButton(onClick = { showFullPreview = true }) {
                                Icon(
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
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = "Share",
                                    tint = MaterialTheme.colorScheme.tertiary
                                )
                            }
                        }
                    }
                }
            }

            // Favorite button in top-right corner
            IconButton(
                onClick = { onFavoriteClick(template) },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = if (template.isFavorite) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = if (template.isFavorite) "Remove from favorites" else "Add to favorites",
                    tint = if (template.isFavorite) Color(0xffC51104) else Color.Black
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
