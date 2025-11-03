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

    // --- Starea pentru Lista de Cărți ---

    // [INTERN] Starea mutabilă. Poate fi modificată doar din interiorul ViewModel-ului.
    private val _books = MutableStateFlow<List<Book>>(emptyList())

    // [PUBLIC] Starea imutabilă, expusă către UI. UI-ul doar o poate citi/observa.
    val books: StateFlow<List<Book>> = _books.asStateFlow()

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
        // Într-o aplicație reală, repository.getAllBooks() ar fi o funcție `suspend`
        // și am rula-o într-un anume Dispatcher (ex: Dispatchers.IO).
        viewModelScope.launch {
            _books.value = repository.getAllBooks()
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
            _selectedBook.value = repository.getBookById(id)
        }
    }
}