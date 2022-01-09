package com.ldept.restapidemo.ui.repoListFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ldept.restapidemo.databinding.RepoLoadStateFooterBinding

class RepoLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<RepoLoadStateAdapter.RepoLoadStateViewHolder>() {

    inner class RepoLoadStateViewHolder(private val binding: RepoLoadStateFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonRetry.setOnClickListener {
                retry()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                val isLoading = loadState is LoadState.Loading
                progressbar.isVisible = isLoading
                textviewError.isVisible = !isLoading
                buttonRetry.isVisible = !isLoading
            }
        }
    }

    override fun onBindViewHolder(holder: RepoLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): RepoLoadStateViewHolder {
        val binding =
            RepoLoadStateFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RepoLoadStateViewHolder(binding)
    }
}