package com.ldept.restapidemo.ui.repoListFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ldept.restapidemo.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RepoListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val githubRepos = repository.getGithubRepos().cachedIn(viewModelScope)
}