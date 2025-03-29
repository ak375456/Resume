package com.example.resumegenerator.home.presentation.util.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.resumegenerator.home.presentation.util.models.Template

// home/presentation/util/components/TemplateCard.kt
@Composable
fun TemplateCard(
    template: Template,
    modifier: Modifier = Modifier,
    onOptionSelected: (Template) -> Unit
) {
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
        }
    }
}