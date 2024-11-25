@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.luczka.miquidorecruitment.ui.screens.details

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.luczka.miquidorecruitment.R
import com.luczka.miquidorecruitment.ui.util.PreviewDataUtil
import com.luczka.miquidorecruitment.ui.theme.MiquidoRecruitmentTaskTheme

@Composable
fun DetailsScreen(
    uiState: DetailsUiState,
    onAction: (DetailsAction) -> Unit
) {
    val width = uiState.picsumImage.width.toFloat()
    val height = uiState.picsumImage.height.toFloat()
    val aspectRatio = width / height

    val model = ImageRequest.Builder(LocalContext.current)
        .data(uiState.picsumImage.downloadUrl)
        .build()

    BackHandler(uiState.showImage) {
        val action = DetailsAction.HideImage
        onAction(action)
    }

    Scaffold(
        topBar = {
            DetailsTopBar(
                uiState = uiState,
                onAction = onAction
            )
        }
    ) { innerPadding ->
        SharedTransitionLayout {
            AnimatedContent(
                targetState = uiState.showImage,
                label = "image_transition"
            ) { targetState ->
                if (!targetState) {
                    DetailsContent(
                        innerPadding = innerPadding,
                        model = model,
                        aspectRatio = aspectRatio,
                        uiState = uiState,
                        onAction = onAction,
                        animatedVisibilityScope = this@AnimatedContent,
                        sharedTransitionScope = this@SharedTransitionLayout
                    )
                } else {
                    DetailsImage(
                        innerPadding = innerPadding,
                        model = model,
                        aspectRatio = aspectRatio,
                        animatedVisibilityScope = this@AnimatedContent,
                        sharedTransitionScope = this@SharedTransitionLayout
                    )
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun DetailsTopBar(
    uiState: DetailsUiState,
    onAction: (DetailsAction) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = {
                    val action = if(uiState.showImage) DetailsAction.HideImage else DetailsAction.NavigateUp
                    onAction(action)
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = null
                )
            }
        },
        title = {
            Text(text = uiState.picsumImage.id)
        }
    )
}

@Composable
private fun DetailsContent(
    innerPadding: PaddingValues,
    model: ImageRequest,
    aspectRatio: Float,
    uiState: DetailsUiState,
    onAction: (DetailsAction) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    Column(modifier = Modifier.padding(innerPadding)) {
        HorizontalDivider()
        LazyColumn {
            item {
                with(sharedTransitionScope) {
                    AsyncImage(
                        modifier = Modifier
                            .aspectRatio(aspectRatio)
                            .fillMaxWidth()
                            .sharedElement(
                                rememberSharedContentState(key = "image"),
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                            .clickable {
                                val action = DetailsAction.ShowImage
                                onAction(action)
                            },
                        model = model,
                        contentDescription = null
                    )
                }
            }
            item {
                ListItem(
                    overlineContent = {
                        Text(text = stringResource(R.string.picsum_image_field_id))
                    },
                    headlineContent = {
                        Text(text = uiState.picsumImage.id)
                    }
                )
            }
            item {
                ListItem(
                    overlineContent = {
                        Text(text = stringResource(R.string.picsum_image_field_author))
                    },
                    headlineContent = {
                        Text(text = uiState.picsumImage.author)
                    }
                )
            }
            item {
                Row {
                    ListItem(
                        modifier = Modifier.weight(1f),
                        overlineContent = {
                            Text(text = stringResource(R.string.picsum_image_field_width))
                        },
                        headlineContent = {
                            Text(text = uiState.picsumImage.width.toString())
                        }
                    )
                    ListItem(
                        modifier = Modifier.weight(1f),
                        overlineContent = {
                            Text(text = stringResource(R.string.picsum_image_field_height))
                        },
                        headlineContent = {
                            Text(text = uiState.picsumImage.height.toString())
                        }
                    )
                }
            }
            item {
                ListItem(
                    overlineContent = {
                        Text(text = stringResource(R.string.picsum_image_field_download_url))
                    },
                    headlineContent = {
                        Text(text = uiState.picsumImage.downloadUrl)
                    }
                )
            }
        }
    }
}

@Composable
private fun DetailsImage(
    innerPadding: PaddingValues,
    model: ImageRequest,
    aspectRatio: Float,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    Box(
        modifier = Modifier
        .padding(innerPadding)
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        with(sharedTransitionScope) {
            AsyncImage(
                modifier = Modifier
                    .aspectRatio(aspectRatio)
                    .fillMaxWidth()
                    .sharedElement(
                        rememberSharedContentState(key = "image"),
                        animatedVisibilityScope = animatedVisibilityScope
                    ),
                model = model,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun DetailsScreenPreview() {
    MiquidoRecruitmentTaskTheme {
        val uiState = DetailsUiState(
            picsumImage = PreviewDataUtil.getPicsumImageUiState(),
            showImage = false
        )
        DetailsScreen(
            uiState = uiState,
            onAction = {}
        )
    }
}