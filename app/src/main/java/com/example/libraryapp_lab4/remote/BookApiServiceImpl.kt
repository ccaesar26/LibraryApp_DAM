package com.example.libraryapp_lab4.remote

import com.example.libraryapp_lab4.remote.dto.BookDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class BookApiServiceImpl @Inject constructor(
    private val httpClient: HttpClient
) : BookApiService {

    override suspend fun getBooks(): List<BookDto> {
        return httpClient.get("/books").body()
    }
}