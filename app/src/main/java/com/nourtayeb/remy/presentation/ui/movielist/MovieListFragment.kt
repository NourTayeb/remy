package com.nourtayeb.remy.presentation.ui.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.coroutineScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.nourtayeb.remy.R
import com.nourtayeb.remy.databinding.FragmentMovieListBinding
import com.nourtayeb.remy.presentation.common.adapters.MovieListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow

@AndroidEntryPoint
class MovieListFragment : Fragment() {
    val viewModel: MovieListViewModel by viewModels()

    val uiActionsChannel = Channel<MovieListUiAction>()
    lateinit var navController: NavController
    lateinit var adapter: MovieListAdapter
    var cursor: String? = null

    lateinit var binding: FragmentMovieListBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieListBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroy() {
        uiActionsChannel.close()
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = MovieListAdapter()
        val flow: Flow<MovieListUiAction> = uiActionsChannel.consumeAsFlow()
        viewModel.reduce(flow)
        viewModel.state.observe(this, getStateObserver())

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        setUpRecyclerViewAdapters()
        loadMovies()
    }

    fun setUpRecyclerViewAdapters() {
        with(binding.recyclerView) {
            adapter = this@MovieListFragment.adapter
            layoutManager = androidx.recyclerview.widget.StaggeredGridLayoutManager(
                1,
                androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
            )
        }

        with(adapter) {
            onEndOfListReached = {
                loadMovies()
            }
            adapter.onItemClicked = { movie ->
                navController.navigate(
                    MovieListFragmentDirections.openMovieDetails(movieId = movie.id)
                )
            }
        }
    }

    fun getStateObserver(): Observer<in MovieListUiState> {
        return Observer {
            when (it) {
                is MovieListUiState.MoviesLoaded -> {
                    adapter.addData(it.data.movies)
                    cursor = it.data.movies.get(it.data.movies.size - 1).cursor
                    if (!it.data.hasNext) {
                        adapter.onEndOfListReached = null
                    }
                    hideLoading()
                }
                is MovieListUiState.Failed -> {
                    showNoData()
                    hideLoading()
                }
                is MovieListUiState.Loading -> {
                    showLoading()
                    hideNoData()
                }
                is MovieListUiState.Exception -> {
                    showToast(it.exception.toString())
                    hideLoading()
                }
            }
        }
    }

    fun showToast(string: String) {
        Toast.makeText(requireContext(), string, Toast.LENGTH_LONG).show()
    }

    fun hideNoData() {
        binding.noData.visibility = View.GONE
    }

    fun showLoading() {
        binding.loading.visibility = View.VISIBLE
    }

    fun showNoData() {
        binding.noData.visibility = View.VISIBLE
    }

    fun hideLoading() {
        binding.loading.visibility = View.GONE
    }


    fun loadMovies() {
        lifecycle.coroutineScope.launchWhenResumed {
            uiActionsChannel.send(MovieListUiAction.LoadMovies(cursor))
        }
    }
}