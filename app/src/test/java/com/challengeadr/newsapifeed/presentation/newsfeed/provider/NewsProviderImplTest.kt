package com.challengeadr.newsapifeed.presentation.newsfeed.provider

import com.challengeadr.newsapifeed.db.repository.NewsRepositoryImpl
import com.challengeadr.newsapifeed.network.NetworkServiceImpl
import com.challengeadr.newsapifeed.network.model.Article
import com.challengeadr.newsapifeed.network.model.ItemsResponse
import com.challengeadr.newsapifeed.network.model.Source
import com.challengeadr.newsapifeed.provider.NewsProviderImpl
import com.challengeadr.newsapifeed.util.scedulers.TestSchedulerProvider
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsProviderImplTest {

    @Mock
    internal var newsRepository: NewsRepositoryImpl = Mockito.mock(NewsRepositoryImpl::class.java)

    @Mock
    internal var networkService: NetworkServiceImpl = Mockito.mock(NetworkServiceImpl::class.java)

    private lateinit var testScheduler: TestScheduler

    var provider: NewsProviderImpl? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        testScheduler = TestScheduler()
        val testSchedulerProvider = TestSchedulerProvider(testScheduler)
        provider = NewsProviderImpl(
            newsRepository,
            networkService,
            testSchedulerProvider
        )
    }

    @Test
    fun `data received from API is transferred to DB unchanged`() {
        provider!!.saveDataLocally(
            ItemsResponse(
                "ok", 38,
                listOf(
                    Article(
                        Source(
                            "Bbc",
                            "BBC"
                        ),
                        "Author",
                        "Title",
                        "Description",
                        "url",
                        null,
                        null,
                        "content"
                    )
                )
            ),
            0,
            7
        )
        testScheduler.triggerActions()
        Mockito.verify(newsRepository).addTimestamp(0, 1)
        Mockito.verify(newsRepository).addOrUpdateNewsModel(
            "Author",
            "Title",
            "Description",
            "url",
            null,
            0L,
            "content",
            "Bbc",
            "BBC",
            0
        )
    }

    @Test
    fun `getCountry code returns correct code and US code by default`() {
        Assert.assertEquals(provider!!.getCountryCode("en-gb"), "gb")
        Assert.assertEquals(provider!!.getCountryCode("en-GB"), "gb")
        Assert.assertEquals(provider!!.getCountryCode(""), "us")
        Assert.assertEquals(provider!!.getCountryCode("en-en"), "us")
    }
}