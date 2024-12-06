package com.android.medicineapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.android.medicineapp.ui.detail.DetailScreen
import com.android.medicineapp.ui.home.HomeScreen
import com.android.medicineapp.ui.login.LoginScreen
import com.android.medicineapp.ui.login.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isLoading by remember { mutableStateOf(true) }
            val userViewModel: UserViewModel = hiltViewModel()
            val user by userViewModel.user.collectAsState()
            LaunchedEffect(user) {
                delay(1000) // 1 second delay
                isLoading = false
            }
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                // Decide the start destination based on user state
                val startDestination = if (user != null) "home" else "login"
                // Set up navigation graph
                NavGraph(userViewModel = userViewModel, startDestination = startDestination)
            }
        }
    }

    // Composable function for setting up the navigation graph
    @Composable
    fun NavGraph(userViewModel: UserViewModel, startDestination: String) {
        val navController = rememberNavController()

        LaunchedEffect(startDestination) {
            navController.navigate(startDestination) {
                popUpTo("login") { inclusive = true }
            }
        }

        //Navigation graph with all composable routes
        NavHost(navController = navController, startDestination = startDestination) {
            composable("login") {
                LoginScreen(viewModel = userViewModel,
                    onLoginSuccess = {
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true } // Removes login from back stack
                            launchSingleTop = true // Prevents adding the same screen to back stack
                        }
                    })
            }

            composable("home") {
                HomeScreen(
                    viewModel = userViewModel,
                    onLogout = {
                        // Logout and navigate to the login screen
                        userViewModel.logout()  // Clear session
                        navController.navigate("login") {
                            popUpTo("home") { inclusive = true }
                        }
                    },
                    onMedicineSelected = { medicine ->
                        navController.navigate("detail/${medicine.name}/${medicine.dose}/${medicine.strength}")
                    }
                )
            }

            composable(
                "detail/{name}/{dose}/{strength}",
                arguments = listOf(
                    navArgument("name") { type = NavType.StringType },
                    navArgument("dose") { type = NavType.StringType },
                    navArgument("strength") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val name = backStackEntry.arguments?.getString("name") ?: ""
                val dose = backStackEntry.arguments?.getString("dose") ?: ""
                val strength = backStackEntry.arguments?.getString("strength") ?: ""
                DetailScreen(name = name, dose = dose, strength = strength)
            }
        }
    }
}


