package com.zaus_app.moviefrumy.view.rv_adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zaus_app.moviefrumy.view.rv_viewholders.FilmViewHolder
import com.zaus_app.moviefrumy.databinding.FilmItemBinding
import com.zaus_app.moviefrumy.data.entity.Film


class FavoritesAdapter(private val clickListener: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var favorites = mutableListOf<Film>()

    override fun getItemCount() = favorites.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = FilmItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return FilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FilmViewHolder -> {
                holder.bind(favorites[position])
                holder.itemView.setOnClickListener {
                    clickListener.click(favorites[position])
                }
            }
        }
    }

    fun getFavorites(): MutableList<Film> {
        return favorites
    }

    fun setFavorites(list: MutableList<Film>) {
        this.favorites = list
    }

    interface OnItemClickListener {
        fun click(film: Film)
    }
}