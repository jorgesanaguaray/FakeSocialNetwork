package com.jorgesanaguaray.fakesocialnetwork.home.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jorgesanaguaray.fakesocialnetwork.Constants.Companion.KEY_POST_ID
import com.jorgesanaguaray.fakesocialnetwork.R
import com.jorgesanaguaray.fakesocialnetwork.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        // Show Bottom Navigation View
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.mBottomNavigationView)
        bottomNavigationView?.visibility = View.VISIBLE

        // Inflate fragment layout
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        homeViewModel = ViewModelProvider(this).get()
        homeAdapter = HomeAdapter(
            homeViewModel = homeViewModel,
            context = requireContext(),
            editClick = { goPostEdit(it) }
        )

    }

    @SuppressLint("RepeatOnLifecycleWrongUsage")
    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED) {

                homeViewModel.homeState.collect {

                    setUpViews(it)

                }

            }

        }

        binding.mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                homeViewModel.getSearchedPosts(query!!)
                binding.mSearchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                homeViewModel.getSearchedPosts(newText!!)
                return true
            }

        })

        binding.mSwipeRefresh.setOnRefreshListener {
            homeViewModel.getPosts()
            binding.mSearchView.setQuery("", false)
            binding.mSwipeRefresh.isRefreshing = false
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun goPostEdit(postId: Int) {
        val bundle = bundleOf(KEY_POST_ID to postId)
        findNavController().navigate(R.id.action_mHomeNavigation_to_mPostEditNavigation, bundle)
    }

    private fun setUpViews(homeState: HomeState) {

        homeAdapter.setPosts(homeState.posts)
        binding.mRecyclerView.adapter = homeAdapter

        if (homeState.isSearchView) binding.mSearchView.visibility = View.VISIBLE
        else binding.mSearchView.visibility = View.GONE

        if (homeState.isRecyclerView) binding.mRecyclerView.visibility = View.VISIBLE
        else binding.mRecyclerView.visibility = View.GONE

        if (homeState.isLoading) binding.mProgressBar.visibility = View.VISIBLE
        else binding.mProgressBar.visibility = View.GONE

    }

}