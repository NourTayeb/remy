package com.nourtayeb.remy.data.mapper

import com.nourtayeb.remy.presentation.common.movieDetails
import com.nourtayeb.remy.presentation.common.movieListMapped
import com.nourtayeb.remy.presentation.common.movieMapped
import com.nourtayeb.remy.presentation.common.remoteMovies
import org.junit.Assert
import org.junit.Test

class MovieMapperTest {
    val mapper = MovieMapper()

    @Test
    fun `Movielist mapping test`(){
        Assert.assertEquals(movieListMapped,mapper.remoteToEntity(remoteMovies))
    }
    @Test
    fun `MovieDetails mapping test`(){
        Assert.assertEquals(movieMapped,mapper.remoteToEntity(movieDetails))
    }
}