package org.kimp.kinopoisk.tinkoff.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import coil.request.ImageRequest
import dagger.hilt.android.AndroidEntryPoint
import org.kimp.kinopoisk.tinkoff.data.model.AboutFragmentViewModel
import org.kimp.kinopoisk.tinkoff.databinding.FragmentAboutBinding
import org.kimp.kinopoisk.tinkoff.R
import timber.log.Timber
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class AboutFragment: Fragment() {
    private val viewModel: AboutFragmentViewModel by activityViewModels()

    @Inject
    lateinit var imageLoader: ImageLoader

    private lateinit var binding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutBinding.inflate(
            inflater, container, false
        )

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.film().value!!.also {
            binding.film = it

            binding.genresList.text = it.genres
                .joinToString { x -> x.genre }
                .replaceFirstChar { ch ->
                    if (ch.isLowerCase()) ch.titlecase(Locale.getDefault()) else ch.toString()
                }
            binding.countriesList.text = it.countries
                .joinToString { x -> x.country }
                .replaceFirstChar { ch ->
                    if (ch.isLowerCase()) ch.titlecase(Locale.getDefault()) else ch.toString()
                }

            ImageRequest.Builder(requireContext())
                .data(it.posterUrl)
                .placeholder(R.drawable.ic_cloud)
                .error(R.drawable.ic_network_error)
                .target(
                    onSuccess = {
                        binding.posterImageView.apply {
                            scaleType = ImageView.ScaleType.CENTER_CROP
                            setImageDrawable(it)
                        }
                    },
                    onStart = {
                        binding.posterImageView.apply {
                            scaleType = ImageView.ScaleType.FIT_CENTER
                            setImageDrawable(it)
                        }
                    },
                    onError = {
                        binding.posterImageView.apply {
                            scaleType = ImageView.ScaleType.FIT_CENTER
                            setImageDrawable(it)
                        }
                    }
                )
                .build().apply {
                    imageLoader.enqueue(this)
                }

            Timber.i("Started AboutFragment for: ${it.nameRu}")
        }

        return binding.root
    }
}