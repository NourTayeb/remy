package com.nourtayeb.remy.presentation.common.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule

open class FragmentBaseTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @MockK
    lateinit var lifecycleOwner: LifecycleOwner

    @Before
    fun initMockkAndLifecycles() {
        MockKAnnotations.init(this)
        val lifecycle = LifecycleRegistry(mockk())
        lifecycle.setCurrentState(Lifecycle.State.RESUMED)
        every { lifecycleOwner.lifecycle } returns (lifecycle)
    }
}