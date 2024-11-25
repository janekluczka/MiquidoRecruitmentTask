package com.luczka.miquidorecruitment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.luczka.miquidorecruitment.ui.navigation.MiquidoRecruitmentTaskNavHost
import com.luczka.miquidorecruitment.ui.theme.MiquidoRecruitmentTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiquidoRecruitmentTaskTheme {
                val navController = rememberNavController()
                MiquidoRecruitmentTaskNavHost(navController)
            }
        }
    }
}

