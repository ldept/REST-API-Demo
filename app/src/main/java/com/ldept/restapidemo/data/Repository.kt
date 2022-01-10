package com.ldept.restapidemo.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.ldept.restapidemo.data.githubAPI.GithubAPI
import com.ldept.restapidemo.data.models.GithubFile
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

private const val PAGE_SIZE = 30
private const val MAX_SIZE = 100

@Singleton
class Repository @Inject constructor(
    private val githubAPI: GithubAPI
) {

    suspend fun getRepoFiles(repoName: String): Response<List<GithubFile>> =
        githubAPI.getAllegroRepoFiles(repoName)

    fun getGithubRepos() =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = MAX_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GithubPagingSource(githubAPI)
            }
        ).liveData
}