package com.luczka.miquidorecruitment.ui.util

import com.luczka.miquidorecruitment.R
import com.luczka.miquidorecruitment.domain.models.PicsumError

object SnackbarErrorUtil {

    fun getSnackbarMessageRes(throwable: Throwable): Int {
        return when (throwable) {
            is PicsumError.NetworkError -> R.string.error_network
            is PicsumError.ServerError -> R.string.error_server
            else -> R.string.error_unknown
        }
    }
}