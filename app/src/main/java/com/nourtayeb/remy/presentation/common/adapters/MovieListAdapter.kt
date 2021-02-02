package com.nourtayeb.remy.presentation.common.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nourtayeb.movies_mvi.common.utility.loadImageFromUrl
import com.nourtayeb.remy.PopularQuery
import com.nourtayeb.remy.databinding.ListItemMovieBinding
import com.nourtayeb.remy.domain.entity.Movie

class MovieListAdapter() :
    RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {
    val list: MutableList<Movie> = mutableListOf()


    class ViewHolder(val binding: ListItemMovieBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addData(data: List<Movie>?) {
        data?.let {
            list.addAll(it)
            notifyDataSetChanged()
        }
    }


    var onEndOfListReached: (() -> Unit)? = null
    var onItemClicked: ((Movie) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = list.get(position)
        holder.binding.image?.loadImageFromUrl(movie!!.poster)
        holder.binding.title.text = movie?.title
        holder.binding.rating.text = "(${movie?.rating}/10)"

        if (position == list.size - 1) {
            onEndOfListReached?.invoke()
        }

        holder.binding.root.setOnClickListener {
            onItemClicked?.invoke(movie)
        }
    }
}