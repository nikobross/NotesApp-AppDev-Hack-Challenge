package com.example.a6starter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.a6starter.ui.theme.A6StarterTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val notesViewModel: NotesViewModel = viewModel()
            A6StarterTheme {
                SimpleNotesApp(notesViewModel)
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    A6StarterTheme {
        Greeting("Android")
    }
}

@Preview(showBackground = true)
@Composable
fun UsersScreenPreview() {
    val navController = rememberNavController()
    A6StarterTheme {
        UsersScreen(viewModel = NotesViewModel(), navController = navController)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    A6StarterTheme {
        HomeScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun CreateScreenPreview() {
    val navController = rememberNavController()
    A6StarterTheme {
        CreateScreen(viewModel = NotesViewModel(), navController = navController)
    }
}
