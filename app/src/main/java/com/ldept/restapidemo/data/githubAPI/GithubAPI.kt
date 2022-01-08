package com.ldept.restapidemo.data.githubAPI

import com.ldept.restapidemo.data.models.GithubRepo
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubAPI {

    companion object {
        const val BASE_URL =  "https://api.github.com/"
    }
    @GET("repos/allegro")
    suspend fun getAllegroRepos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ) : List<GithubRepo>
}