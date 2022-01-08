package com.ldept.restapidemo.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ldept.restapidemo.data.githubAPI.GithubAPI
import com.ldept.restapidemo.data.models.GithubRepo
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

private const val STARTING_PAGE_INDEX = 1

class GithubPagingSource(
    private val githubAPI: GithubAPI,
) : PagingSource<Int, GithubRepo>() {
    override fun getRefreshKey(state: PagingState<Int, GithubRepo>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubRepo> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = githubAPI.getAllegroRepos(position, params.loadSize)
            LoadResult.Page(
                data = response,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (response.isEmpty()) null else position + 1,
            )
        } catch (exception: Exception) {
            when(exception){
                // No internet connection for example
                is IOException ->
                    LoadResult.Error(exception)
                // Server errors
                is HttpException ->
                    LoadResult.Error(exception)
                else ->
                    throw exception
            }
        }
    }
}