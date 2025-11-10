package com.example.libraryapp_lab4.remote.dto

import com.example.libraryapp_lab4.data.model.Book
import com.example.libraryapp_lab4.data.model.BookCategory
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object pentru Cărți.
 * Mapează exact structura JSON primită de la API.
 * Adnotarea @Serializable este necesară pentru ca Kotlinx.serialization să funcționeze.
 */
@Serializable
data class BookDto(
    val id: Int,
    val title: String,
    val author: String,
    val year: Int,
    val description: String,
    val category: String // În DTO, categoria este un simplu String
)

/**
 * Funcție de extensie pentru a mapa un BookDto (model de rețea)
 * la un Book (model de domeniu/UI).
 * Aceasta decuplează logica de rețea de restul aplicației.
 */
fun BookDto.toDomainModel(): Book {
    return Book(
        id = this.id,
        title = this.title,
        author = this.author,
        year = this.year,
        description = this.description,
        // Convertim String-ul la enum-ul nostru, cu o valoare default
        // în caz că API-ul trimite o categorie necunoscută.
        category = try {
            BookCategory.valueOf(this.category)
        } catch (e: IllegalArgumentException) {
            BookCategory.NON_FICTION // O valoare sigură de fallback
        }
    )
}