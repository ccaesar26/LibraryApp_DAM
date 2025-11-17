package com.example.libraryapp_lab4.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.libraryapp_lab4.R

/**
 * Composable pentru ecranul de "Acasă".
 * Afișează un mesaj de bun venit.
 */
@Composable
fun HomeScreen(
    // Injectăm noul ViewModel
    viewModel: HomeViewModel = hiltViewModel()
) {
    // Colectăm numele utilizatorului din ViewModel într-un mod sigur dpdv al ciclului de viață
    val userName by viewModel.userName.collectAsStateWithLifecycle()

    // Stare locală pentru a gestiona textul din câmpul de input
    var textFieldValue by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Mesajul de salut dinamic
        Text(
            text = if (userName.isBlank()) {
                stringResource(id = R.string.welcome_message_default)
            } else {
                stringResource(id = R.string.welcome_message_personalized, userName)
            },
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Câmp de text pentru introducerea numelui
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = { textFieldValue = it },
            label = { Text("Enter your name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Buton pentru a salva numele
        Button(
            onClick = {
                // La click, apelăm funcția din ViewModel pentru a salva numele
                viewModel.saveUserName(textFieldValue)
                // Opțional: golim câmpul de text după salvare
                textFieldValue = ""
            },
            // Butonul este activ doar dacă s-a introdus text
            enabled = textFieldValue.isNotBlank()
        ) {
            Text("Save Name")
        }
    }
}