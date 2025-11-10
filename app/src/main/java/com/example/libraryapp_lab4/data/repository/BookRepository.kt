package com.example.libraryapp_lab4.data.repository

import com.example.libraryapp_lab4.data.model.Book
import com.example.libraryapp_lab4.data.model.BookCategory
import com.example.libraryapp_lab4.remote.BookApiService
import com.example.libraryapp_lab4.remote.dto.toDomainModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository-ul gestionează sursele de date pentru cărți.
 * Momentan, folosește date hardcodate. În viitor, poate interoga o bază de date locală sau un API.
 *
 * @Inject constructor() îi spune lui Hilt cum să creeze o instanță a acestei clase.
 * @Singleton asigură că va exista o singură instanță a acestui repository în întreaga aplicație.
 */
@Singleton
class BookRepository @Inject constructor(
    private val apiService: BookApiService
) {
    // Cache simplu pentru a nu apela API-ul de fiecare dată
    private var cachedBooks: List<Book>? = null


    /**
     * Obține lista de cărți de la API, o mapează la modelul de domeniu
     * și o stochează într-un cache.
     */
    suspend fun getAllBooks(): List<Book> {
        return try {
            val books = apiService.getBooks().map { it.toDomainModel() }
            cachedBooks = books
            books
        } catch (e: Exception) {
            // Aici am putea loga eroarea
            // Într-o aplicație complexă, am putea returna date din cache dacă există,
            // chiar dacă apelul de rețea a eșuat.
            throw e // Aruncăm excepția mai departe pentru a fi prinsă în ViewModel
        }
    }

    /**
     * Găsește o carte după ID din lista deja încărcată în cache.
     */
    suspend fun getBookById(id: Int): Book? {
        // Asigură-te că lista este încărcată înainte de a căuta
        if (cachedBooks == null) {
            getAllBooks()
        }
        return cachedBooks?.find { it.id == id }
    }
}