package com.luczka.miquidorecruitment.ui.screens.main

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.luczka.miquidorecruitment.R
import com.luczka.miquidorecruitment.ui.util.PreviewDataUtil
import com.luczka.miquidorecruitment.ui.components.cards.PicsumImageCard
import com.luczka.miquidorecruitment.ui.components.progress.CustomCircularProgressIndicator
import com.luczka.miquidorecruitment.ui.components.progress.CustomLinearProgressIndicator
import com.luczka.miquidorecruitment.ui.models.PicsumImageUiState
import com.luczka.miquidorecruitment.ui.theme.MiquidoRecruitmentTaskTheme
import com.luczka.miquidorecruitment.ui.util.SnackbarErrorUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    lazyPagingItems: LazyPagingItems<PicsumImageUiState>,
    onAction: (MainAction) -> Unit
) {
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(lazyPagingItems.loadState) {
        when {
            lazyPagingItems.loadState.refresh is LoadState.Error -> {
                val loadStateError = lazyPagingItems.loadState.refresh as LoadState.Error
                showSnackbar(
                    context = context,
                    error = loadStateError.error,
                    coroutineScope = coroutineScope,
                    snackbarHostState = snackbarHostState,
                    lazyPagingItems = lazyPagingItems
                )
            }

            lazyPagingItems.loadState.append is LoadState.Error -> {
                val loadStateError = lazyPagingItems.loadState.append as LoadState.Error
                showSnackbar(
                    context = context,
                    error = loadStateError.error,
                    coroutineScope = coroutineScope,
                    snackbarHostState = snackbarHostState,
                    lazyPagingItems = lazyPagingItems
                )
            }

            else -> {

            }
        }
    }

    Scaffold(
        topBar = {
            MainTopBar()
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { innerPadding ->
        MainContent(
            innerPadding = innerPadding,
            lazyPagingItems = lazyPagingItems,
            onAction = onAction
        )
    }
}

private fun showSnackbar(
    context: Context,
    error: Throwable,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    lazyPagingItems: LazyPagingItems<PicsumImageUiState>
) {
    val messageRes = SnackbarErrorUtil.getSnackbarMessageRes(error)

    coroutineScope.launch {
        val result = snackbarHostState.showSnackbar(
            message = context.getString(messageRes),
            actionLabel = context.getString(R.string.action_retry),
            duration = SnackbarDuration.Indefinite,
            withDismissAction = true
        )

        when (result) {
            SnackbarResult.Dismissed -> {

            }

            SnackbarResult.ActionPerformed -> {
                lazyPagingItems.refresh()
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun MainTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Icon(
                modifier = Modifier.height(24.dp),
                painter = painterResource(id = R.drawable.ic_logo_miquido_text),
                contentDescription = stringResource(id = R.string.image_description_app_logo)
            )
        }
    )
}

@Composable
private fun MainContent(
    innerPadding: PaddingValues,
    lazyPagingItems: LazyPagingItems<PicsumImageUiState>,
    onAction: (MainAction) -> Unit
) {
    val isRefreshLoading = lazyPagingItems.loadState.refresh is LoadState.Loading
    val isAppendLoading = lazyPagingItems.loadState.append is LoadState.Loading
    val isLoading = isRefreshLoading || isAppendLoading

    Box(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {
        Column {
            HorizontalDivider()
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(
                    count = lazyPagingItems.itemCount,
                    key = lazyPagingItems.itemKey { it.id }
                ) { index ->
                    val picsumImage = lazyPagingItems[index]!!
                    PicsumImageCard(
                        modifier = Modifier.animateItem(),
                        picsumImageUiState = picsumImage,
                        onClick = {
                            val action = MainAction.NavigateToDetails(picsumImage)
                            onAction(action)
                        }
                    )
                }
                if (lazyPagingItems.itemCount != 0 && isLoading) {
                    item {
                        Box(modifier = Modifier.padding(16.dp)) {
                            CustomCircularProgressIndicator(modifier = Modifier.size(48.dp))
                        }
                    }
                }
            }
        }
        if (lazyPagingItems.itemCount == 0 && isLoading) {
            CustomLinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MiquidoRecruitmentTaskTheme {
        val pagingData = PreviewDataUtil.getPicsumImageUiPagingDataFlow(20)
        val lazyPagingItems = pagingData.collectAsLazyPagingItems()
        MainScreen(
            lazyPagingItems = lazyPagingItems,
            onAction = {}
        )
    }
}