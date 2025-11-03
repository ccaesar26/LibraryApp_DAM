package com.example.libraryapp_lab4.ui.book

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

/**
 * Ecranul care afișează detaliile unei singure cărți.
 *
 * @param bookId ID-ul cărții de afișat (primit prin navigație).
 * @param viewModel ViewModel-ul injectat de Hilt.
 */
@Composable
fun BookDetailScreen(
    bookId: Int,
    viewModel: BookViewModel = hiltViewModel()
) {
    // De fiecare dată când `bookId` se schimbă,
    // acest bloc `LaunchedEffect` se va executa o singură dată.
    // Este locul ideal pentru a-i spune ViewModel-ului să încarce datele.
    LaunchedEffect(key1 = bookId) {
        viewModel.loadBookById(bookId)
    }

    // Colectăm starea cărții selectate din ViewModel.
    // Inițial, `book` va fi null. După ce `loadBookById` completează,
    // StateFlow-ul va emite o nouă valoare (cartea), iar UI-ul se va recomune.
    val book by viewModel.selectedBook.collectAsState()


    // Composable-ul se va redesena corect când `book` trece de la null la o valoare reală.
    if (book != null) {
        // Compilatorul Kotlin nu poate garanta că valoarea unei proprietăți delegate
        // nu se va schimba între momentul verificării (if (book != null)) și
        // momentul utilizării (Text(book.title, ...)).
        val currentBook = book!!
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(currentBook.title, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text("by ${currentBook.author}", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Published in ${currentBook.year}", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Category: ${currentBook.category.name}", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(16.dp))
            Text(currentBook.description, style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        // Această parte este vizibilă pentru o fracțiune de secundă, până se încarcă datele.
        // Într-o aplicație reală, aici am afișa un indicator de încărcare (CircularProgressIndicator).
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Loading book...")
        }
    }
}