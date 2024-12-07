package com.example.a6starter
//
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.navigation.*
//import androidx.navigation.compose.*
//
//
//@Composable
//fun NavigationGraph(
//    navController: NavHostController,
//    viewModel: NotesViewModel,
//    modifier: Modifier = Modifier
//) {
//    NavHost(
//        navController = navController,
//        startDestination = Screen.Home.route,
//        modifier = modifier
//    ) {
//        composable(Screen.Home.route) {
//            HomeScreen()
//        }
//        composable(Screen.Users.route) {
//            UsersScreen(viewModel = viewModel, navController = navController)
//        }
//        composable(Screen.Create.route) {
//            CreateScreen(viewModel = viewModel, navController = navController)
//        }
//        composable(
//            route = "${Screen.UserNotes.route}/{userId}",
//            arguments = listOf(navArgument("userId") { type = NavType.IntType })
//        ) { backStackEntry ->
//            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
//            UserNotesScreen(userId = userId, viewModel = viewModel, navController = navController)
//        }
//    }
//}

// NavigationGraph.kt

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.*

@Composable
fun NavigationGraph(
    navController: NavHostController,
    viewModel: NotesViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
//                onGetStarted = { navController.navigate(Screen.Users.route) }
            )
        }
        composable(Screen.Users.route) {
            UsersScreen(viewModel = viewModel, navController = navController)
        }
        composable(Screen.Create.route) {
            CreateScreen(viewModel = viewModel, navController = navController)
        }
        // Dynamic route for UserNotes screen
        composable(
            route = "${Screen.UserNotes.route}/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            UserNotesScreen(userId = userId, viewModel = viewModel, navController = navController)
        }
    }
}
