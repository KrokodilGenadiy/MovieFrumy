package com.zaus_app.moviefrumy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zaus_app.moviefrumy.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val film = intent.extras?.get("film") as Film

        //Устанавливаем заголовок
        binding.detailsToolbar.title = film.title
//Устанавливаем картинку
        binding.detailsPoster.setImageResource(film.poster)
//Устанавливаем описание
        binding.detailsDescription.text = film.description
    }
}