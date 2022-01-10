package com.ldept.restapidemo.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubRepo(
    val id: Int,
    val name: String,
    val description: String?,
    val visibility: String,
    // Github Repository API url
    val url: String,
    val stargazers_count: Int,
    val watchers_count: Int,
    val language: String?,
    val license: GithubLicense?
) : Parcelable {
    @Parcelize
    data class GithubLicense(
        val name: String,
    ) : Parcelable
}