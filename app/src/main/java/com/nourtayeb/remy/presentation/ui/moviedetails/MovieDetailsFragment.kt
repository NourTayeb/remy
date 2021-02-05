package com.nourtayeb.remy.presentation.ui.moviedetails

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
import androidx.navigation.fragment.navArgs
import com.nourtayeb.movies_mvi.common.utility.loadImageFromUrl
import com.nourtayeb.remy.R
import com.nourtayeb.remy.databinding.FragmentMovieDetailsBinding
import com.nourtayeb.remy.databinding.FragmentMovieListBinding
import com.nourtayeb.remy.domain.entity.Movie
import com.nourtayeb.remy.presentation.common.adapters.MovieListAdapter
import com.nourtayeb.remy.presentation.ui.movielist.MovieListUiAction
import com.nourtayeb.remy.presentation.ui.movielist.MovieListUiState
import com.nourtayeb.remy.presentation.ui.movielist.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow

@AndroidEntryPoint
class MovieDetailsFragment:Fragment() {
    val viewModel: MovieDetailsViewModel by viewModels()

    val args: MovieDetailsFragmentArgs by navArgs()
    val uiActionsChannel = Channel<MovieDetailsUiAction>()
    lateinit var navController: NavController
    lateinit var binding: FragmentMovieDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroy() {
        uiActionsChannel.close()
        super.onDestroy()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val flow: Flow<MovieDetailsUiAction> = uiActionsChannel.consumeAsFlow()
        viewModel.reduce(flow)
        viewModel.state.observe(this, getStateObserver())
        loadMovieDetails()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        binding.back.setOnClickListener {
            navController.popBackStack()
        }
    }
    fun getStateObserver(): Observer<in MovieDetailsUiState> {
        return Observer {
            when (it) {
                is MovieDetailsUiState.MovieLoaded -> {
                    showMovieData(it)
                    hideLoading()
                }
                is MovieDetailsUiState.Failed -> {
                    showToast(getString(R.string.couldnt_load_movie))
                    hideLoading()
                }
                is MovieDetailsUiState.Loading -> {
                    showLoading()
                }
                is MovieDetailsUiState.Exception -> {
                    showToast(it.exception.toString())
                    hideLoading()
                }
            }
        }
    }

    fun showMovieData(it: MovieDetailsUiState.MovieLoaded) {
        binding.movieName.text = it.title
        binding.details.text = it.details
        binding.releaseDate.text = it.releaseDate
        binding.rating.text = "(${it.rating}/10)"
        binding.image.loadImageFromUrl(it.poster)
    }


    fun showToast(string: String) {
        Toast.makeText(requireContext(), string, Toast.LENGTH_LONG).show()
    }


    fun showLoading() {
        binding.loading.visibility = View.VISIBLE
    }


    fun hideLoading() {
        binding.loading.visibility = View.GONE
    }


    fun loadMovieDetails() {
        lifecycle.coroutineScope.launchWhenResumed {
            uiActionsChannel.send(MovieDetailsUiAction.LoadMovieDetails(args.movieId))
        }
    }
}