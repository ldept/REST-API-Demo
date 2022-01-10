package com.ldept.restapidemo.ui.repoDetailsFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ldept.restapidemo.R
import com.ldept.restapidemo.data.models.GithubFile
import com.ldept.restapidemo.databinding.FileItemBinding

class RepoFilesAdapter :
    ListAdapter<GithubFile, RepoFilesAdapter.RepoFilesViewHolder>(DiffCallback()) {

    class RepoFilesViewHolder(private val binding: FileItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(githubFile: GithubFile) {
            binding.apply {
                textviewFileName.text = githubFile.name
                if (githubFile.type == "file")
                    textviewFileName.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_baseline_insert_drive_file_24,
                        0,
                        0,
                        0
                    )
                else
                    textviewFileName.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_baseline_folder_24,
                        0,
                        0,
                        0
                    )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoFilesViewHolder {
        val binding = FileItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RepoFilesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoFilesViewHolder, position: Int) {
        val item = getItem(position)

        if (item != null)
            holder.bind(item)
    }

    class DiffCallback : DiffUtil.ItemCallback<GithubFile>() {
        override fun areItemsTheSame(oldItem: GithubFile, newItem: GithubFile): Boolean =
            oldItem.sha == newItem.sha


        override fun areContentsTheSame(oldItem: GithubFile, newItem: GithubFile): Boolean =
            oldItem == newItem

    }

}