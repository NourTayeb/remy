package com.nourtayeb.remy.presentation.common

import com.nourtayeb.remy.DetailsQuery
import com.nourtayeb.remy.PopularQuery
import com.nourtayeb.remy.domain.entity.Movie
import com.nourtayeb.remy.domain.entity.MovieList

val movies = listOf(
    Movie("11", 1, "movie1", 1.1, "url1"),
    Movie("22", 2, "movie2", 2.2, "url2"),
    Movie("33", 3, "movie3", 3.3, "url3"),
    Movie("44", 4, "movie4", 4.4, "url4")
)
val movieList = MovieList(movies, true)

val emptyMovies = listOf<Movie>()
val emptyMovieList = MovieList(emptyMovies, false)

val dummyMovie = Movie("11", 1, "movie1", 1.1, "url1", "01-01-2021", "details1")

val edges = listOf(
    PopularQuery.Edge(cursor = "start",
        node = PopularQuery.Node(id = 1,title = "first",rating = 1.1,poster = "url1")
    ),
    PopularQuery.Edge(cursor = "end",
        node = PopularQuery.Node(id = 2,title = "second",rating = 2.2,poster = "url2")
    )
)
val remoteMovies = PopularQuery.Movies(
    popular = PopularQuery.Popular(
        totalCount = 100,
        pageInfo = PopularQuery.PageInfo(
            endCursor = "end",
            hasNextPage = true,
            hasPreviousPage = false,
            startCursor = "start"
        ),
        edges = edges
    )
)
val moviesMapped = listOf(
    Movie(cursor = "start",id = 1,title = "first",rating = 1.1,poster = "url1"),
    Movie(cursor = "end",id = 2,title = "second",rating = 2.2,poster = "url2")
)
val movieListMapped = MovieList(moviesMapped, true)

val movieDetails = DetailsQuery.Movie(id = 1,title = "movie",poster = "url",rating = 2.2,releaseDate = "20/10/2020T10:32"
    ,details = DetailsQuery.Details(overview = "overview"))
val movieMapped = Movie(cursor = "",id = 1,title = "movie",poster = "url",rating = 2.2,releaseDate = "20/10/2020",details = "overview")