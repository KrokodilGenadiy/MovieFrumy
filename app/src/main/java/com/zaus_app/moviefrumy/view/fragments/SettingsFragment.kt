package com.zaus_app.moviefrumy.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.zaus_app.moviefrumy.R
import com.zaus_app.moviefrumy.databinding.FragmentSettingsBinding
import com.zaus_app.moviefrumy.utils.AnimationHelper
import com.zaus_app.moviefrumy.viewmodel.SettingsFragmentViewModel


class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(SettingsFragmentViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        val selections = resources.getStringArray(R.array.selections)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_selections, selections)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val selections = resources.getStringArray(R.array.selections)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_selections, selections)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Подключаем анимации и передаем номер позиции у кнопки в нижнем меню
        val selections = resources.getStringArray(R.array.selections)
        var arrayAdapter: ArrayAdapter<String>
        AnimationHelper.performFragmentCircularRevealAnimation(
            binding.settingsFragmentRoot,
            requireActivity(),
            4
        )
        //Слушаем, какой у нас сейчас выбран вариант в настройках
        viewModel.categoryPropertyLifeData.observe(viewLifecycleOwner, {
            when (it) {
                POPULAR_CATEGORY -> {
                    binding.autoCompleteTextView.setText(R.string.popular)
                    arrayAdapter =
                        ArrayAdapter(requireContext(), R.layout.dropdown_selections, selections)
                    binding.autoCompleteTextView.setAdapter(arrayAdapter)
                }
                TOP_RATED_CATEGORY -> {
                    binding.autoCompleteTextView.setText(R.string.top_rated)
                    arrayAdapter =
                        ArrayAdapter(requireContext(), R.layout.dropdown_selections, selections)
                    binding.autoCompleteTextView.setAdapter(arrayAdapter)
                }
                UPCOMING_CATEGORY -> {
                    binding.autoCompleteTextView.setText(R.string.upcoming)
                    arrayAdapter =
                        ArrayAdapter(requireContext(), R.layout.dropdown_selections, selections)
                    binding.autoCompleteTextView.setAdapter(arrayAdapter)
                }
                NOW_PLAYING_CATEGORY -> {
                    binding.autoCompleteTextView.setText(R.string.playing)
                    arrayAdapter =
                        ArrayAdapter(requireContext(), R.layout.dropdown_selections, selections)
                    binding.autoCompleteTextView.setAdapter(arrayAdapter)
                }
            }
        })

        //Слушатель для отправки нового состояния в настройк
        binding.autoCompleteTextView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                when (parent.getItemAtPosition(position).toString()) {
                    resources.getString(R.string.popular) -> viewModel.putCategoryProperty(
                        POPULAR_CATEGORY
                    )
                    resources.getString(R.string.top_rated) -> viewModel.putCategoryProperty(
                        TOP_RATED_CATEGORY
                    )
                    resources.getString(R.string.upcoming) -> viewModel.putCategoryProperty(
                        UPCOMING_CATEGORY
                    )
                    resources.getString(R.string.playing) -> viewModel.putCategoryProperty(
                        NOW_PLAYING_CATEGORY
                    )
                }
            }

    }

    companion object {
        private const val POPULAR_CATEGORY = "popular"
        private const val TOP_RATED_CATEGORY = "top_rated"
        private const val UPCOMING_CATEGORY = "upcoming"
        private const val NOW_PLAYING_CATEGORY = "now_playing"
    }
}