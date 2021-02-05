package com.nourtayeb.remy.presentation.ui.moviedetails

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.coroutineScope
import androidx.test.core.app.ApplicationProvider
import com.nourtayeb.remy.R
import com.nourtayeb.remy.databinding.ActivityMainBinding
import com.nourtayeb.remy.databinding.FragmentMovieDetailsBinding
import com.nourtayeb.remy.presentation.common.adapters.MovieListAdapter
import com.nourtayeb.remy.presentation.common.base.FragmentBaseTest
import com.nourtayeb.remy.presentation.common.dummyMovie
import com.nourtayeb.remy.presentation.common.movieList
import com.nourtayeb.remy.presentation.common.movies
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.w3c.dom.Text

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])

class MovieDetailsFragmentTest : FragmentBaseTest() {
    lateinit var fragment: MovieDetailsFragment


    @RelaxedMockK
    lateinit var viewModel: MovieDetailsViewModel

    lateinit var context: Context


    @Before
    fun init() {
        fragment = spyk(MovieDetailsFragment())
        every { fragment.viewModel } returns viewModel
        every { fragment.hideLoading() } returns Unit
        every { fragment.showLoading() } returns Unit
        every { fragment.showToast(any()) } returns Unit
        every { fragment.showMovieData(any()) } returns Unit
        context = ApplicationProvider.getApplicationContext<Context>()
        every { fragment.requireContext() } returns context
    }

    @Test
    fun `MovieLoadedUiState calls hideLoading and showMovieData`() {
        val movieLoadedState = MovieDetailsUiState.MovieLoaded(
            dummyMovie.id,
            dummyMovie.title,
            dummyMovie.rating,
            dummyMovie.poster,
            dummyMovie.releaseDate,
            dummyMovie.details
        )
        every { viewModel.state } returns MutableLiveData(
            movieLoadedState
        )
        val flow = flow {
            emit(MovieDetailsUiAction.LoadMovieDetails(dummyMovie.id))
        }
        viewModel.reduce(flow)
        viewModel.state.observe(lifecycleOwner, fragment.getStateObserver())
        verify { fragment.hideLoading() }
        verify { fragment.showMovieData(movieLoadedState) }
    }

    @Test
    fun `MovieLoadedUiState never calls showToast error couldnt load movie`() {
        val movieLoadedState = MovieDetailsUiState.MovieLoaded(
            dummyMovie.id,
            dummyMovie.title,
            dummyMovie.rating,
            dummyMovie.poster,
            dummyMovie.releaseDate,
            dummyMovie.details
        )
        every { viewModel.state } returns MutableLiveData(
            movieLoadedState
        )
        val flow = flow {
            emit(MovieDetailsUiAction.LoadMovieDetails(dummyMovie.id))
        }
        viewModel.reduce(flow)
        viewModel.state.observe(lifecycleOwner, fragment.getStateObserver())
        verify(exactly = 0) { fragment.showToast(context.getString(R.string.couldnt_load_movie)) }
    }

    @Test
    fun `FailedUiState calls hideLoading and showToast error couldnt load movie`() {
        every { viewModel.state } returns MutableLiveData(
            MovieDetailsUiState.Failed()
        )
        val flow = flow {
            emit(MovieDetailsUiAction.LoadMovieDetails(dummyMovie.id))
        }
        viewModel.reduce(flow)
        viewModel.state.observe(lifecycleOwner, fragment.getStateObserver())
        verify { fragment.hideLoading() }
        verify { fragment.showToast(context.getString(R.string.couldnt_load_movie)) }
    }


    @Test
    fun `LoadingUiState calls showLoading`() {
        every { viewModel.state } returns MutableLiveData(
            MovieDetailsUiState.Loading
        )
        val flow = flow {
            emit(MovieDetailsUiAction.LoadMovieDetails(dummyMovie.id))
        }
        viewModel.reduce(flow)
        viewModel.state.observe(lifecycleOwner, fragment.getStateObserver())
        verify { fragment.showLoading() }
    }

    @Test
    fun `LoadingUiState never calls hideLoading`() {
        every { viewModel.state } returns MutableLiveData(
            MovieDetailsUiState.Loading
        )
        val flow = flow {
            emit(MovieDetailsUiAction.LoadMovieDetails(dummyMovie.id))
        }
        viewModel.reduce(flow)
        viewModel.state.observe(lifecycleOwner, fragment.getStateObserver())
        verify(exactly = 0) { fragment.hideLoading() }
    }

}