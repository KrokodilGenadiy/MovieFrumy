package com.zaus_app.moviefrumy.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zaus_app.moviefrumy.R
import com.zaus_app.moviefrumy.databinding.FragmentHomeBinding
import com.zaus_app.moviefrumy.databinding.SheetContentBinding
import com.zaus_app.moviefrumy.viewmodel.BottomSheetFragmentViewModel
import com.zaus_app.moviefrumy.viewmodel.SettingsFragmentViewModel

class BottomSheetFragment(homeFragment: HomeFragment) : BottomSheetDialogFragment() {
    private val homeFragment = homeFragment
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
        //Слушаем, какой у нас сейчас выбран вариант в настройках
        //Слушаем, какой у нас сейчас выбран вариант в настройках
        viewModel.categoryPropertyLifeData.observe(viewLifecycleOwner, Observer<String> {
            when(it) {
                POPULAR_CATEGORY -> binding.radioGroup.check(R.id.radio_popular)
                TOP_RATED_CATEGORY -> binding.radioGroup.check(R.id.radio_top_rated)
                UPCOMING_CATEGORY -> binding.radioGroup.check(R.id.radio_upcoming)
                NOW_PLAYING_CATEGORY -> binding.radioGroup.check(R.id.radio_now_playing)
            }
        })

        //Слушатель для отправки нового состояния в настройк
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.radio_popular -> viewModel.putCategoryProperty(POPULAR_CATEGORY)
                R.id.radio_top_rated -> viewModel.putCategoryProperty(TOP_RATED_CATEGORY)
                R.id.radio_upcoming -> viewModel.putCategoryProperty(UPCOMING_CATEGORY)
                R.id.radio_now_playing -> viewModel.putCategoryProperty(NOW_PLAYING_CATEGORY)
            }
        }

        binding.apply.setOnClickListener {
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