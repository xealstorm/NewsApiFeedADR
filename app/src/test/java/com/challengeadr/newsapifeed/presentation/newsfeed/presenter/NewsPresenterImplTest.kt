package com.challengeadr.newsapifeed.presentation.newsfeed.presenter

import com.challengeadr.newsapifeed.db.repository.NewsRepositoryImpl
import com.challengeadr.newsapifeed.presentation.newsfeed.model.NewsDataSourceFactory
import com.challengeadr.newsapifeed.presentation.newsfeed.ui.NewsView
import com.challengeadr.newsapifeed.util.scedulers.TestSchedulerProvider
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsPresenterImplTest {

    var presenter: NewsPresenterImpl? = null

    @Mock
    internal var newsDataSourceFactory: NewsDataSourceFactory =
        Mockito.mock(NewsDataSourceFactory::class.java)

    @Mock
    internal var newsRepository: NewsRepositoryImpl = Mockito.mock(NewsRepositoryImpl::class.java)

    @Mock
    internal var view: NewsView? = null

    private lateinit var testScheduler: TestScheduler

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        testScheduler = TestScheduler()
        val testSchedulerProvider = TestSchedulerProvider(testScheduler)
        presenter = NewsPresenterImpl(newsDataSourceFactory, testSchedulerProvider, newsRepository)
        presenter!!.setView(view!!)
    }

    @Test
    fun `DB clean is called when removeNews is triggered`() {
        presenter!!.removeNews()
        Mockito.verify(newsRepository)!!.cleanItems()
    }
}