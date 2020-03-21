package com.challengeadr.newsapifeed.presentation.newsfeed.ui

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.challengeadr.newsapifeed.R
import com.challengeadr.newsapifeed.databinding.NewsItemBinding
import com.challengeadr.newsapifeed.presentation.newsfeed.model.NewsItem


class NewsAdapter : PagedListAdapter<NewsItem, NewsAdapter.NewsViewHolder>(DIFF_CALLBACK) {

    private val newsItems = ArrayList<NewsItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<NewsItemBinding>(
                inflater,
                R.layout.news_item, parent, false
            )
        return NewsViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.onBind(getItem(position) as NewsItem)
    }

    fun setNewsItems(newListOfNewsItems: PagedList<NewsItem>) {
        newsItems.addAll(newListOfNewsItems)
        submitList(newListOfNewsItems)
        notifyDataSetChanged()
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<NewsItem> =
            object : DiffUtil.ItemCallback<NewsItem>() {
                override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
                    return oldItem.title === newItem.title
                }

                override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
                    return oldItem.title == newItem.title && oldItem.title == newItem.title
                }
            }
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = DataBindingUtil.getBinding<NewsItemBinding>(itemView)

        fun onBind(item: NewsItem) {
            if (binding != null) {
                with(binding) {
                    newsTitle.text = item.title
                    root.setOnClickListener {
                        it.context.startActivity(
                            Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
                        )
                    }
                }
            }
        }
    }
}