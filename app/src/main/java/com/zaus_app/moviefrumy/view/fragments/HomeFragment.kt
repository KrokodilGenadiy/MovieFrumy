package com.zaus_app.moviefrumy.view.fragments

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
import com.zaus_app.moviefrumy.R
import com.zaus_app.moviefrumy.databinding.FragmentHomeBinding
import com.zaus_app.moviefrumy.data.entity.Film
import com.zaus_app.moviefrumy.utils.AnimationHelper
import com.zaus_app.moviefrumy.view.MainActivity
import com.zaus_app.moviefrumy.view.rv_adapters.FilmAdapter
import com.zaus_app.moviefrumy.view.rv_adapters.FilmDiff
import com.zaus_app.moviefrumy.view.rv_adapters.ItemDecorator
import com.zaus_app.moviefrumy.viewmodel.HomeFragmentViewModel
import java.util.*


class HomeFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(HomeFragmentViewModel::class.java)
    }
    private var _binding: FragmentHomeBinding? = null
    private var isRefreshing = false
    private val binding get() = _binding!!
    private lateinit var filmsAdapter: FilmAdapter
    private var filmsDataBase = mutableListOf<Film>()
        //Используем backing field
        set(value) {
            //Если придет такое же значение то мы выходим из метода
            if (field == value) return
            //Если пришло другое значение, то кладем его в переменную
            if (isRefreshing) {
                field = value
                isRefreshing = false
            } else {
                field = (field + value) as MutableList<Film>
            }
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


        AnimationHelper.performFragmentCircularRevealAnimation(
            binding.homeConstraintLayout,
            requireActivity(),
            1
        )


        initToolbar()
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
            updateData(filmsDataBase)
        })

        //initPullToRefresh()

        binding.mainRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    val visibleItemCount = binding.mainRecycler.layoutManager!!.childCount
                    val totalItemCount = binding.mainRecycler.layoutManager!!.itemCount
                    val pastVisibleItemCount =
                        (binding.mainRecycler.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    viewModel.doPagination(visibleItemCount, totalItemCount, pastVisibleItemCount)
                }
            }
        })
    }


    fun initRecycler() {
        //находим наш RV
        binding.mainRecycler.apply {
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
        }
    }

    fun initRefresh() {
             isRefreshing = true
             //Делаем новый запрос фильмов на сервер
             viewModel.getFilms()
            binding.mainRecycler.layoutManager?.scrollToPosition(0)
     }

    fun initToolbar() {
        binding.toolbarMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.filters -> {
                    val bottomSheetFragment = BottomSheetFragment(this)
                    bottomSheetFragment.show(requireActivity().supportFragmentManager,"BottomSheet")
                    true
                }
                else -> false
            }
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