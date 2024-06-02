package com.dbtechprojects.photonotes.ui.EditNote

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.dbtechprojects.photonotes.Constants
import com.dbtechprojects.photonotes.PhotoNotesApp
import com.dbtechprojects.photonotes.R
import com.dbtechprojects.photonotes.model.Note
import com.dbtechprojects.photonotes.ui.NotesList.NotesFab
import com.dbtechprojects.photonotes.ui.NotesViewModel
import com.dbtechprojects.photonotes.ui.theme.PhotoNotesTheme
import com.dbtechprojects.photonotes.ui.theme.Purple700
import com.dbtechprojects.photonotes.ui.theme.Purple80
import com.dbtechprojects.photonotes.ui.theme.PurpleGrey80
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NoteEditScreen(noteId: Int, navController: NavController, viewModel: NotesViewModel) {
    val scope = rememberCoroutineScope()
    val note = remember {
        mutableStateOf(Constants.noteDetailPlaceHolder)
    }

    val currentNote = remember { mutableStateOf(note.value.note) }
    val currentTitle = remember { mutableStateOf(note.value.title) }
    val saveButtonState = remember { mutableStateOf(false) }
    val  currentdeadline = remember { mutableStateOf(note.value.deadline) }

    LaunchedEffect(true) {
        scope.launch(Dispatchers.IO) {
            note.value = viewModel.getNote(noteId) ?: Constants.noteDetailPlaceHolder
            currentNote.value = note.value.note
            currentTitle.value = note.value.title
            currentdeadline.value= note.value.deadline

        }
    }



    PhotoNotesTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.primary) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = "Update Your Note") }
                    )
                },
                content = {

                    Column(
                        Modifier
                            .padding(12.dp)
                            .fillMaxSize()
                    ) {

                        TextField(
                            value = currentTitle.value,
                            modifier = Modifier
                                .background(color= PurpleGrey80)
                                .fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(
                                cursorColor = Color.Black,
                                focusedLabelColor = Color.Black,
                            ),
                            onValueChange = { value ->
                                currentTitle.value = value
                                if (currentTitle.value != note.value.title) {
                                    saveButtonState.value = true
                                } else if (currentNote.value == note.value.note &&
                                    currentTitle.value == note.value.title
                                ) {
                                    saveButtonState.value = false
                                }
                            },
                            label = { Text(text = "Title") }
                        )
                        Spacer(modifier = Modifier.padding(12.dp))
                        TextField(
                            value = currentNote.value,
                            modifier = Modifier
                                .fillMaxHeight(0.61f)
                                .background(color= PurpleGrey80)
                                .fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(
                                cursorColor = Color.Black,
                                focusedLabelColor = Color.Black,
                            ),
                            onValueChange = { value ->
                                currentNote.value = value
                                if (currentNote.value != note.value.note) {
                                    saveButtonState.value = true
                                } else if (currentNote.value == note.value.note &&
                                    currentTitle.value == note.value.title
                                ) {
                                    saveButtonState.value = false
                                }
                            },
                            label = { Text(text = "Body") }
                        )
                        Spacer(modifier = Modifier.padding(12.dp))
                        TextField(
                            value = currentdeadline.value,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color= PurpleGrey80),
                            colors = TextFieldDefaults.textFieldColors(
                                cursorColor = Color.Black,
                                focusedLabelColor = Color.Black,
                            ),
                            onValueChange = { value ->
                                currentdeadline.value = value
                                if (currentdeadline.value != note.value.deadline) {
                                    saveButtonState.value = true
                                } else if (currentNote.value == note.value.note &&
                                    currentTitle.value == note.value.title && currentdeadline.value == note.value.deadline
                                ) {
                                    saveButtonState.value = false
                                }
                            },
                            label = { Text(text = "Deadline") }
                        )
                        Spacer(modifier = Modifier.padding(15.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                onClick = {
                                    viewModel.updateNote(
                                        Note(
                                            id = note.value.id,
                                            note = currentNote.value,
                                            title = currentTitle.value,
                                            deadline = currentdeadline.value
                                        )
                                    )
                                    navController.popBackStack()

                                },
                                shape = RoundedCornerShape(15.dp),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Purple700
                                ),
                                modifier = Modifier
                                    .height(65.dp)
                                    .width(150.dp)

                            ) {
                                Text(
                                    text = "Save Note",
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }

            )
        }
    }
}