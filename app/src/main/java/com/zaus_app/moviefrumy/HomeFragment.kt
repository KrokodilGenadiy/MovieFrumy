package com.zaus_app.moviefrumy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.zaus_app.moviefrumy.Database.filmsDataBase
import com.zaus_app.moviefrumy.databinding.FragmentHomeBinding
import java.util.*

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var filmsAdapter: FilmAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AnimationHelper.performFragmentCircularRevealAnimation(binding.homeConstraintLayout, requireActivity(), 1)

        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            //Этот метод отрабатывает при нажатии кнопки "поиск" на софт клавиатуре
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            //Этот метод отрабатывает на каждое изменения текста
            override fun onQueryTextChange(newText: String): Boolean {
                //Если ввод пуст то вставляем в адаптер всю БД
                if (newText.isEmpty()) {
                    updateData(filmsDataBase as MutableList<Film>)
                    return true
                }
                //Фильтруем список на поискк подходящих сочетаний
                val result = filmsDataBase.filter {
                    //Чтобы все работало правильно, нужно и запрос, и имя фильма приводить к нижнему регистру
                    it.title.lowercase(Locale.getDefault())
                        .contains(newText.lowercase(Locale.getDefault()))
                }
                //Добавляем в адаптер
                updateData(result as MutableList<Film>)
                return true
            }
        })
        initRecycler()
        //Кладем нашу БД в RV
        updateData(filmsDataBase as MutableList<Film>)

        binding.include.mainRecycler.isNestedScrollingEnabled = false
    }


    fun initRecycler() {
        //находим наш RV
        _binding?.include?.mainRecycler?.apply {
            filmsAdapter = FilmAdapter(object : FilmAdapter.OnItemClickListener {
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
    }

    fun updateData(newList: MutableList<Film>) {
        val oldList = filmsAdapter.getItems()
        val productDiff = FilmDiff(oldList, newList)
        val diffResult = DiffUtil.calculateDiff(productDiff)
        filmsAdapter.setItems(newList)
        diffResult.dispatchUpdatesTo(filmsAdapter)
    }

}