package com.an.paginglib3_sample.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.an.paginglib3_sample.model.Article
import javax.inject.Inject

class NewsDataSource @Inject constructor (
    private val repository: NewsRepository,
    private val query: String
): PagingSource<Long, Article>() {
    override fun getRefreshKey(state: PagingState<Long, Article>): Long? {
        // Try to find the page key of the closest page to anchorPosition from
        // either the prevKey or the nextKey; you need to handle nullability
        // here.
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey are null -> anchorPage is the
        //    initial page, so return null.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, Article> {
        return try {
            // Start refresh at page 1 if undefined
            val nextPage = params.key ?: 1L
            val newsResponse = repository.fetchNews(query, nextPage)
                if (newsResponse.articles.isNotEmpty()) {
                    LoadResult.Page(
                        data = newsResponse.articles,
                        prevKey = if (nextPage == 1L) null else nextPage - 1,
                        nextKey = nextPage.plus(1)
                    )
                } else LoadResult.Error(Exception("No results found"))
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}