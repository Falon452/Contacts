package com.activecampaign.contacts.util

import android.view.View
import android.view.animation.AccelerateInterpolator

internal fun View.expandAndFadeAnimationRun(
    scaleEnd: Float = 6f,
    alphaEnd: Float = 0f,
    durationMillis: Long = 300L,
    endAction: () -> Unit,
) =
    animate()?.apply {
        scaleX(scaleEnd)
        scaleY(scaleEnd)
        alpha(alphaEnd)
        interpolator = AccelerateInterpolator(3F)
        duration = durationMillis
        withEndAction { endAction() }
        start()
    }
