package com.example.newsapp.presenter

import com.example.newsapp.model.Article

interface NewsView {
    fun displayArticles(articles: List<Article>)
    fun showError(message: String)

}