package com.zaus_app.moviefrumy.view.rv_adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zaus_app.moviefrumy.view.rv_viewholders.FilmViewHolder
import com.zaus_app.moviefrumy.R
import com.zaus_app.moviefrumy.databinding.FilmItemBinding
import com.zaus_app.moviefrumy.domain.Film

class FilmAdapter(private val clickListener: OnItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    //Здесь у нас хранится список элементов для RV
    private var items = mutableListOf<Film>()

    //Этот метод нужно переопределить на возврат количества елементов в списке RV
    override fun getItemCount() = items.size

    //В этом методе мы привязываем наш view holder и передаем туда "надутую" верстку нашего фильма
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = FilmItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmViewHolder(binding)
    }

    //В этом методе будет привзяка полей из объекта Film, к view из film_item.xml
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //Проверяем какой у нас ViewHolder
        when (holder) {
            is FilmViewHolder -> {
                //Вызываем метод bind(), который мы создали и передаем туда объект
                //из нашей базы данных с указанием позиции
                holder.bind(items[position])
                //Обрабатываем нажатие на весь элемент целиком(можно сделать на отдельный элемент
                //напрмер, картинку) и вызываем метод нашего листенера, который мы получаем из
                //конструктора адаптера
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

    //Интерфейс для обработки кликов
    interface OnItemClickListener {
        fun click(film: Film)
    }
}