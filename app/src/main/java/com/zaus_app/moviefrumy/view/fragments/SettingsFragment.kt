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
import com.zaus_app.moviefrumy.view.MainActivity
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
        val languages = resources.getStringArray(R.array.languages)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.languege_selection, languages)
        binding.languageSelection.setAdapter(arrayAdapter)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val languages = resources.getStringArray(R.array.languages)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.languege_selection, languages)
        binding.languageSelection.setAdapter(arrayAdapter)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Подключаем анимации и передаем номер позиции у кнопки в нижнем меню
        val selections = resources.getStringArray(R.array.languages)
        var arrayAdapter: ArrayAdapter<String>
        AnimationHelper.performFragmentCircularRevealAnimation(
            binding.settingsFragmentRoot,
            requireActivity(),
            4
        )
        //Слушаем, какой у нас сейчас выбран вариант в настройках
        viewModel.languagePropertyLiveData.observe(viewLifecycleOwner, {
            when (it) {
                RUSSIAN_LANGUAGE   -> {
                    binding.languageSelection.setText(R.string.russian)
                    arrayAdapter =
                        ArrayAdapter(requireContext(), R.layout.languege_selection, selections)
                    binding.languageSelection.setAdapter(arrayAdapter)
                }
                ENGLISH_LANGUAGE -> {
                    binding.languageSelection.setText(R.string.english)
                    arrayAdapter =
                        ArrayAdapter(requireContext(), R.layout.languege_selection, selections)
                    binding.languageSelection.setAdapter(arrayAdapter)
                }
            }
        })

        //Слушатель для отправки нового состояния в настройки
        binding.languageSelection.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                when (parent.getItemAtPosition(position).toString()) {
                    resources.getString(R.string.russian) -> {
                        if (viewModel.languagePropertyLiveData.value != RUSSIAN_LANGUAGE) {
                            viewModel.putLanguageProperty(RUSSIAN_LANGUAGE)
                            (activity as MainActivity).setLocale("ru")
                        }

                    }
                    resources.getString(R.string.english) -> {
                        if (viewModel.languagePropertyLiveData.value != ENGLISH_LANGUAGE) {
                            viewModel.putLanguageProperty(ENGLISH_LANGUAGE)
                            (activity as MainActivity).setLocale("en")
                        }

                    }
                }
            }
    }


    companion object {
        private const val RUSSIAN_LANGUAGE = "ru"
        private const val ENGLISH_LANGUAGE = "en"
    }
}