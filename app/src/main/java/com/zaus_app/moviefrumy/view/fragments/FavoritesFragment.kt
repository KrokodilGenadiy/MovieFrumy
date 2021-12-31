package com.zaus_app.moviefrumy.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.zaus_app.moviefrumy.databinding.FragmentFavoritesBinding
import com.zaus_app.moviefrumy.data.entity.Film
import com.zaus_app.moviefrumy.utils.AnimationHelper
import com.zaus_app.moviefrumy.view.MainActivity
import com.zaus_app.moviefrumy.view.rv_adapters.FavoritesAdapter
import com.zaus_app.moviefrumy.view.rv_adapters.FilmDiff
import com.zaus_app.moviefrumy.view.rv_adapters.ItemDecorator
import com.zaus_app.moviefrumy.viewmodel.FavoriteFragmentViewModel


class FavoritesFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(FavoriteFragmentViewModel::class.java)
    }
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var filmsAdapter: FavoritesAdapter
    private var favotesDataBase = mutableListOf<Film>()
        //Используем backing field
        set(value) {
            //Если придет такое же значение то мы выходим из метода
            if (field == value) return
            //Если прило другое значение, то кладем его в переменную
            field = value
            //Обновляем RV адаптер
            updateData(field)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        filmsAdapter = FavoritesAdapter(object : FavoritesAdapter.OnItemClickListener {
            override fun click(film: Film) {
                (requireActivity() as MainActivity).launchDetailsFragment(film)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        //Кладем нашу БД в RV
        viewModel.filmsListLiveData.observe(viewLifecycleOwner, {
            favotesDataBase = it as MutableList<Film>
        })
        initRecycler()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AnimationHelper.performFragmentCircularRevealAnimation(binding.favoritesFragment, requireActivity(), 2)

        if (viewModel.interactor.getFavoriteFilms().isEmpty()) {
            binding.listIsEmptyText.visibility = View.VISIBLE
            binding.lottieAnim.visibility = View.VISIBLE
        } else {
            binding.listIsEmptyText.visibility = View.GONE
            binding.lottieAnim.visibility = View.GONE
        }
    }

    fun updateData(newList: MutableList<Film>) {
        val oldList = filmsAdapter.getFavorites()
        val productDiff = FilmDiff(oldList, newList)
        val diffResult = DiffUtil.calculateDiff(productDiff)
        filmsAdapter.setFavorites(newList)
        diffResult.dispatchUpdatesTo(filmsAdapter)
    }

    private fun initRecycler() {
        //находим наш RV
        binding.favoritesRecycler.apply {
            //Присваиваем адаптер
            adapter = filmsAdapter
            //Присвои layoutmanager
            layoutManager = LinearLayoutManager(requireContext())
            //Применяем декоратор для отступов
            val decorator = ItemDecorator(8)
            addItemDecoration(decorator)
        }

        //Кладем нашу БД в RV
        viewModel.filmsListLiveData.observe(viewLifecycleOwner, {
            favotesDataBase = it as MutableList<Film>
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding.favoritesRecycler.adapter = null
    }
}