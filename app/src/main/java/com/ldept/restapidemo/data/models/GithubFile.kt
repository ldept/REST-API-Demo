package com.ldept.restapidemo.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubFile(
    val name: String,
    val type: String,
    val sha: String
) : Parcelable
