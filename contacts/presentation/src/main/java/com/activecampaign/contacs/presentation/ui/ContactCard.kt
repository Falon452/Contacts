package com.activecampaign.contacs.presentation.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.activecampaign.contacs.presentation.model.ContactItem

@Composable
fun ContactCard(
    contactItem: ContactItem,
    modifier: Modifier = Modifier,
    isRight: Boolean,
) {
    var expandText by rememberSaveable { mutableStateOf(false) }
    val transition = updateTransition(expandText, label = "transition")
    val rotate by transition.animateFloat(label = "rotate") { expandedText ->
        when {
            !expandedText -> 0F
            isRight -> -3F
            else -> 3F
        }
    }
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = if (expandText) 1.05f else 1f,
        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse)
    )

    val translateAnim = infiniteTransition.animateFloat(
        initialValue = if (isRight) 1000f else 0f,
        targetValue = if (isRight) 0f else 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )
    val shimmerColors = listOf(
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
    )

    val brush =
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset(translateAnim.value - 500, translateAnim.value - 500),
            end = Offset(translateAnim.value, translateAnim.value)
        )



    Card(
        modifier = modifier.rotate(rotate).scale(scale),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expandText = !expandText
                }
                .background(
                    brush = if (expandText) {
                        brush
                    } else {
                        Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2F),
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                            ),
                        )
                    }
                )
                .padding(16.dp)
        ) {
            Text(
                text = contactItem.displayName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = if (expandText) Int.MAX_VALUE else 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.animateContentSize()
            )
        }
    }
}
