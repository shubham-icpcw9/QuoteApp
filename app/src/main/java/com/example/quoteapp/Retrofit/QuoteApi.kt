package com.example.quoteapp.Retrofit

import com.example.quoteapp.Models.QuoteListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface QuoteApi {

    @GET("/quotes")
    suspend fun getQuotes(@Query("page") page: Int): QuoteListResponse
}