package com.example.newsapp.view

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityDetailedNewBinding
import com.example.newsapp.model.Article
import com.squareup.picasso.Picasso

class DetailedNewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailedNewBinding
    private  var article: Article?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailedNewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

         article = intent?.getParcelableExtra<Article>("article")
        if (article == null || article?.url.isNullOrEmpty()) {
            Toast.makeText(this, "Invalid article", Toast.LENGTH_SHORT).show()
            finish()
            return
        }


        article?.let {
            with(binding) {
                titleTV.text = it.title
                it.urlToImage?.let { url ->
                    Picasso.get()
                        .load(url)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.baseline_error_24)
                        .into(newsIV)
                }
                descriptionTV.text = it.description
                contentTV.text = it.content
                publishedTV.text = it.publishedAt
                authorTV.text = it.author
                sourceNameTV.text = it.source?.name
            }
        }
        val shareButton: ImageButton = findViewById(R.id.shareIV)
        shareButton.setOnClickListener {
            shareArticleUrl()
        }
    }
    private fun shareArticleUrl() {
        if (article == null || article?.url.isNullOrEmpty()) {
            Toast.makeText(this, "Cannot share article URL", Toast.LENGTH_SHORT).show()
            return
        }
        val url = article?.url
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, url)
        intent.setPackage("com.whatsapp")

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "WhatsApp is not installed", Toast.LENGTH_SHORT).show()
        }
    }

}