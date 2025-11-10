package com.example.libraryapp_lab4.remote

import com.example.libraryapp_lab4.remote.dto.BookDto

interface BookApiService {
    suspend fun getBooks(): List<BookDto>
}