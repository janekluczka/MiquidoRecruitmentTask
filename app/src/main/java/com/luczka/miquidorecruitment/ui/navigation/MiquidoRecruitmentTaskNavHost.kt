package com.luczka.miquidorecruitment.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import androidx.paging.compose.collectAsLazyPagingItems
import com.luczka.miquidorecruitment.ui.models.PicsumImageUiState
import com.luczka.miquidorecruitment.ui.screens.details.DetailsAction
import com.luczka.miquidorecruitment.ui.screens.details.DetailsScreen
import com.luczka.miquidorecruitment.ui.screens.details.DetailsViewModel
import com.luczka.miquidorecruitment.ui.screens.details.DetailsViewModelFactory
import com.luczka.miquidorecruitment.ui.screens.main.MainAction
import com.luczka.miquidorecruitment.ui.screens.main.MainScreen
import com.luczka.miquidorecruitment.ui.screens.main.MainViewModel
import kotlin.reflect.typeOf

@Composable
fun MiquidoRecruitmentTaskNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Routes.Main
    ) {
        composable<Routes.Main> {
            val viewModel = hiltViewModel<MainViewModel>()
            val pagingItems = viewModel.pagedPicsumImages.collectAsLazyPagingItems()
            MainScreen(
                lazyPagingItems = pagingItems,
                onAction = { action ->
                    when(action) {
                        is MainAction.NavigateToDetails -> navHostController.navigate(Routes.Details(action.picsumImage))
                    }
                }
            )
        }
        composable<Routes.Details>(
            typeMap = mapOf(
                typeOf<PicsumImageUiState>() to CustomNavTypes.MethodUiStateNavType
            ),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(durationMillis = 300, easing = LinearEasing)
                ) + slideIntoContainer(
                    animationSpec = tween(durationMillis = 300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(durationMillis = 300, easing = LinearEasing)
                ) + slideOutOfContainer(
                    animationSpec = tween(durationMillis = 300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) { backStackEntry ->
            val arguments = backStackEntry.toRoute<Routes.Details>()
            val viewModel = hiltViewModel<DetailsViewModel, DetailsViewModelFactory> { factory ->
                factory.create(arguments.picsumImage)
            }
            val uiState by viewModel.uiState.collectAsState()
            DetailsScreen(
                uiState = uiState,
                onAction = { action ->
                    when(action) {
                        is DetailsAction.NavigateUp -> navHostController.navigateUp()
                        else -> {}
                    }
                    viewModel.onAction(action)
                }
            )
        }
    }
}