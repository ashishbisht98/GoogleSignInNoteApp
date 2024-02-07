package com.notesAppGoogleSignIn.noteApp.userInterface

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.notesAppGoogleSignIn.noteApp.data.Note

data class NoteState (
    val notes:List<Note> = emptyList(),
    val title:MutableState<String> = mutableStateOf(""),
    val desc:MutableState<String> = mutableStateOf("")

)