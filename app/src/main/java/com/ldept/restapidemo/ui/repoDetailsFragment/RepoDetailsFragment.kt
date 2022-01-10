package com.ldept.restapidemo.ui.repoDetailsFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.ldept.restapidemo.R
import com.ldept.restapidemo.data.models.GithubRepo
import com.ldept.restapidemo.databinding.FragmentRepoDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RepoDetailsFragment : Fragment() {

    private val viewModel by viewModels<RepoDetailsViewModel>()
    private val args by navArgs<RepoDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repo_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentRepoDetailsBinding.bind(view)

        val filesAdapter = RepoFilesAdapter()

        binding.apply {
            val repo: GithubRepo = args.repo

            textviewName.text = repo.name
            textviewLicenseName.text = repo.license?.name ?: ""

            filesRecyclerview.apply {
                adapter = filesAdapter
                addItemDecoration(
                    DividerItemDecoration(
                        filesRecyclerview.context,
                        DividerItemDecoration.VERTICAL
                    )
                )
                isNestedScrollingEnabled = false
            }

            loadStateLayout.buttonRetry.setOnClickListener {
                viewModel.getRepoFiles()
            }


        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventFlow.collect { event ->
                    when (event) {
                        is RepoDetailsViewModel.RepoDetailsEvent.ConnectionErrorEvent -> {
                            showConnectionError(binding)
                        }
                        is RepoDetailsViewModel.RepoDetailsEvent.LoadingAPIResponse -> {
                            showProgressBar(binding)
                        }
                    }
                }
            }
        }

        viewModel.fileListResponse.observe(viewLifecycleOwner) { apiResponse ->
            if (apiResponse.isSuccessful) {
                showContent(binding)
                val list = apiResponse.body()
                if (list != null) {
                    filesAdapter.submitList(list.sortedWith(compareBy({ it.type }, { it.name })))
                }
            } else {
                showConnectionError(binding)
            }
        }


    }

    private fun showContent(binding: FragmentRepoDetailsBinding) {
        binding.apply {
            contentLayout.isVisible = true
            loadStateLayout.progressbar.isVisible = false
            loadStateLayout.textviewError.isVisible = false
            loadStateLayout.buttonRetry.isVisible = false
        }
    }

    private fun showProgressBar(binding: FragmentRepoDetailsBinding) {
        binding.apply {
            contentLayout.isVisible = false
            loadStateLayout.progressbar.isVisible = true
            loadStateLayout.textviewError.isVisible = false
            loadStateLayout.buttonRetry.isVisible = false
        }
    }

    private fun showConnectionError(binding: FragmentRepoDetailsBinding) {
        binding.contentLayout.isVisible = false
        binding.loadStateLayout.apply {
            progressbar.isVisible = false
            textviewError.isVisible = true
            buttonRetry.isVisible = true
        }
    }
}