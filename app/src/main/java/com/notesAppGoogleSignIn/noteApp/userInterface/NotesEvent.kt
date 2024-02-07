package com.notesAppGoogleSignIn.noteApp.userInterface

import com.notesAppGoogleSignIn.noteApp.data.Note


sealed interface NotesEvent {
    data object SortNotes : NotesEvent

    data class DeleteNote(val note: Note) : NotesEvent

    data class SaveNote(
        val title: String,
        val desc: String
    ): NotesEvent

}
