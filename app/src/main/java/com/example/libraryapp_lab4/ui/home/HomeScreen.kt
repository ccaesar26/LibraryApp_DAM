package com.example.libraryapp_lab4.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.libraryapp_lab4.R

/**
 * Composable pentru ecranul de "Acasă".
 * Afișează un mesaj de bun venit.
 */
@Composable
fun HomeScreen() {
    // Column aliniază elementele pe verticală.
    // fillMaxSize() face ca Column să ocupe tot spațiul disponibil.
    // Arrangement.Center și Alignment.CenterHorizontally centrează conținutul.
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.welcome_message),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}