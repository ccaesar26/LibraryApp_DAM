package com.example.libraryapp_lab4.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.libraryapp_lab4.data.local.entity.AuthorEntity
import com.example.libraryapp_lab4.data.local.entity.BookEntity
import com.example.libraryapp_lab4.data.model.Book
import com.example.libraryapp_lab4.data.model.BookCategory

data class BookWithAuthor(
    @Embedded val book: BookEntity,
    @Relation(
        parentColumn = "authorId", // from BookEntity
        entityColumn = "id"        // from AuthorEntity
    )
    val author: AuthorEntity
)

fun BookWithAuthor.toDomainModel(): Book {
    return Book(
        id = this.book.id,
        title = this.book.title,
        author = this.author.name,
        year = this.book.year,
        description = this.book.description,
        category = try {
            BookCategory.valueOf(this.book.category)
        } catch (e: Exception) {
            BookCategory.NON_FICTION
        }
    )
}