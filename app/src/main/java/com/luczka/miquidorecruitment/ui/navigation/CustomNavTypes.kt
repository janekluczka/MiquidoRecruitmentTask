package com.luczka.miquidorecruitment.ui.navigation

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.luczka.miquidorecruitment.ui.models.PicsumImageUiState
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object CustomNavTypes {

    val MethodUiStateNavType = object : NavType<PicsumImageUiState>(isNullableAllowed = false) {
        override fun put(bundle: Bundle, key: String, value: PicsumImageUiState) {
            bundle.putParcelable(key, value)
        }

        override fun get(bundle: Bundle, key: String): PicsumImageUiState {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable(key, PicsumImageUiState::class.java)!!
            } else {
                @Suppress("DEPRECATION")
                bundle.getParcelable(key)!!
            }
        }

        override fun serializeAsValue(value: PicsumImageUiState): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun parseValue(value: String): PicsumImageUiState {
            return Json.decodeFromString<PicsumImageUiState>(value)
        }
    }
}