package com.challengeadr.newsapifeed.presentation.newsfeed.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.challengeadr.newsapifeed.R
import com.challengeadr.newsapifeed.databinding.ActivityNewsBinding
import com.challengeadr.newsapifeed.di.activity.ActivityComponent
import com.challengeadr.newsapifeed.di.news.NewsModule
import com.challengeadr.newsapifeed.network.Configuration
import com.challengeadr.newsapifeed.presentation.base.ui.BaseActivity
import com.challengeadr.newsapifeed.presentation.newsfeed.model.NewsItem
import com.challengeadr.newsapifeed.presentation.newsfeed.presenter.NewsPresenter
import javax.inject.Inject

class NewsActivity : BaseActivity(), NewsView {

    private lateinit var binding: ActivityNewsBinding
    @Inject
    internal lateinit var newsAdapter: NewsAdapter
    @Inject
    internal lateinit var newsPresenter: NewsPresenter

    lateinit var gridLayoutManager: GridLayoutManager

    private var firstVisiblePosition: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news)
        newsPresenter.setView(this@NewsActivity)
        initRecyclerView()
        newsAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                // scrolls to the element that was visible before screen had got rotated
                if (firstVisiblePosition != null && newsAdapter.itemCount > 0) {
                    gridLayoutManager.scrollToPosition(firstVisiblePosition!!)
                    firstVisiblePosition = null
                }
            }
        })
        binding.newsSwipeRefreshLayout.setOnRefreshListener {
            newsPresenter.dispose()
            newsPresenter.removeNews()
            newsPresenter.provideNews()
        }
        newsPresenter.provideNews(savedInstanceState?.getInt(PAGES_KEY))
    }

    private fun initRecyclerView(){
        with(binding.newsRecyclerView) {
            adapter = newsAdapter
            gridLayoutManager = GridLayoutManager(
                this@NewsActivity,
                this@NewsActivity.resources.getInteger(R.integer.grid_cells_amount)
            )
            gridLayoutManager.spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position % Configuration.DEFAULT_ITEMS_PER_PAGE_NUMBER == 0) {
                        this@NewsActivity.resources.getInteger(R.integer.grid_cells_amount)
                    } else {
                        1
                    }
                }
            }
            layoutManager = gridLayoutManager
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        newsPresenter.dispose()
    }

    override fun doInjections(activityComponent: ActivityComponent?) {
        activityComponent?.plus(NewsModule())?.injectTo(this)
    }

    override fun updateNewsWithData(pagedList: PagedList<NewsItem>) {
        newsAdapter.setNewsItems(pagedList)
    }

    override fun setRefreshingTo(value: Boolean) {
        binding.newsSwipeRefreshLayout.isRefreshing = value
    }

    override fun onSaveInstanceState(state: Bundle) {
        super.onSaveInstanceState(state)
        firstVisiblePosition = gridLayoutManager.findFirstVisibleItemPosition()
        state.putInt(FIRST_VISIBLE_POSITION_KEY, firstVisiblePosition!!)
        state.putInt(PAGES_KEY, newsAdapter.itemCount)
    }

    override fun onRestoreInstanceState(state: Bundle?) {
        super.onRestoreInstanceState(state)
        if (state != null) {
            firstVisiblePosition = state.getInt(FIRST_VISIBLE_POSITION_KEY)
        }
    }

    companion object {
        private const val FIRST_VISIBLE_POSITION_KEY = "recycler_list_state"
        private const val PAGES_KEY = "pages_key"
    }
}


