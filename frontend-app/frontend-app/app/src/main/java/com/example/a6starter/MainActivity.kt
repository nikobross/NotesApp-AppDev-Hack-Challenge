package com.example.a6starter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.a6starter.ui.theme.A6StarterTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a6starter.repository.RepositoryProvider
import com.example.a6starter.viewmodel.NotesViewModelFactory
import com.example.a6starter.viewmodel.NotesViewModel



@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = NotesViewModelFactory(RepositoryProvider.repository)

        setContent {
            A6StarterTheme {
                val notesViewModel: NotesViewModel = viewModel(factory = factory)
                NavigationGraph(viewModel = notesViewModel)
            }
        }
    }
}




