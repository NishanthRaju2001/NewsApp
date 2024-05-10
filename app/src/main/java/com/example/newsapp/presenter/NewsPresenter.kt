package com.example.newsapp.presenter

interface NewsPresenter {
    fun fetchArticles(country: String, apiKey: String)
}