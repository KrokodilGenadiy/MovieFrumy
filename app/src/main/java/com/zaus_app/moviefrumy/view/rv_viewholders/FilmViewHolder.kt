package com.zaus_app.moviefrumy.view.rv_viewholders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zaus_app.moviefrumy.data.ApiConstants
import com.zaus_app.moviefrumy.databinding.FilmItemBinding
import com.zaus_app.moviefrumy.data.entity.Film

class FilmViewHolder(binding: FilmItemBinding) : RecyclerView.ViewHolder(binding.root) {

    private val title = binding.title
    private val poster = binding.poster
    private val description = binding.description
    private val ratingDonut = binding.ratingDonut


    fun bind(film: Film) {
        title.text = film.title
        Glide.with(itemView)
            .load(ApiConstants.IMAGES_URL + "w342" + film.poster)
            .centerCrop()
            .into(poster)
        description.text = film.description
        ratingDonut.setProgress((film.rating * 10).toInt())
        ratingDonut.animateProgress()
    }
}