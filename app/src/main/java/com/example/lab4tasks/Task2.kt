package com.example.lab4tasks

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

private val COUNTER_KEY = intPreferencesKey("counter")

private const val DataStoreName = "counter_preferences"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DataStoreName)

@Composable
fun CounterTK2Screen() {
    val context = LocalContext.current
    val counterDataStore = context.dataStore

    var counter by remember { mutableStateOf(0) }

    fun loadData() = runBlocking {
        counter = counterDataStore.data.first()[COUNTER_KEY] ?: 0
    }

    fun saveData() = runBlocking {
        counterDataStore.edit { preferences ->
            preferences[COUNTER_KEY] = counter
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Counter: $counter")
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.Center) {
            Button(onClick = { loadData() }) {
                Text("Load")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {
                counter++
                saveData()
            }) {
                Text("Increment & Save")
            }
        }
    }
}