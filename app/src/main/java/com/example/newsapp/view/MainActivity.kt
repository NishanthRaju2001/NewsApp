package com.example.newsapp.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.model.ApiService
import com.example.newsapp.model.Article
import com.example.newsapp.model.RetrofitBuilder
import com.example.newsapp.presenter.NewsPresenter
import com.example.newsapp.presenter.NewsPresenterImpl
import com.example.newsapp.presenter.NewsView
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), NewsView , NewsAdapter.OnItemClickListener{
    private lateinit var binding: ActivityMainBinding
    private lateinit var apiService: ApiService
    private lateinit var presenter: NewsPresenter
    private lateinit var adapter: NewsAdapter
    private lateinit var retrofit: Retrofit
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.newsRV.layoutManager = LinearLayoutManager(this)
        adapter = NewsAdapter(emptyList(),this)
        binding.newsRV.adapter = adapter

         retrofit = RetrofitBuilder.getRetrofit()

        apiService = retrofit.create(ApiService::class.java)

        presenter = NewsPresenterImpl(this, apiService)

        presenter.fetchArticles("us", "0f5b9f492b2841b88bf23980a539d558")
    }

    override fun displayArticles(articles: List<Article>) {
        adapter.setData(articles)
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }
    override fun onItemClick(article: Article) {
        val intent = Intent(this, DetailedNewActivity::class.java).apply {
            putExtra("article", article)
        }
        startActivity(intent)
    }
}