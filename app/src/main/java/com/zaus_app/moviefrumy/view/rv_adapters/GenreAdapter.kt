package com.zaus_app.moviefrumy.view.rv_adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zaus_app.moviefrumy.data.GenreList
import com.zaus_app.moviefrumy.data.entity.Film
import com.zaus_app.moviefrumy.data.entity.Genre
import com.zaus_app.moviefrumy.databinding.GenreItemBinding
import com.zaus_app.moviefrumy.view.rv_viewholders.FilmViewHolder
import com.zaus_app.moviefrumy.view.rv_viewholders.GenreViewHolder

class GenreAdapter(private val clickListener: GenreAdapter.OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items = GenreList.genrelist

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = GenreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GenreViewHolder -> {
                holder.bind(items[position])
                holder.itemView.setOnClickListener {
                    clickListener.click(items[position])
                }
            }
        }
    }

    override fun getItemCount() = items.size

    interface OnItemClickListener {
        fun click(genre: Genre)
    }
}