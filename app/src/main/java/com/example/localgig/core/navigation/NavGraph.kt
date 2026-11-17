package com.example.localgig.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.localgig.core.theme.LocalGigTheme
import com.example.localgig.feature.dashboard.DashboardScreen

@Composable
fun LocalGigNavHost() {
    val navController = rememberNavController()

    LocalGigTheme {
        NavHost(
            navController = navController,
            startDestination = Routes.Dashboard,
        ) {
            composable(Routes.Dashboard) {
                DashboardScreen()
            }
        }
    }
}
