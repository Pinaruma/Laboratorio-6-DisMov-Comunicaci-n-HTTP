package com.example.lab_http_googleapis.data.repository

import com.example.lab_http_googleapis.BuildConfig
import com.example.lab_http_googleapis.data.model.BookItem
import com.example.lab_http_googleapis.data.remote.RetrofitClient

class BooksRepository {
    suspend fun searchBooks(query: String): List<BookItem> {
        val response = RetrofitClient.api.searchBooks(
            query = query,
            apiKey = BuildConfig.GOOGLE_BOOKS_API_KEY
        )
        return response.items ?: emptyList()
    }
}
