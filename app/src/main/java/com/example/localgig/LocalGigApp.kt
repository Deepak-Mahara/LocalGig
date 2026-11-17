package com.example.localgig

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.localgig.core.data.UserPreferences
import com.example.localgig.feature.dashboard.DashboardScreen
import com.example.localgig.feature.login.LoginScreen
import kotlinx.coroutines.launch

@Composable
fun LocalGigApp() {
    val context = LocalContext.current
    val userPreferences = remember { UserPreferences(context) }
    val scope = rememberCoroutineScope()

    // Collect states from DataStore
    val isLoggedIn by userPreferences.isLoggedInFlow.collectAsState(initial = null)
    val userRole by userPreferences.userRoleFlow.collectAsState(initial = null)

    // Show a loading spinner while DataStore reads the initial value
    if (isLoggedIn == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val navController = rememberNavController()
    val startDestination = if (isLoggedIn == true) "dashboard" else "login"

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(
                onLoginAsLocal = {
                    scope.launch {
                        userPreferences.saveLoginState(true, "LOCAL")
                        navController.navigate("dashboard") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                },
                onLoginAsGig = {
                    scope.launch {
                        userPreferences.saveLoginState(true, "GIG")
                        navController.navigate("dashboard") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                }
            )
        }

        composable("dashboard") {
            // Determine initial role for the dashboard based on stored preference
            val initialIsLocal = userRole == "LOCAL"

            // Pass this to your DashboardScreen
            DashboardScreen(initialIsLocalRole = initialIsLocal)
        }
    }
}