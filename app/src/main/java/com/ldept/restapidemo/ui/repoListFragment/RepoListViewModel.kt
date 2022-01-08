package com.ldept.restapidemo.ui.repoListFragment

import androidx.lifecycle.ViewModel
import com.ldept.restapidemo.data.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RepoListViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {

}