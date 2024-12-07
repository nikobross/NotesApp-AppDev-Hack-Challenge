package com.example.a6starter


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.a6starter.viewmodel.NotesViewModel
import com.example.a6starter.ui.screens.*

@Composable
fun NavigationGraph(startDestination: String = Screen.Users.route, viewModel: NotesViewModel) {
    val navController = rememberNavController()

    val items = listOf(
        Screen.Home,
        Screen.Users,
        Screen.Create
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val showBottomBar = items.any { it.route == currentDestination?.route }

    Scaffold(
        bottomBar = { if (showBottomBar) BottomNavigationBar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen()
            }
            composable(Screen.Users.route) {
                UsersScreen(viewModel = viewModel, navController = navController)
            }
            composable(Screen.Create.route) {
                CreateScreen(viewModel = viewModel, navController = navController)
            }
            composable(
                Screen.UserNotes.route,
                arguments = listOf(navArgument("userId") { type = NavType.IntType })
            ) { backStackEntry ->
                val userId = backStackEntry.arguments?.getInt("userId")
                if (userId != null) {
                    UserNotesScreen(viewModel = viewModel, navController = navController, userId = userId)
                } else {
                    ErrorScreen(message = "Invalid User ID", navController = navController)
                }
            }
            composable(Screen.Error.route) {
                ErrorScreen(message = "An unexpected error occurred.", navController = navController)
            }
        }
    }
}
