package com.challengeadr.newsapifeed.presentation.newsfeed.ui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.challengeadr.newsapifeed.R
import com.challengeadr.newsapifeed.databinding.ActivityNewsBinding
import com.challengeadr.newsapifeed.di.activity.ActivityComponent
import com.challengeadr.newsapifeed.presentation.base.ui.BaseActivity
import com.challengeadr.newsapifeed.presentation.newsfeed.presenter.NewsPresenter
import javax.inject.Inject

class NewsActivity : BaseActivity(), NewsView {

    private lateinit var binding: ActivityNewsBinding
    @Inject
    internal lateinit var newsPresenter: NewsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_news
        )
        newsPresenter.setView(this@NewsActivity)
        with(binding.newsRecyclerView) {
            layoutManager = LinearLayoutManager(this@NewsActivity)
        }
    }

    override fun doInjections(activityComponent: ActivityComponent?) {
        activityComponent?.injectTo(this)
    }
}


