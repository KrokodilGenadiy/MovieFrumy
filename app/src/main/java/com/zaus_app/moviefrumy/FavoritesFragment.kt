package com.zaus_app.moviefrumy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.zaus_app.moviefrumy.databinding.FragmentFavoritesBinding
import com.zaus_app.moviefrumy.domain.Film
import com.zaus_app.moviefrumy.utils.AnimationHelper


class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var filmsAdapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AnimationHelper.performFragmentCircularRevealAnimation(
            binding.favoritesFragment,
            requireActivity(),
            2
        )
        //находим наш RV
        _binding?.favoritesRecycler?.apply {
            filmsAdapter = FavoritesAdapter(object : FavoritesAdapter.OnItemClickListener {
                override fun click(film: Film) {
                    (requireActivity() as MainActivity).launchDetailsFragment(film)
                }
            })
            //Присваиваем адаптер
            adapter = filmsAdapter
            //Присвои layoutmanager
            layoutManager = LinearLayoutManager(requireContext())
            //Применяем декоратор для отступов
            val decorator = ItemDecorator(8)
            addItemDecoration(decorator)
        }
        //Кладем нашу БД в RV
        if (Database.favoritesList.isEmpty()) {
            _binding?.listIsEmptyText?.visibility = View.VISIBLE
            _binding?.lottieAnim?.visibility = View.VISIBLE
        } else {
            _binding?.listIsEmptyText?.visibility = View.GONE
            _binding?.lottieAnim?.visibility = View.GONE
        }


        updateData(Database.favoritesList)
    }

    fun updateData(newList: MutableList<Film>) {
        val oldList = filmsAdapter.getFavorites()
        val productDiff = FilmDiff(oldList, newList)
        val diffResult = DiffUtil.calculateDiff(productDiff)
        filmsAdapter.setFavorites(newList)
        diffResult.dispatchUpdatesTo(filmsAdapter)
    }
}