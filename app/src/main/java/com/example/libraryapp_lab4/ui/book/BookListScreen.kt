package com.example.libraryapp_lab4.ui.book

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.libraryapp_lab4.data.model.Book

/**
 * Ecranul reactiv care afișează lista de cărți.
 *
 * @param viewModel ViewModel-ul injectat de Hilt.
 * @param onBookClick O funcție lambda care este apelată când se dă click pe o carte.
 *                    Transmite ID-ul cărții selectate.
 */
@Composable
fun BookListScreen(
    viewModel: BookViewModel = hiltViewModel(),
    onBookClick: (Int) -> Unit
) {
    // Colectăm starea din StateFlow.
    // `collectAsState` transformă un Flow într-un State<T> pe care Compose îl poate observa.
    // Când valoarea din viewModel.books se schimbă, această variabilă se va actualiza
    // și Composable-ul se va recompune automat.
    val uiState by viewModel.booksUiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        is BooksUiState.Loading -> {
            // Afișăm un indicator de progres în centrul ecranului
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is BooksUiState.Success -> {
            // Afișăm lista de cărți
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.books) { book ->
                    BookCard(book = book, onClick = { onBookClick(book.id) })
                }
            }
        }
        is BooksUiState.Error -> {
            // Afișăm mesajul de eroare
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

/**
 * Composable pentru un singur card de carte în listă.
 *
 * @param book Cartea de afișat.
 * @param onClick Acțiunea executată la click.
 */
@Composable
fun BookCard(book: Book, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        // Culoarea cardului este determinată de categoria cărții.
        colors = CardDefaults.cardColors(
            containerColor = book.category.color
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "by ${book.author}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Published in ${book.year}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}