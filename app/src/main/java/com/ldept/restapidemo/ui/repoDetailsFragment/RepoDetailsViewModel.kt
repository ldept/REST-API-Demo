package com.ldept.restapidemo.ui.repoDetailsFragment

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingSource
import com.ldept.restapidemo.data.Repository
import com.ldept.restapidemo.data.models.GithubFile
import com.ldept.restapidemo.data.models.GithubRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class RepoDetailsViewModel @Inject constructor(
    private val repository: Repository,
    state: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val REPO_KEY = "repo"
    }

    private val repo: GithubRepo? = state.get<GithubRepo>(REPO_KEY)

    sealed class RepoDetailsEvent {
        object LoadingAPIResponse : RepoDetailsEvent()
        object ConnectionErrorEvent : RepoDetailsEvent()
    }

    private val eventChannel = Channel<RepoDetailsEvent>(Channel.BUFFERED)
    val eventFlow = eventChannel.receiveAsFlow()

    init {
        getRepoFiles()
    }

    private val _fileListResponse = MutableLiveData<Response<List<GithubFile>>>()
    val fileListResponse: LiveData<Response<List<GithubFile>>>
        get() = _fileListResponse

    fun getRepoFiles() = viewModelScope.launch {
        eventChannel.send(RepoDetailsEvent.LoadingAPIResponse)
        kotlin.runCatching {
            repository.getRepoFiles(repo?.name ?: "")
        }.onSuccess {
            _fileListResponse.postValue(it)
        }.onFailure { exception ->
            when (exception) {
                is IOException -> onRetrofitException(exception)
                is HttpException -> onRetrofitException(exception)
                else -> throw exception
            }
        }

    }

    private suspend fun onRetrofitException(exception: Exception) {
        eventChannel.send(RepoDetailsEvent.ConnectionErrorEvent)
        Log.e("RepoDetailsViewModel, getRepoFiles", exception.message.toString())
    }

}