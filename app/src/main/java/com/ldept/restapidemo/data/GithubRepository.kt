package com.ldept.restapidemo.data

import com.ldept.restapidemo.data.githubAPI.GithubAPI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepository @Inject constructor(
    private val githubAPI: GithubAPI
) {
}