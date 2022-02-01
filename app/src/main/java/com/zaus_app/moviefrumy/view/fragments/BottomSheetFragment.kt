package com.zaus_app.moviefrumy.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zaus_app.moviefrumy.R
import com.zaus_app.moviefrumy.data.GenreList
import com.zaus_app.moviefrumy.data.entity.Genre
import com.zaus_app.moviefrumy.databinding.FragmentHomeBinding
import com.zaus_app.moviefrumy.databinding.SheetContentBinding
import com.zaus_app.moviefrumy.view.rv_adapters.GenreAdapter
import com.zaus_app.moviefrumy.view.rv_adapters.itemdecorators.ItemDecorator
import com.zaus_app.moviefrumy.viewmodel.BottomSheetFragmentViewModel
import com.zaus_app.moviefrumy.viewmodel.SettingsFragmentViewModel

class BottomSheetFragment(homeFragment: HomeFragment) : BottomSheetDialogFragment() {
    private val homeFragment = homeFragment
    private lateinit var genreAdapter: GenreAdapter
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(BottomSheetFragmentViewModel::class.java)
    }
    private lateinit var binding: SheetContentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val inflater = LayoutInflater.from(requireContext())
        binding = SheetContentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.include.genresList.apply {
            genreAdapter = GenreAdapter(object : GenreAdapter.OnItemClickListener{
                override fun click(genre: Genre) {
                  Toast.makeText(requireContext(),"Clicked!",Toast.LENGTH_SHORT).show()
                }
            })
            adapter = genreAdapter

            layoutManager = GridLayoutManager(requireContext(), 5)
            val decorator = ItemDecorator(6)
            addItemDecoration(decorator)
        }
        genreAdapter.items = GenreList.genrelist
        viewModel.categoryPropertyLifeData.observe(viewLifecycleOwner, Observer<String> {
            when(it) {
                POPULAR_CATEGORY -> binding.include.radioGroup.check(R.id.radio_popular)
                TOP_RATED_CATEGORY -> binding.include.radioGroup.check(R.id.radio_top_rated)
                UPCOMING_CATEGORY -> binding.include.radioGroup.check(R.id.radio_upcoming)
                NOW_PLAYING_CATEGORY -> binding.include.radioGroup.check(R.id.radio_now_playing)
            }
        })

        //Слушатель для отправки нового состояния в настройк
        binding.include.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId) {
                R.id.radio_popular -> viewModel.putCategoryProperty(POPULAR_CATEGORY)
                R.id.radio_top_rated -> viewModel.putCategoryProperty(TOP_RATED_CATEGORY)
                R.id.radio_upcoming -> viewModel.putCategoryProperty(UPCOMING_CATEGORY)
                R.id.radio_now_playing -> viewModel.putCategoryProperty(NOW_PLAYING_CATEGORY)
            }
        }

        binding.include.apply.setOnClickListener {
            homeFragment.initRefresh()
            dismiss()
        }
    }

    companion object {
        private const val POPULAR_CATEGORY = "popular"
        private const val TOP_RATED_CATEGORY = "top_rated"
        private const val UPCOMING_CATEGORY = "upcoming"
        private const val NOW_PLAYING_CATEGORY = "now_playing"
    }
}