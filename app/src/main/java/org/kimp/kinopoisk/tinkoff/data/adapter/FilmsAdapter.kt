package org.kimp.kinopoisk.tinkoff.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.marginTop
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.ImageLoader
import coil.request.ImageRequest
import org.kimp.kinopoisk.tinkoff.data.remote.dto.FilmDto
import org.kimp.kinopoisk.tinkoff.databinding.ViewFilmCardBinding
import org.kimp.kinopoisk.tinkoff.R
import org.kimp.kinopoisk.tinkoff.data.model.AboutFragmentViewModel
import org.kimp.kinopoisk.tinkoff.databinding.ViewLoadingBinding
import timber.log.Timber
import java.util.Locale
import javax.inject.Inject
import kotlin.math.max

class FilmsAdapter @Inject constructor(
    val imageLoader: ImageLoader,
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var listener: FilmSelectedListener? = null
    var films: List<FilmDto> = listOf()

    class LoadingViewHolder(
        private val binding: ViewLoadingBinding,
        val parentHeight: Int
    ): RecyclerView.ViewHolder(binding.root) {}

    class FilmsViewHolder(private val binding: ViewFilmCardBinding): RecyclerView.ViewHolder(binding.root) {
        val title = binding.title
        val subtitle = binding.subtitle
        val favoriteIndicator = binding.favoriteIndicator
        val poster = binding.posterImageView
    }

    override fun getItemViewType(position: Int): Int {
        return if (films.isEmpty()) 0 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (films.isEmpty()) {
            return LoadingViewHolder(
                ViewLoadingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                parent.height
            )
        }
        return FilmsViewHolder(
            ViewFilmCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (films.isEmpty()) {
            val loadingViewHolder = holder as LoadingViewHolder
            loadingViewHolder.itemView.apply {
                updatePadding(
                    top = (loadingViewHolder.parentHeight - height) / 4
                )
            }
            return
        }
        val filmHolder = holder as FilmsViewHolder

        filmHolder.title.text = films[position].nameRu
        filmHolder.subtitle.text = "(${films[position].genres[0].genre.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }} ${films[position].year})"
        filmHolder.favoriteIndicator.visibility = View.GONE

        ImageRequest.Builder(filmHolder.itemView.context)
            .data(films[position].posterUrlPreview)
            .diskCacheKey(films[position].posterUrlPreview)
            .placeholder(R.drawable.ic_cloud)
            .error(R.drawable.ic_network_error)
            .target (
                onStart = {
                    filmHolder.poster.apply {
                        scaleType = ImageView.ScaleType.FIT_CENTER
                        setImageDrawable(it)
                    }
                },
                onSuccess = {
                    filmHolder.poster.apply {
                        scaleType = ImageView.ScaleType.CENTER_CROP
                        setImageDrawable(it)
                    }
                },
                onError = {
                    filmHolder.poster.apply {
                        scaleType = ImageView.ScaleType.FIT_CENTER
                        setImageDrawable(it)
                    }
                }
            )
            .build().apply {
                imageLoader.enqueue(this)
            }

        filmHolder.itemView.setOnClickListener {
            Timber.i("Clicked ${position} - ${films[position].nameRu}")
            listener?.filmSelected(films[position])
        }
    }

    override fun getItemCount(): Int {
        return max(1, films.size)
    }

}