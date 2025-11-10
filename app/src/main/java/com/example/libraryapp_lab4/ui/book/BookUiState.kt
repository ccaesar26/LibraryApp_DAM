package com.example.libraryapp_lab4.ui.book

import com.example.libraryapp_lab4.data.model.Book

sealed interface BooksUiState {
    object Loading : BooksUiState
    data class Success(val books: List<Book>) : BooksUiState
    data class Error(val message: String) : BooksUiState
}