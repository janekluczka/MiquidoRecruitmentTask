package com.luczka.miquidorecruitment.domain.models

sealed class PicsumError : Exception() {

    data object NetworkError : PicsumError() {
        private fun readResolve(): Any = NetworkError
    }

    data object ServerError : PicsumError() {
        private fun readResolve(): Any = ServerError
    }
}
