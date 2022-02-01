package com.zaus_app.moviefrumy.view.fragments

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.zaus_app.moviefrumy.R
import com.zaus_app.moviefrumy.data.ApiConstants
import com.zaus_app.moviefrumy.data.GenreList
import com.zaus_app.moviefrumy.databinding.FragmentDetailsBinding
import com.zaus_app.moviefrumy.data.entity.Film
import com.zaus_app.moviefrumy.viewmodel.DetailsFragmentViewModel
import kotlinx.coroutines.*
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener


class DetailsFragment : Fragment() {
    private lateinit var film: Film
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private var loadingPoster = false
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(DetailsFragmentViewModel::class.java)
    }
    private val scope = CoroutineScope(Dispatchers.IO)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        film = arguments?.get("film") as Film
        setFilmsDetails(film)

        binding.detailsFabFavorites.setOnClickListener {
            if (!film.isInFavorites) {
                Glide.with( binding.detailsFabFavorites)
                    .load(R.drawable.ic_round_favorite)
                    .into(binding.detailsFabFavorites)
               // binding.detailsFabFavorites.setImageResource(R.drawable.ic_round_favorite)
                film.isInFavorites = true
                viewModel.interactor.addToFavorites(film)
            } else {
                Glide.with( binding.detailsFabFavorites)
                    .load(R.drawable.ic_baseline_favorite_border_24)
                    .into(binding.detailsFabFavorites)
             //   binding.detailsFabFavorites.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                film.isInFavorites = false
                viewModel.interactor.removeFromFavorites(film)
            }
        }

        binding.detailsFabShare.setOnClickListener {     //Создаем интент
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(
                Intent.EXTRA_TEXT,
                resources.getString(R.string.check_out)+" ${film.title} \n ${film.description}"
            )
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, resources.getString(R.string.share_to)))
        }

        binding.detailsFabDownloadWp.setOnClickListener {
            performAsyncLoadOfPoster()
        }

        val downloadFabButton = binding.detailsFabDownloadWp
        downloadFabButton.viewTreeObserver.addOnGlobalLayoutListener(OnGlobalLayoutListener {
            if (downloadFabButton.isShown) {
                binding.progressBar.isVisible = loadingPoster
            }
        })
    }

    private fun setFilmsDetails(film: Film) {
        binding.title.text = film.title
        Glide.with(this)
            .load(ApiConstants.IMAGES_URL + "w780" + film.poster)
            .centerCrop()
            .into(binding.detailsPoster)
        binding.detailsDescription.text = film.description
        var genreText = ""
        for (genre in film.genres) {
            genreText = genreText+GenreList.genres.get(genre)+" "
        }
        binding.genres.text = genreText
        binding.detailsFabFavorites.setImageResource(
            if (film.isInFavorites) R.drawable.ic_round_favorite
            else R.drawable.ic_baseline_favorite_border_24
        )
    }

    private fun saveToGallery(bitmap: Bitmap?): Boolean {
        if (bitmap == null) {
            Snackbar.make(
                binding.root,
                resources.getString(R.string.no_internet),
                Snackbar.LENGTH_LONG
            ).show()
            return false
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.TITLE, film.title.handleSingleQuote())
                put(
                    MediaStore.Images.Media.DISPLAY_NAME,
                    film.title.handleSingleQuote()
                )
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(
                    MediaStore.Images.Media.DATE_ADDED,
                    System.currentTimeMillis() / 1000
                )
                put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MovieFrumyApp")
            }
            val contentResolver = requireActivity().contentResolver
            val uri = contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            val outputStream = contentResolver.openOutputStream(uri!!)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream?.close()
        } else {
            //То же, но для более старых версий ОС
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.insertImage(
                requireActivity().contentResolver,
                bitmap,
                film.title.handleSingleQuote(),
                film.description.handleSingleQuote()
            )
        }
        return true
    }

    private fun String.handleSingleQuote(): String {
        return this.replace("'", "")
    }

    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        )
    }

    private fun performAsyncLoadOfPoster() {
        loadingPoster = true
        if (!checkPermission()) {
            requestPermission()
            return
        }
        MainScope().launch {
             binding.progressBar.isVisible = true
            val job = scope.async {
                viewModel.loadWallpaper(ApiConstants.IMAGES_URL + "original" + film.poster)
            }
            val isSuccesful = saveToGallery(job.await())
            if (isSuccesful) {
                Snackbar.make(
                    binding.root,
                    R.string.downloaded_to_gallery,
                    Snackbar.LENGTH_LONG
                )
                    .setAction(R.string.open) {
                        val intent = Intent()
                        intent.action = Intent.ACTION_VIEW
                        intent.type = "image/*"
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }
                    .show()
            }
            loadingPoster = false
            binding.progressBar.isVisible = false
        }
    }

}