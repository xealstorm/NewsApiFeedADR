package com.challengeadr.newsapifeed.presentation.newsfeed.ui

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.challengeadr.newsapifeed.R
import com.challengeadr.newsapifeed.databinding.NewsItemNarrowBinding
import com.challengeadr.newsapifeed.databinding.NewsItemWideBinding
import com.challengeadr.newsapifeed.network.Configuration
import com.challengeadr.newsapifeed.presentation.newsfeed.model.NewsItem

class NewsAdapter : PagedListAdapter<NewsItem, NewsAdapter.NewsViewHolder>(DIFF_CALLBACK) {

    private val newsItems = ArrayList<NewsItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = if (viewType == NewsViewTypes.WIDE.ordinal) {
            DataBindingUtil.inflate<NewsItemWideBinding>(
                inflater,
                R.layout.news_item_wide, parent, false
            )
        } else {
            DataBindingUtil.inflate<NewsItemNarrowBinding>(
                inflater,
                R.layout.news_item_narrow, parent, false
            )
        }

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

    override fun getItemViewType(position: Int): Int {
        return (if (position % Configuration.DEFAULT_ITEMS_PER_PAGE_NUMBER == 0) {
            NewsViewTypes.WIDE
        } else {
            NewsViewTypes.NARROW
        }).ordinal
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
        private val binding: ViewDataBinding? = DataBindingUtil.getBinding(itemView)

        fun onBind(item: NewsItem) {
            if (binding != null) {
                if (binding is NewsItemWideBinding) {
                    with(binding) {
                        newsTitle.text = item.title
                        newsSubtitle.text = item.content
                        newsSource.text = itemView.context.getString(R.string.source_prefix, item.sourceName)
                        newsView.load(url = item.urlToImage)
                        newsTime.text = item.getPublishedDateFormatted(itemView.context)
                        root.setOnClickListener {
                            it.context.startActivity(
                                Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
                            )
                        }
                    }
                } else if (binding is NewsItemNarrowBinding) {
                    with(binding) {
                        newsTitle.text = item.title
                        newsSubtitle.text = item.description
                        newsSource.text = itemView.context.getString(R.string.source_prefix, item.sourceName)
                        newsView.load(url = item.urlToImage)
                        newsTime.text = item.getPublishedDateFormatted(itemView.context)
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

    enum class NewsViewTypes {
        WIDE,
        NARROW
    }
}