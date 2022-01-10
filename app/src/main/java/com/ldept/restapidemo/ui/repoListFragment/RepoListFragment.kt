package com.ldept.restapidemo.ui.repoListFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.ldept.restapidemo.R
import com.ldept.restapidemo.data.models.GithubRepo
import com.ldept.restapidemo.databinding.FragmentRepoListBinding
import com.ldept.restapidemo.databinding.RepoLoadStateFooterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoListFragment : Fragment(), RepoAdapter.OnItemClickListener {

    private val viewModel by viewModels<RepoListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentRepoListBinding.bind(view)
        val adapter = RepoAdapter(listener = this)
        binding.apply {
            repoRecyclerview.setHasFixedSize(true)
            repoRecyclerview.adapter = adapter.withLoadStateHeaderAndFooter(
                header = RepoLoadStateAdapter { adapter.retry() },
                footer = RepoLoadStateAdapter { adapter.retry() }
            )

            loadStateLayout.buttonRetry.setOnClickListener {
                adapter.retry()
            }
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                loadStateLayout.apply {
                    progressbar.isVisible = loadState.source.refresh is LoadState.Loading
                    textviewError.isVisible = loadState.source.refresh is LoadState.Error
                    buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                }

                repoRecyclerview.isVisible = loadState.source.refresh is LoadState.NotLoading
            }
        }

        viewModel.githubRepos.observe(viewLifecycleOwner) { pagingData ->
            adapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
        }
    }

    override fun onItemClick(githubRepo: GithubRepo) {
        val action = RepoListFragmentDirections.actionRepoListFragmentToRepoDetailsFragment(
            githubRepo
        )
        findNavController().navigate(action)
    }

}