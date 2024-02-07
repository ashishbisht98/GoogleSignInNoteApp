package com.notesAppGoogleSignIn.noteApp.userInterface

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.notesAppGoogleSignIn.googleSignIn.model.User
import com.notesAppGoogleSignIn.noteApp.data.NotesDatabase

class MainActivity : ComponentActivity() {


    private val database: NotesDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            NotesDatabase::class.java,
            "notes.db"
        ).build()
    }

    private val viewModel by viewModels<NotesViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return NotesViewModel(database.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ForwardUserDetails(user = User("", ""))
                }
            }
        }
    }
}

    @Composable
    fun ForwardUserDetails(user: User) {
        val state by viewModel.state.collectAsState()
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "NotesScreen") {
            composable("NotesScreen") {
                NotesScreen(
                    user = user,
                    state = state,
                    navController = navController,
                    onEvent = viewModel::onEvent
                )
            }
            composable("AddNoteScreen") {
                AddNoteScreen(
                    state = state,
                    navController = navController,
                    onEvent = viewModel::onEvent
                )
            }
            composable("AddDetailsScreen") {
                AddDetailsScreen(
                    state = state,
                    navController = navController,
                    onEvent = viewModel::onEvent
                )
            }
        }

    }

