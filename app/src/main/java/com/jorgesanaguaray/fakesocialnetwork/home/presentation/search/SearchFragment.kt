package com.jorgesanaguaray.fakesocialnetwork.home.presentation.search

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jorgesanaguaray.fakesocialnetwork.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: SearchAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        viewModel = ViewModelProvider(this).get()
        adapter = SearchAdapter()

    }

    @SuppressLint("RepeatOnLifecycleWrongUsage")
    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    setUpViews(state)
                }
            }
        }

        binding.mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getSearchedUsers(query!!)
                binding.mSearchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.getSearchedUsers(newText!!)
                return true
            }

        })

        binding.mSwipeRefreshContent.setOnRefreshListener {
            viewModel.getUsers()
            binding.mSearchView.setQuery("", false)
            binding.mSwipeRefreshContent.isRefreshing = false
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpViews(state: SearchState) {

        adapter.setUsers(state.users)
        binding.mRecyclerView.adapter = adapter

        if (state.isContent) binding.mSwipeRefreshContent.visibility = View.VISIBLE
        else binding.mSwipeRefreshContent.visibility = View.GONE

        if (state.isLoading) binding.mProgressBar.visibility = View.VISIBLE
        else binding.mProgressBar.visibility = View.GONE

    }

}