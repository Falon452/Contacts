package com.activecampaign.contacts.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.activecampaign.contacts.R
import com.activecampaign.theme.ui.AppTheme

@Composable
internal fun LogoInBackground(
    modifier: Modifier = Modifier,
) {
    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )
    val activeCampaignColor = Color(0xFF004CFF)
    val shimmerColors = listOf(
        activeCampaignColor.copy(alpha = 1f),
        activeCampaignColor.copy(alpha = 0.4f),
        activeCampaignColor.copy(alpha = 1f),
    )
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim.value - 500, translateAnim.value - 500),
        end = Offset(translateAnim.value, translateAnim.value)
    )
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Icon(
            modifier = Modifier
                .graphicsLayer(alpha = 0.2f)
                .drawWithCache {
                    onDrawWithContent {
                        drawContent()
                        drawRect(brush, blendMode = BlendMode.SrcAtop)
                    }
                }
                .align(Alignment.Center),
            imageVector = ImageVector.vectorResource(R.drawable.ac_logo),
            contentDescription = null,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DarkLogoInBackground() {
    AppTheme {
        LogoInBackground()
    }
}
