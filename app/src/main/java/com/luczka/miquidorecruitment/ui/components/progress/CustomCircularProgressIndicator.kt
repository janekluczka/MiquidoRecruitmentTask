package com.luczka.miquidorecruitment.ui.components.progress

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap

@Composable
fun CustomCircularProgressIndicator(modifier: Modifier) {
    CircularProgressIndicator(
        modifier = modifier,
        strokeCap = StrokeCap.Round
    )
}