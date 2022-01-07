package com.zaus_app.moviefrumy.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.zaus_app.moviefrumy.R
import com.zaus_app.moviefrumy.data.ApiConstants
import com.zaus_app.moviefrumy.data.GenreList
import com.zaus_app.moviefrumy.databinding.FragmentDetailsBinding
import com.zaus_app.moviefrumy.data.entity.Film
import com.zaus_app.moviefrumy.viewmodel.DetailsFragmentViewModel


class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(DetailsFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val film = arguments?.get("film") as Film
        setFilmsDetails(film)

        binding.detailsFabFavorites.setOnClickListener {
            if (!film.isInFavorites) {
                Glide.with( binding.detailsFabFavorites)
                    .load(R.drawable.ic_round_favorite)
                    .into(binding.detailsFabFavorites)
               // binding.detailsFabFavorites.setImageResource(R.drawable.ic_round_favorite)
                film.isInFavorites = true
                viewModel.interactor.addToFavorites(film)
            } else {
                Glide.with( binding.detailsFabFavorites)
                    .load(R.drawable.ic_baseline_favorite_border_24)
                    .into(binding.detailsFabFavorites)
             //   binding.detailsFabFavorites.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                film.isInFavorites = false
                viewModel.interactor.removeFromFavorites(film)
            }
        }

        binding.detailsFabShare.setOnClickListener {     //Создаем интент
            val intent = Intent()
            //Указываем action с которым он запускается
            intent.action = Intent.ACTION_SEND
            //Кладем данные о нашем фильме
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Check out this film: ${film.title} \n\n ${film.description}"
            )
            //Указываем MIME тип, чтобы система знала, какое приложения предложить
            intent.type = "text/plain"
            //Запускаем наше активити
            startActivity(Intent.createChooser(intent, "Share To:"))
        }
    }

    private fun setFilmsDetails(film: Film) {
        //Получаем наш фильм из переданного бандла
        //Устанавливаем заголовок
        binding.title.text = film.title
        //Устанавливаем картинку
        Glide.with(this)
            .load(ApiConstants.IMAGES_URL + "w780" + film.poster)
            .centerCrop()
            .into(binding.detailsPoster)
        //Устанавливаем описание
        binding.detailsDescription.text = film.description
        var genreText = ""
        for (genre in film.genres) {
            genreText = genreText+GenreList.genres.get(genre)+" "
        }
        binding.genres.text = genreText
        //Устанавиливаем значок любимого
        binding.detailsFabFavorites.setImageResource(
            if (film.isInFavorites) R.drawable.ic_round_favorite
            else R.drawable.ic_baseline_favorite_border_24
        )
    }
}