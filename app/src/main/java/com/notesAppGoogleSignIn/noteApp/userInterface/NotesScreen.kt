package com.notesAppGoogleSignIn.noteApp.userInterface

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.notesAppGoogleSignIn.R
import com.notesAppGoogleSignIn.googleSignIn.model.User

@Composable
fun NotesScreen(
    state: NoteState,
    navController: NavController,
    onEvent: (NotesEvent) -> Unit,
    user: User,
) {


    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    modifier = Modifier.weight(1F),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                        text = user.email,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineMedium,
                        fontSize = 30.sp
                    )
                Log.d("User",user.toString())
                Spacer(modifier = Modifier.height(10.dp))
                Text(text =" ${user.displayName}")

                IconButton(onClick = { onEvent(NotesEvent.SortNotes) }) {
                    Icon(
                        imageVector = Icons.Rounded.Build,
                        contentDescription = "Sort Notes",
                        modifier = Modifier.size(35.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                state.title.value = ""
                state.desc.value = ""
                navController.navigate("AddNoteScreen")
            }) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "")
            }
        }
    ) {
        LazyColumn(
            contentPadding = it,
           // verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        )
        {
            items(state.notes.size) { index ->
                NoteItem(state = state, index = index, onEvent = onEvent)
                Spacer(modifier = Modifier.padding(10.dp))
            }
        }
    }
}


@Composable
fun NoteItem(
    state: NoteState,
    index: Int,
    onEvent: (NotesEvent) -> Unit

) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(12.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = state.notes[index].title,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            //Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = state.notes[index].desc,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
        IconButton(onClick = {
            onEvent(NotesEvent.DeleteNote(state.notes[index]))
        }
        ) {
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = "Delete Note",
                modifier = Modifier.size(25.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

    }
}
