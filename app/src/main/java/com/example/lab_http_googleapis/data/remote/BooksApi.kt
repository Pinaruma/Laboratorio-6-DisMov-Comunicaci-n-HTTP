package com.example.lab_http_googleapis.data.remote

import com.example.lab_http_googleapis.data.model.BooksResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApi {
    @GET("books/v1/volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("key") apiKey: String,
        @Query("maxResults") maxResults: Int = 20
    ): BooksResponse
}
