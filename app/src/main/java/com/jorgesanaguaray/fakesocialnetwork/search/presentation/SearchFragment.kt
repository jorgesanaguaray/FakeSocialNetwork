package com.jorgesanaguaray.fakesocialnetwork.search.presentation

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

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        searchViewModel = ViewModelProvider(this).get()
        searchAdapter = SearchAdapter(
            context = requireContext(),
            followClick = {}
        )

    }

    @SuppressLint("RepeatOnLifecycleWrongUsage")
    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED) {

                searchViewModel.searchState.collect {

                    setUpViews(it)

                }

            }

        }

        binding.mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                searchViewModel.getSearchedUsers(query!!)
                binding.mSearchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchViewModel.getSearchedUsers(newText!!)
                return true
            }

        })

        binding.mSwipeRefresh.setOnRefreshListener {
            searchViewModel.getUsers()
            binding.mSearchView.setQuery("", false)
            binding.mSwipeRefresh.isRefreshing = false
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpViews(searchState: SearchState) {

        searchAdapter.setUsers(searchState.users)
        binding.mRecyclerView.adapter = searchAdapter

        if (searchState.isSearchView) binding.mSearchView.visibility = View.VISIBLE
        else binding.mSearchView.visibility = View.GONE

        if (searchState.isRecyclerView) binding.mRecyclerView.visibility = View.VISIBLE
        else binding.mRecyclerView.visibility = View.GONE

        if (searchState.isLoading) binding.mProgressBar.visibility = View.VISIBLE
        else binding.mProgressBar.visibility = View.GONE

    }

}