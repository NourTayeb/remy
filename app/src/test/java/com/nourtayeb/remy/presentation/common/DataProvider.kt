package com.nourtayeb.remy.presentation.common

import com.nourtayeb.remy.domain.entity.Movie
import com.nourtayeb.remy.domain.entity.MovieList

val movies = listOf(
    Movie("11",1,"movie1",1.1,"url1"),
    Movie("22",2,"movie2",2.2,"url2"),
    Movie("33",3,"movie3",3.3,"url3"),
    Movie("44",4,"movie4",4.4,"url4")
)
val movieList = MovieList(movies,true)

val emptyMovies = listOf<Movie>()
val emptyMovieList = MovieList(emptyMovies,false)

val dummyMovie = Movie("11",1,"movie1",1.1,"url1","01-01-2021","details1")
