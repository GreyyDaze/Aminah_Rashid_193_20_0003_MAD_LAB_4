package com.example.lab4tasks

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.io.FileNotFoundException

private const val NOTE_FILENAME = "Notes.txt"

@Composable
fun NoteTakingAppTK3(context: Context = LocalContext.current.applicationContext) {
    val notes = remember { mutableStateListOf<String>() }
    var newNote by remember { mutableStateOf("") }

    fun saveNotes(content: String) {
        val fileOutputStream = context.openFileOutput(NOTE_FILENAME, Context.MODE_PRIVATE)
        fileOutputStream.write(content.toByteArray())
        fileOutputStream.close()
        notes.add(newNote)
    }

    fun loadNotes() {
        try {
            val fileInputStream = context.openFileInput(NOTE_FILENAME)
            val noteContent = fileInputStream.readBytes().toString(Charsets.UTF_8)
            notes.clear() // Clear existing notes before loading new content
            notes.addAll(noteContent.split("\n").filter { it.isNotBlank() }) // Split content by newline for individual notes
            fileInputStream.close()
        } catch (e: FileNotFoundException) {
            // Handle the exception, e.g., initialize the notes to an empty list
            notes.clear()
        }
    }

    LaunchedEffect(Unit) {
        loadNotes()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = newNote,
            onValueChange = { newNote = it },
            label = { Text("Enter Note") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.Black)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = {
                val currentNotes = notes.joinToString("\n") // Combine notes with newline separator
                saveNotes(currentNotes + "\n" + newNote) // Append new note to existing content
                newNote = ""
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Note", tint = Color.White)
            }
        }
        if (notes.isNotEmpty()) {
            Text("Saved Notes:", color = Color.Green)
            Spacer(modifier = Modifier.height(8.dp))
            notes.forEach { note ->
                Text(note)
            }
        } else {
            Text("No notes saved yet.")
        }
    }
}
