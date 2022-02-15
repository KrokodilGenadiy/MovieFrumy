package com.zaus_app.moviefrumy.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.zaus_app.moviefrumy.R
import com.zaus_app.moviefrumy.data.entity.Film
import com.zaus_app.moviefrumy.databinding.FragmentHomeBinding
import com.zaus_app.moviefrumy.utils.AnimationHelper
import com.zaus_app.moviefrumy.view.MainActivity
import com.zaus_app.moviefrumy.view.rv_adapters.FilmAdapter
import com.zaus_app.moviefrumy.view.rv_adapters.diffutils.FilmDiff
import com.zaus_app.moviefrumy.view.rv_adapters.itemdecorators.ItemDecorator
import com.zaus_app.moviefrumy.viewmodel.HomeFragmentViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.util.*


class HomeFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(HomeFragmentViewModel::class.java)
    }
    private var _binding: FragmentHomeBinding? = null
    private var isRefreshing = false
    private val binding get() = _binding!!
    private lateinit var filmsAdapter: FilmAdapter
    private lateinit var scope: CoroutineScope
    private var filmsDataBase = mutableListOf<Film>()
        set(value) {
            if (field == value) return
            if (isRefreshing || ((viewModel.isFromInternet) && (viewModel.currentPage == 1))) {
                field = value
                viewModel.isFromInternet = false
                isRefreshing = false
            } else {
                field = (field + value) as MutableList<Film>
            }
            updateData(field)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AnimationHelper.performFragmentCircularRevealAnimation(
            binding.homeConstraintLayout,
            requireActivity(),
            1
        )
        initToolbar()
        initSearchView()
        initRecycler()
        viewModel.status.observe(viewLifecycleOwner) {
            if (it)
                Snackbar.make(
                    binding.root,
                    "No internet connection",
                    Snackbar.LENGTH_LONG
                ).show()
            viewModel.status.postValue(false)
        }
        scope = CoroutineScope(Dispatchers.IO).also { scope ->
            scope.launch {
                viewModel.filmsListData.collect {
                    withContext(Dispatchers.Main) {
                        filmsDataBase = it as MutableList<Film>
                    }
                }
            }
            scope.launch {
                for (element in viewModel.showShimmering) {
                    launch(Dispatchers.Main) {
                        if (element)
                            binding.include.shimmerLayout.startShimmer()
                        else
                            binding.include.shimmerLayout.stopShimmer()
                        binding.include.shimmerLayout.isVisible = element
                    }
                }
            }
        }

        binding.include.mainRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    val visibleItemCount = binding.include.mainRecycler.layoutManager!!.childCount
                    val totalItemCount = binding.include.mainRecycler.layoutManager!!.itemCount
                    val pastVisibleItemCount =
                        (binding.include.mainRecycler.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    viewModel.doPagination(visibleItemCount, totalItemCount, pastVisibleItemCount)
                }
            }
        })
    }

    override fun onStop() {
        super.onStop()
        scope.cancel()
    }

    fun initRecycler() {
        binding.include.mainRecycler.apply {
            filmsAdapter = FilmAdapter(object : FilmAdapter.OnItemClickListener {
                override fun click(film: Film) {
                    (requireActivity() as MainActivity).launchDetailsFragment(film)
                }
            })
            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val decorator = ItemDecorator(8)
            addItemDecoration(decorator)
        }
    }

    fun initSearchView() {
        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    updateData(filmsDataBase)
                    return true
                }
                val result = filmsDataBase.filter {
                    it.title.lowercase(Locale.getDefault())
                        .contains(newText.lowercase(Locale.getDefault()))
                }
                updateData(result as MutableList<Film>)
                return true
            }
        })
    }

    fun initRefresh() {
        isRefreshing = true
        viewModel.getFilms()
        binding.include.mainRecycler.layoutManager?.scrollToPosition(0)
    }

    fun initToolbar() {
        binding.toolbarMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.filters -> {
                    val bottomSheetFragment = BottomSheetFragment(this)
                    bottomSheetFragment.show(
                        requireActivity().supportFragmentManager,
                        "BottomSheet"
                    )
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