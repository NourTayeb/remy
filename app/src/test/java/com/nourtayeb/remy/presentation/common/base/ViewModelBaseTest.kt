package com.nourtayeb.remy.presentation.common.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule

open class ViewModelBaseTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    val testDispatcher = TestCoroutineDispatcher()

    @MockK
    lateinit var lifecycleOwner: LifecycleOwner


    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }


    @Before
    fun initDipatchersAndLifecycles() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        val lifecycle = LifecycleRegistry(mockk())
        lifecycle.setCurrentState(Lifecycle.State.RESUMED)
        every { lifecycleOwner.lifecycle } returns (lifecycle)
    }
}