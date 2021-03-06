package com.nourtayeb.remy.presentation.ui.movielist

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
import com.nourtayeb.remy.presentation.common.adapters.MovieListAdapter
import com.nourtayeb.remy.presentation.common.base.FragmentBaseTest
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

class MovieListFragmentTest : FragmentBaseTest() {
    lateinit var fragment: MovieListFragment


    @RelaxedMockK
    lateinit var viewModel: MovieListViewModel

    @RelaxedMockK
    lateinit var adapter: MovieListAdapter
    lateinit var context: Context

    @Before
    fun init() {
        fragment = spyk(MovieListFragment())
        every { fragment.viewModel } returns viewModel
        every { fragment.adapter } returns adapter
        every { fragment.hideLoading() } returns Unit
        every { fragment.showLoading() } returns Unit
        every { fragment.showNoData() } returns Unit
        every { fragment.hideNoData() } returns Unit
        context = ApplicationProvider.getApplicationContext<Context>()
    }

    @Test
    fun `MoviesLoadedUiState calls hideLoading and adapter addData`() {
        every { viewModel.state } returns MutableLiveData(
            MovieListUiState.MoviesLoaded(movieList)
        )
        val flow = flow {
            emit(MovieListUiAction.LoadMovies())
        }
        viewModel.reduce(flow)
        viewModel.state.observe(lifecycleOwner, fragment.getStateObserver())
        verify { fragment.hideLoading() }
        verify { adapter.addData(movieList.movies) }
    }
    @Test
    fun `MoviesLoadedUiState never calls showNoData`() {
        every { viewModel.state } returns MutableLiveData(
            MovieListUiState.MoviesLoaded(movieList)
        )
        val flow = flow {
            emit(MovieListUiAction.LoadMovies())
        }
        viewModel.reduce(flow)
        viewModel.state.observe(lifecycleOwner, fragment.getStateObserver())
        verify(exactly = 0) { fragment.showNoData() }
    }

    @Test
    fun `FailedUiState calls hideLoading and showNoData`() {
        every { viewModel.state } returns MutableLiveData(
            MovieListUiState.Failed()
        )
        val flow = flow {
            emit(MovieListUiAction.LoadMovies())
        }
        viewModel.reduce(flow)
        viewModel.state.observe(lifecycleOwner, fragment.getStateObserver())
        verify { fragment.hideLoading() }
        verify { fragment.showNoData() }
    }

    @Test
    fun `FailedUiState never calls hideNoData`() {
        every { viewModel.state } returns MutableLiveData(
            MovieListUiState.Failed()
        )
        val flow = flow {
            emit(MovieListUiAction.LoadMovies())
        }
        viewModel.reduce(flow)
        viewModel.state.observe(lifecycleOwner, fragment.getStateObserver())
        verify(exactly = 0) { fragment.hideNoData() }
    }

    @Test
    fun `LoadingUiState calls showLoading and hideNoData`() {
        every { viewModel.state } returns MutableLiveData(
            MovieListUiState.Loading
        )
        val flow = flow {
            emit(MovieListUiAction.LoadMovies())
        }
        viewModel.reduce(flow)
        viewModel.state.observe(lifecycleOwner, fragment.getStateObserver())
        verify { fragment.showLoading() }
        verify { fragment.hideNoData() }
    }

    @Test
    fun `LoadingUiState never calls hideLoading`() {
        every { viewModel.state } returns MutableLiveData(
            MovieListUiState.Loading
        )
        val flow = flow {
            emit(MovieListUiAction.LoadMovies())
        }
        viewModel.reduce(flow)
        viewModel.state.observe(lifecycleOwner, fragment.getStateObserver())
        verify(exactly = 0) { fragment.hideLoading() }
    }

}