package com.example.a6starter

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Home")
    object Users : Screen("users", "Users")
    object Create : Screen("create", "Create")
    object UserNotes : Screen("user_notes/{userId}", "User Notes") {
        fun createRoute(userId: Int) = "user_notes/$userId"
    }
    object Error : Screen("error", "Error")
}