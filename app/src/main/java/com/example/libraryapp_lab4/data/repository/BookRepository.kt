package com.example.libraryapp_lab4.data.repository

import androidx.room.withTransaction
import com.example.libraryapp_lab4.data.local.AppDatabase
import com.example.libraryapp_lab4.data.local.entity.AuthorEntity
import com.example.libraryapp_lab4.data.local.entity.BookEntity
import com.example.libraryapp_lab4.data.local.relation.toDomainModel
import com.example.libraryapp_lab4.data.model.Book
import com.example.libraryapp_lab4.data.model.BookCategory
import com.example.libraryapp_lab4.remote.BookApiService
import com.example.libraryapp_lab4.remote.dto.toDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * The repository acts as the single point of contact for data.
 * It manages the synchronization between the remote API (network) and the local cache (database).
 *
 * @param apiService The service to fetch data from the network.
 * @param db The Room database instance, injected to allow for transaction control.
 */
@Singleton
class BookRepository @Inject constructor(
    private val apiService: BookApiService,
    private val db: AppDatabase
) {
    // We can get the DAO from the db instance.
    private val bookDao = db.bookDao()

    /**
     * SINGLE SOURCE OF TRUTH (SSoT).
     * This function exposes a Flow of book lists directly from the database.
     * The ViewModel and UI will observe this Flow. Any changes in the database
     * (e.g., after a network refresh) will be automatically propagated to the UI.
     *
     * @return A Flow emitting a list of `Book` domain models.
     */
    fun getAllBooks(): Flow<List<Book>> {
        return bookDao.getBooksWithAuthors().map { bookWithAuthorList ->
            // Map the database relation objects to clean domain models for the UI.
            bookWithAuthorList.map { it.toDomainModel() }
        }
    }

    /**
     * Găsește o singură carte în baza de date locală pe baza ID-ului.
     * @return Cartea găsită sau null.
     */
    suspend fun getBookById(id: Int): Book? {
        return bookDao.getBookWithAuthorById(id)?.toDomainModel()
    }

    /**
     * Refreshes the local book data from the remote API.
     * This is the only function that interacts with the network.
     * It fetches new data, and then saves it into the local database,
     * which will then automatically update the UI via the `getAllBooks()` Flow.
     */
    suspend fun refreshBooks() {
        try {
            // 1. Fetch the list of book DTOs from the remote API.
            val bookDtos = apiService.getBooks()

            // 2. Use a database transaction to ensure data integrity.
            // This guarantees that all operations within the block either
            // succeed together or fail together, preventing a partially updated state.
            db.withTransaction {
                bookDtos.forEach { bookDto ->
                    // 3. For each book, resolve its author's ID.
                    // Check if the author already exists in the 'authors' table.
                    val existingAuthor = bookDao.getAuthorByName(bookDto.author)
                    val authorId: Int

                    if (existingAuthor == null) {
                        // 4a. If the author does not exist, insert them into the database
                        // and retrieve the newly auto-generated primary key (ID).
                        authorId = bookDao.insertAuthor(AuthorEntity(name = bookDto.author)).toInt()
                    } else {
                        // 4b. If the author already exists, use their existing ID.
                        authorId = existingAuthor.id
                    }

                    // 5. Insert the book into the 'books' table using the resolved author ID.
                    // OnConflictStrategy.REPLACE ensures that if a book with the same ID
                    // exists, it will be updated with the new data.
                    bookDao.insertBook(
                        BookEntity(
                            id = bookDto.id,
                            title = bookDto.title,
                            year = bookDto.year,
                            description = bookDto.description,
                            category = bookDto.category,
                            authorId = authorId // Use the correct foreign key
                        )
                    )
                }
            }
        } catch (e: Exception) {
            // If any network or database error occurs, re-throw the exception
            // so it can be caught and handled in the ViewModel (e.g., to show an error message).
            throw e
        }
    }
}