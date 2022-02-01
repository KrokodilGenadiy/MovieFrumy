package com.zaus_app.moviefrumy.view.rv_viewholders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zaus_app.moviefrumy.data.entity.Genre
import com.zaus_app.moviefrumy.databinding.GenreItemBinding


class GenreViewHolder(binding: GenreItemBinding) : RecyclerView.ViewHolder(binding.root) {

    private val title = binding.genreName
    private val poster = binding.genreImage

    fun bind(genre: Genre) {
        title.text = genre.name
        Glide.with(itemView)
            .load(genre.image)
            .centerCrop()
            .into(poster)
    }
}