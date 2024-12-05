package com.example.a6starter

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Home")
    object Users : Screen("users", "Users")
    object Create : Screen("create", "Create")
    object UserNotes : Screen("user_notes", "User Notes") // For displaying notes of a user
}