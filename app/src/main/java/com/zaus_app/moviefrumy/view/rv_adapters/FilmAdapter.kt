package com.zaus_app.moviefrumy.view.rv_adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zaus_app.moviefrumy.view.rv_viewholders.FilmViewHolder
import com.zaus_app.moviefrumy.databinding.FilmItemBinding
import com.zaus_app.moviefrumy.data.entity.Film

class FilmAdapter(private val clickListener: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items = mutableListOf<Film>()

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = FilmItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FilmViewHolder -> {
                holder.bind(items[position])
                holder.itemView.setOnClickListener {
                    clickListener.click(items[position])
                }
            }
        }
    }

    fun getItems(): MutableList<Film> {
        return items
    }

    fun setItems(list: MutableList<Film>) {
        this.items = list
    }

    interface OnItemClickListener {
        fun click(film: Film)
    }
}