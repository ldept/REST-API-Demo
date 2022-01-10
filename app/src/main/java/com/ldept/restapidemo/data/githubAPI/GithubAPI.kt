package com.ldept.restapidemo.data.githubAPI

import com.ldept.restapidemo.data.models.GithubFile
import com.ldept.restapidemo.data.models.GithubRepo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubAPI {

    companion object {
        const val BASE_URL =  "https://api.github.com/"
    }
    @Headers("Accept: application/vnd.github.v3+json")
    @GET("users/allegro/repos")
    suspend fun getAllegroRepos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ) : List<GithubRepo>

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/repos/allegro/{name}/contents")
    suspend fun getAllegroRepoFiles(
        @Path("name") name: String
    ) : Response<List<GithubFile>>
}