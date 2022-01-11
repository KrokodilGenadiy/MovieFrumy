package com.zaus_app.moviefrumy.view.rv_viewholders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zaus_app.moviefrumy.data.entity.Genre
import com.zaus_app.moviefrumy.databinding.GenreItemBinding


class GenreViewHolder(binding: GenreItemBinding) : RecyclerView.ViewHolder(binding.root) {

    private val title = binding.genreName
    private val poster = binding.genreImage

    //В этом методе кладем данные из film в наши view
    fun bind(genre: Genre) {
        //Устанавливаем заголовок
        title.text = genre.name
        //Указываем контейнер, в которм будет "жить" наша картинка
        Glide.with(itemView)
            //Загружаем сам ресурс
            .load(genre.image)
            //Центруем изображение
            .centerCrop()
            //Указываем ImageView, куда будем загружать изображение
            .into(poster)
    }
}