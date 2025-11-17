package com.example.libraryapp_lab4.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.libraryapp_lab4.data.local.entity.AuthorEntity
import com.example.libraryapp_lab4.data.local.entity.BookEntity
import com.example.libraryapp_lab4.data.local.relation.BookWithAuthor
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    /**
     * Inserts an author and returns its newly generated ID.
     * The `Long` return type is required by Room for this operation.
     * OnConflictStrategy is changed to REPLACE in case author details change.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuthor(author: AuthorEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: BookEntity)

    // --- Operații de Interogare ---

    /**
     * New method to find an author by their unique name.
     * This is crucial for checking if an author already exists before inserting.
     */
    @Query("SELECT * FROM authors WHERE name = :name LIMIT 1")
    suspend fun getAuthorByName(name: String): AuthorEntity?

    @Transaction
    @Query("SELECT * FROM books WHERE id = :id LIMIT 1")
    suspend fun getBookWithAuthorById(id: Int): BookWithAuthor?


    /**
     * This method for getting all books remains unchanged, as the logic
     * is handled by the updated @Relation annotation.
     */
    @Transaction
    @Query("SELECT * FROM books")
    fun getBooksWithAuthors(): Flow<List<BookWithAuthor>>
}