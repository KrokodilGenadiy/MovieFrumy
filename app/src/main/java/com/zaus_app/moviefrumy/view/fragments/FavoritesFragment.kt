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
import com.zaus_app.moviefrumy.utils.Converter
import com.zaus_app.moviefrumy.view.MainActivity
import com.zaus_app.moviefrumy.view.rv_adapters.FavoritesAdapter
import com.zaus_app.moviefrumy.view.rv_adapters.diffutils.FilmDiff
import com.zaus_app.moviefrumy.view.rv_adapters.itemdecorators.ItemDecorator
import com.zaus_app.moviefrumy.viewmodel.FavoriteFragmentViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect


class FavoritesFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(FavoriteFragmentViewModel::class.java)
    }
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var filmsAdapter: FavoritesAdapter
    private lateinit var scope: CoroutineScope
    private var favoritesDataBase = mutableListOf<Film>()
        set(value) {
            if (field == value) return
            field = value
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AnimationHelper.performFragmentCircularRevealAnimation(binding.favoritesFragment, requireActivity(), 2)

        initRecycler()

        scope = CoroutineScope(Dispatchers.IO).also { scope ->
            scope.launch {
                viewModel.filmsListData.collect {
                    withContext(Dispatchers.Main) {
                        favoritesDataBase = Converter.convertListOfFavoriteToFilmList(it)
                        if (favoritesDataBase.size == 0) {
                            binding.listIsEmptyText.visibility = View.VISIBLE
                            binding.lottieAnim.visibility = View.VISIBLE
                        } else {
                            binding.listIsEmptyText.visibility = View.GONE
                            binding.lottieAnim.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        scope.cancel()
    }

    fun updateData(newList: MutableList<Film>) {
        val oldList = filmsAdapter.getFavorites()
        val productDiff = FilmDiff(oldList, newList)
        val diffResult = DiffUtil.calculateDiff(productDiff)
        filmsAdapter.setFavorites(newList)
        diffResult.dispatchUpdatesTo(filmsAdapter)
    }

    private fun initRecycler() {
        binding.favoritesRecycler.apply {
            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val decorator = ItemDecorator(8)
            addItemDecoration(decorator)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding.favoritesRecycler.adapter = null
    }
}