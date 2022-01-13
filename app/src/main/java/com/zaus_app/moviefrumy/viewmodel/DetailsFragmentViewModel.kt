package com.zaus_app.moviefrumy.viewmodel


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import com.zaus_app.moviefrumy.App
import com.zaus_app.moviefrumy.domain.Interactor
import java.io.IOException
import java.net.URL
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class DetailsFragmentViewModel : ViewModel() {
    @Inject
    lateinit var interactor: Interactor

    init {
        App.instance.dagger.inject(this)
    }

    suspend fun loadWallpaper(url: String): Bitmap? {
        return suspendCoroutine {
            var bitmap: Bitmap? = null
            try {
                val client = URL(url).openConnection()
                bitmap = BitmapFactory.decodeStream(client.getInputStream())
            } catch (e: IOException) {

            }
            it.resume(bitmap)
        }
    }
}