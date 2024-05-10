package com.example.newsapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.newsapp.R
import com.example.newsapp.databinding.NewsListBinding
import com.example.newsapp.model.Article
import com.squareup.picasso.Picasso

class NewsAdapter(private var news:List<Article>,private val itemClickListener: OnItemClickListener):RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(article: Article)
    }
    inner class NewsViewHolder(private val binding: NewsListBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val article = news[position]
                    itemClickListener.onItemClick(article)
                }
            }
        }

        fun bindData(newsList: Article){
            with(binding){
                Picasso
                    .get()
                    .load(newsList.urlToImage)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.baseline_error_24)
                    .into(binding.newsImage)
                titleTV.text=newsList.title
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding=NewsListBinding.inflate(layoutInflater,parent,false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount() = news.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val data = news[position]
        holder.bindData(data)
    }
    fun setData(newArticles: List<Article>) {
        news = newArticles
        notifyDataSetChanged()
    }
}