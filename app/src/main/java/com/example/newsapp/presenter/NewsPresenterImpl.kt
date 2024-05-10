package com.example.newsapp.presenter

import com.example.newsapp.model.ApiService
import com.example.newsapp.model.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsPresenterImpl(private val view:NewsView,private val apiService: ApiService):NewsPresenter {
    override fun fetchArticles(country: String, apiKey: String) {
        apiService.getNews(country, apiKey).enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    val newsResponse = response.body()
                    if (newsResponse != null) {
                        view.displayArticles(newsResponse.articles)
                    } else {
                        view.showError("Response body is null")
                    }
                } else {
                    view.showError("Failed to fetch articles")
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                view.showError("Network error: ${t.message}")
            }
        })
    }
}