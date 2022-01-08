package com.ldept.restapidemo.ui.repoListFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ldept.restapidemo.R
import com.ldept.restapidemo.databinding.FragmentRepoListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoListFragment : Fragment() {

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
        val adapter = RepoAdapter()
        binding.apply {
            repoRecyclerview.setHasFixedSize(true)
            repoRecyclerview.adapter = adapter
        }

        viewModel.githubRepos.observe(viewLifecycleOwner) { pagingData ->
            adapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
        }
    }

}