package com.an.paginglib3_sample.data

import androidx.paging.PagingSource
import com.an.paginglib3_sample.BaseUnitTest
import com.an.paginglib3_sample.model.Article
import com.an.paginglib3_sample.model.NewsApiResponse
import com.an.paginglib3_sample.model.Source
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class NewsDataSourceTest: BaseUnitTest() {
    private val repository: NewsRepository = mock()
    private val query = "movies"
    private val pageSize = 20
    private val firstPage = 1L

    private val dataSource = NewsDataSource(repository, query)

    private val expectedArticles = listOf(
        Article(
            source = Source("", ""), author = "", title = "", description = "",
            url = "", urlToImage = "", content = "", publishedAt = ""
        ),
        Article(
            source = Source("", ""), author = "", title = "", description = "",
            url = "", urlToImage = "", content = "", publishedAt = ""
        ),
        Article(
            source = Source("", ""), author = "", title = "", description = "",
            url = "", urlToImage = "", content = "", publishedAt = ""
        )
    )

    @Test
    fun `when api returns first page of news then paging source returns success load result`() = runTest {
        // given that repository returns the first page of the news
        setupMockResponse(firstPage)

        val params = PagingSource
            .LoadParams
            .Refresh(
                key = firstPage,
                loadSize = pageSize,
                placeholdersEnabled = false
            )

        val expected = PagingSource
            .LoadResult
            .Page(
                data = expectedArticles,
                prevKey = null,
                nextKey = firstPage + 1
            )

        // when paging source returns success
        val actual = dataSource.load(params = params)

        // then the first page of the news list should be available
        assertEquals(expected, actual)
    }

    @Test
    fun `when api returns error response then paging source returns error load result`() = runTest {
        val params = PagingSource
            .LoadParams
            .Refresh(
                key = firstPage,
                loadSize = pageSize,
                placeholdersEnabled = false
            )

        val expected = PagingSource
            .LoadResult
            .Error<Long, Article>(
                throwable = Exception("Response body is null")
            )::class.java

        // when
        val actual = dataSource.load(params = params)::class.java

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `when second page of news is available then paging source returns success append load result`() = runTest {
        // given
        val secondPage = firstPage + 1
        setupMockResponse(secondPage)

        val params = PagingSource
            .LoadParams
            .Append(
                key = secondPage,
                loadSize = pageSize,
                placeholdersEnabled = false
            )

        val expected = PagingSource
            .LoadResult
            .Page(
                data = expectedArticles,
                prevKey = secondPage-1,
                nextKey = secondPage+1
            )

        // when
        val actual = dataSource.load(params = params)

        // then
        assertEquals(expected, actual)
    }

    private suspend fun setupMockResponse(page: Long) {
        `when`(
            repository.fetchNews(query, page)
        ).thenReturn(
            NewsApiResponse(
                status = "ok",
                totalResults = 3,
                articles = expectedArticles
            )
        )
    }
}