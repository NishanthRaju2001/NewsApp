package com.example.newsapp.model

import com.example.newsapp.model.Constant.END_POINT
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(END_POINT)
    fun getNews(
        @Query("country")country:String="us",
        @Query("apiKey")apiKey:String="0f5b9f492b2841b88bf23980a539d558"
    ): Call<NewsResponse>
}