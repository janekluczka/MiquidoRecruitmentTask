package com.luczka.miquidorecruitment.ui.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.luczka.miquidorecruitment.ui.util.PreviewDataUtil
import com.luczka.miquidorecruitment.ui.models.PicsumImageUiState
import com.luczka.miquidorecruitment.ui.theme.MiquidoRecruitmentTaskTheme

@Composable
fun PicsumImageCard(
    modifier: Modifier = Modifier,
    picsumImageUiState: PicsumImageUiState,
    onClick: () -> Unit
) {
    val model = ImageRequest.Builder(LocalContext.current)
        .data(picsumImageUiState.downloadUrl)
        .crossfade(true)
        .build()

    val width = picsumImageUiState.width.toFloat()
    val height = picsumImageUiState.height.toFloat()
    val aspectRatio = width / height

    val textContainerShape = RoundedCornerShape(8.dp)

    Surface(
        modifier = modifier,
        onClick = onClick,
    ) {
        AsyncImage(
            modifier = Modifier
                .aspectRatio(aspectRatio)
                .fillMaxWidth(),
            model = model,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Box(
            Modifier
                .wrapContentSize()
                .padding(16.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = textContainerShape
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant,
                    shape = textContainerShape
                )
        ) {
            Text(
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                ),
                text = picsumImageUiState.id,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview
@Composable
private fun PicsumImageCardPreview() {
    MiquidoRecruitmentTaskTheme {
        PicsumImageCard(
            picsumImageUiState = PreviewDataUtil.getPicsumImageUiState(),
            onClick = {}
        )
    }
}