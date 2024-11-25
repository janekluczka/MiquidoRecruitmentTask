package com.luczka.miquidorecruitment.ui.components.progress

import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap

@Composable
fun CustomLinearProgressIndicator(modifier: Modifier) {
    LinearProgressIndicator(
        modifier = modifier,
        strokeCap = StrokeCap.Round
    )
}