package com.example.libraryapp_lab4.ui.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryapp_lab4.data.model.Book
import com.example.libraryapp_lab4.data.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * ViewModel pentru ecranele legate de cărți (listă și detalii).
 *
 * Utilizează StateFlow pentru a expune starea către UI într-un mod reactiv.
 * UI-ul va colecta date din aceste fluxuri și se va redesena automat la schimbări.
 */
@HiltViewModel
class BookViewModel @Inject constructor(
    private val repository: BookRepository
) : ViewModel() {

    private val _booksUiState = MutableStateFlow<BooksUiState>(BooksUiState.Loading)
    val booksUiState: StateFlow<BooksUiState> = _booksUiState.asStateFlow()

    // --- Starea pentru Cartea Selectată (Ecranul de Detalii) ---

    // [INTERN] Stare mutabilă pentru cartea vizualizată în detaliu.
    private val _selectedBook = MutableStateFlow<Book?>(null)

    // [PUBLIC] Stare imutabilă expusă pentru ecranul de detalii.
    val selectedBook: StateFlow<Book?> = _selectedBook.asStateFlow()


    /**
     * Blocul init este executat la crearea ViewModel-ului.
     * Aici inițiem încărcarea listei de cărți.
     */
    init {
        loadBooks()
    }

    /**
     * Încarcă lista de cărți din repository și actualizează starea `_books`.
     * Chiar dacă acum este o operație sincronă, folosim un model pregătit pentru asincron.
     */
    private fun loadBooks() {
        viewModelScope.launch {
            // 1. Emitem imediat starea de Loading
            _booksUiState.value = BooksUiState.Loading
            try {
                // 2. Apelăm repository-ul
                val books = repository.getAllBooks()

                // 3. Emitem starea de Success dacă totul a mers bine
                _booksUiState.value = BooksUiState.Success(books)
            } catch (e: UnknownHostException) {
                // 4. Prindem excepții specifice pentru mesaje de eroare clare
                _booksUiState.value =
                    BooksUiState.Error("No internet connection. Please check your settings.")
            } catch (e: Exception) {
                // Prindem orice altă excepție
                _booksUiState.value =
                    BooksUiState.Error("An unexpected error occurred: ${e.message}")
            }
        }
    }

    /**
     * Încarcă o carte specifică pe baza ID-ului și actualizează starea `_selectedBook`.
     * Această funcție va fi apelată de pe ecranul de detalii.
     *
     * @param id ID-ul cărții de încărcat.
     */
    fun loadBookById(id: Int) {
        viewModelScope.launch {
            try {
                val book = repository.getBookById(id)
                _selectedBook.value = book
            } catch (_: Exception) {
                // Aici am putea gestiona erorile specifice pentru încărcarea unei singure cărți
                _selectedBook.value = null // Sau putem păstra valoarea anterioară
            }
        }
    }
}