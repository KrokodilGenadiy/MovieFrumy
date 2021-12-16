package com.zaus_app.moviefrumy.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zaus_app.moviefrumy.view.rv_adapters.FilmAdapter
import com.zaus_app.moviefrumy.view.rv_adapters.FilmDiff
import com.zaus_app.moviefrumy.view.rv_adapters.ItemDecorator
import com.zaus_app.moviefrumy.view.MainActivity
import com.zaus_app.moviefrumy.databinding.FragmentHomeBinding
import com.zaus_app.moviefrumy.domain.Film
import com.zaus_app.moviefrumy.utils.AnimationHelper
import com.zaus_app.moviefrumy.viewmodel.HomeFragmentViewModel
import java.util.*
import androidx.core.widget.NestedScrollView
import com.zaus_app.moviefrumy.App

class HomeFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(HomeFragmentViewModel::class.java)
    }
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var filmsAdapter: FilmAdapter
    private var filmsDataBase = mutableListOf<Film>()
        //Используем backing field
        set(value) {
            //Если придет такое же значение то мы выходим из метода
            if (field == value) return
            //Если прило другое значение, то кладем его в переменную
            field = (field + value) as MutableList<Film>
            //Обновляем RV адаптер
            updateData(field)
        }

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
                    updateData(filmsDataBase)
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
        viewModel.filmsListLiveData.observe(viewLifecycleOwner, {
            filmsDataBase = it as MutableList<Film>
        })


        binding.include.mainNestedScrollview.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if(v.getChildAt(v.childCount - 1) != null) {
                if ((scrollY >= (v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight) - 3000) && scrollY > oldScrollY) {
                    val visibleItemCount = binding.include.mainRecycler.layoutManager!!.childCount
                    val totalItemCount = binding.include.mainRecycler.layoutManager!!.itemCount
                    val pastVisibleItemCount = (binding.include.mainRecycler.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    viewModel.doPagination(visibleItemCount, totalItemCount, pastVisibleItemCount)
                }
            }
        })
    }


    fun initRecycler() {
        //находим наш RV
        binding.include.mainRecycler.apply {
            filmsAdapter = FilmAdapter(object : FilmAdapter.OnItemClickListener {
                override fun click(film: Film) {
                    (requireActivity() as MainActivity).launchDetailsFragment(film)
                }
            })
            //Присваиваем адаптер
            adapter = filmsAdapter
            //Присвои layoutmanager
            layoutManager = LinearLayoutManager(requireContext())
            //добавляем пагинацию
           // initPagination()
            //Применяем декоратор для отступов
            val decorator = ItemDecorator(8)
            addItemDecoration(decorator)
            isNestedScrollingEnabled = false
        }
    }

    private fun RecyclerView.initPagination() {
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val visibleItemCount = recyclerView.layoutManager!!.childCount
                    val totalItemCount = recyclerView.layoutManager!!.itemCount
                    val pastVisibleItemCount = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    viewModel.doPagination(
                        visibleItemCount = visibleItemCount,
                        totalItemCount = totalItemCount,
                        pastVisibleItemCOunt = pastVisibleItemCount
                    )
                }
            }
        })
    }

    fun updateData(newList: MutableList<Film>) {
        val oldList = filmsAdapter.getItems()
        val productDiff = FilmDiff(oldList, newList)
        val diffResult = DiffUtil.calculateDiff(productDiff)
        filmsAdapter.setItems(newList)
        diffResult.dispatchUpdatesTo(filmsAdapter)
    }

}