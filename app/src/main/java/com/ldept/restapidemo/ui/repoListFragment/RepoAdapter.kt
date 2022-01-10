package com.ldept.restapidemo.ui.repoListFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ldept.restapidemo.data.models.GithubRepo
import com.ldept.restapidemo.databinding.RepoItemBinding

class RepoAdapter(
    private val listener: OnItemClickListener
) : PagingDataAdapter<GithubRepo, RepoAdapter.RepoViewHolder>(DiffCallback()) {

    inner class RepoViewHolder(private val binding: RepoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null)
                        listener.onItemClick(item)
                }
            }
        }

        fun bind(githubRepo: GithubRepo) {
            binding.apply {
                nameTextview.text = githubRepo.name
                descriptionTextview.text = githubRepo.description
                if (githubRepo.description == null)
                    descriptionTextview.visibility = View.GONE
                languageTextview.text = githubRepo.language
                if (githubRepo.language == null)
                    languageTextview.visibility = View.GONE
                visibilityTextview.text = githubRepo.visibility
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val binding = RepoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val item = getItem(position)

        if (item != null)
            holder.bind(item)
    }

    class DiffCallback : DiffUtil.ItemCallback<GithubRepo>() {
        override fun areItemsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean =
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean =
            oldItem == newItem

    }

    interface OnItemClickListener {
        fun onItemClick(githubRepo: GithubRepo)
    }
}