package com.dbtechprojects.photonotes.ui.createNote
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dbtechprojects.photonotes.ui.NotesViewModel
import com.dbtechprojects.photonotes.ui.theme.PhotoNotesTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CreateNoteScreen(
    navController: NavController,
    viewModel: NotesViewModel
) {


    val currentNote = remember { mutableStateOf("") }
    val currentTitle = remember { mutableStateOf("") }
    val saveButtonState = remember { mutableStateOf(false) }
     val currentdeadline = remember { mutableStateOf("") }



         PhotoNotesTheme {
        // A surface container using the 'background' color from the theme

            Scaffold(
                topBar = {
                    Button(
                            onClick = {  viewModel.createNote(
                        currentTitle.value,
                        currentNote.value,
                        currentdeadline.value
                    )
                        navController.popBackStack() }) {
                        Text("New Note")
                    }

                },
                content = {
                    Column(
                        Modifier
                            .padding(12.dp)
                            .fillMaxSize()
                    ) {


                        TextField(
                            value = currentTitle.value,
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(
                                cursorColor = Color.Black,
                                focusedLabelColor = Color.Black,
                            ),
                            onValueChange = { value ->
                                currentTitle.value = value
                                saveButtonState.value =
                                    currentTitle.value != "" && currentNote.value != ""
                            },
                            label = { Text(text = "Title") }
                        )
                        Spacer(modifier = Modifier.padding(12.dp))
                        TextField(
                            value = currentNote.value,
                            colors = TextFieldDefaults.textFieldColors(
                                cursorColor = Color.Black,
                                focusedLabelColor = Color.Black,
                            ),
                            modifier = Modifier
                                .fillMaxHeight(0.5f)
                                .fillMaxWidth(),
                            onValueChange = { value ->
                                currentNote.value = value
                                saveButtonState.value =
                                    currentTitle.value != "" && currentNote.value != ""
                            },
                            label = { Text(text = "Body") }
                        )
                        Spacer(modifier = Modifier.padding(12.dp))
                        TextField(
                            value = currentdeadline.value,
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(
                                cursorColor = Color.Black,
                                focusedLabelColor = Color.Black,
                            ),
                            onValueChange = { value ->
                                currentdeadline.value = value
                                saveButtonState.value =
                                    currentTitle.value != "" && currentNote.value != "" && currentdeadline.value != ""
                            },
                            label = { Text(text = "Deadline") }
                        )

                    }
                }

            )

    }
}