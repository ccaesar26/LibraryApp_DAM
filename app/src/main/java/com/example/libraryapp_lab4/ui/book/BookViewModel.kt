package com.example.libraryapp_lab4.ui.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryapp_lab4.data.model.Book
import com.example.libraryapp_lab4.data.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
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
        // La inițializarea ViewModel-ului, facem două lucruri în paralel:
        // 1. Începem să observăm baza de date pentru orice modificări.
        observeBooks()
        // 2. Declanșăm o reîmprospătare a datelor de la rețea.
        refreshBooks()
    }

    /**
     * Observă încontinuu sursa unică a adevărului (baza de date).
     * De fiecare dată când datele din DB se schimbă, acest bloc va emite
     * o nouă stare de Succes către UI.
     */
    private fun observeBooks() {
        viewModelScope.launch {
            repository.getAllBooks()
                // În caz de eroare la citirea din DB (rar, dar posibil)
                .catch { e ->
                    _booksUiState.value = BooksUiState.Error("Failed to load data from local database.")
                }
                .collect { books ->
                    // Transformăm lista de cărți primită de la DB într-o stare de Succes.
                    _booksUiState.value = BooksUiState.Success(books)
                }
        }
    }

    /**
     * Declanșează o acțiune de reîmprospătare a datelor de la rețea.
     * Gestionează stările de Loading și Error.
     */
    private fun refreshBooks() {
        viewModelScope.launch {
            try {
                // Setăm starea de Loading DOAR dacă nu avem deja date afișate.
                // Astfel evităm un flicker al UI-ului la reîmprospătări de fundal.
                if (_booksUiState.value !is BooksUiState.Success) {
                    _booksUiState.value = BooksUiState.Loading
                }

                // Apelăm funcția suspend din repository pentru a aduce datele noi.
                repository.refreshBooks()
                // NU setăm starea de Succes aici. `observeBooks` se va ocupa de asta
                // automat, odată ce noile date sunt scrise în baza de date.
            } catch (e: UnknownHostException) {
                // Dacă nu există conexiune, setăm o eroare specifică.
                // UI-ul va continua să afișeze datele vechi din DB, dacă există.
                _booksUiState.value = BooksUiState.Error("No internet connection. Please check your settings.")
            } catch (e: Exception) {
                // Pentru orice altă eroare de rețea.
                _booksUiState.value = BooksUiState.Error("An unexpected error occurred: ${e.message}")
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