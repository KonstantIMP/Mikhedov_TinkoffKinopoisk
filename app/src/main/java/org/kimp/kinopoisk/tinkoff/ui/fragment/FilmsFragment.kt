package org.kimp.kinopoisk.tinkoff.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.kimp.kinopoisk.tinkoff.R
import org.kimp.kinopoisk.tinkoff.data.FilmsFragmentMode
import org.kimp.kinopoisk.tinkoff.data.adapter.FilmSelectedListener
import org.kimp.kinopoisk.tinkoff.data.adapter.FilmsAdapter
import org.kimp.kinopoisk.tinkoff.data.model.AboutFragmentViewModel
import org.kimp.kinopoisk.tinkoff.data.model.FavoriteViewModel
import org.kimp.kinopoisk.tinkoff.data.model.FilmsFragmentViewModel
import org.kimp.kinopoisk.tinkoff.data.remote.dto.FilmDto
import org.kimp.kinopoisk.tinkoff.databinding.FragmentFilmsBinding
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class FilmsFragment: Fragment(), FilmSelectedListener, SearchView.OnQueryTextListener {
    @Inject lateinit var adapter: FilmsAdapter

    private val aboutModel: AboutFragmentViewModel by activityViewModels()
    private val faforitesModel: FavoriteViewModel by viewModels()
    private val viewModel: FilmsFragmentViewModel by viewModels()

    private lateinit var binding: FragmentFilmsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilmsBinding.inflate(
            layoutInflater, container, false
        )

        binding.filmsRecyclerView.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL).apply {
                setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.films_fragment_cards_divider)!!)
            }
        )
        binding.toolbar.apply {
            inflateMenu(R.menu.search_menu)
            (menu.findItem(R.id.action_search)
                .actionView as SearchView).setOnQueryTextListener(this@FilmsFragment)
        }
        binding.viewModel = viewModel

        binding.filmsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.filmsRecyclerView.adapter = adapter

        binding.retryBtn.setOnClickListener { viewModel.loadFilms() }
        if (faforitesModel.films().value!!.isEmpty()) faforitesModel.loadFilms()
        if (viewModel.films().value!!.isEmpty()) viewModel.loadFilms()

        adapter.films = viewModel.films().value!!
        adapter.manager = faforitesModel
        adapter.listener = this

        viewModel.films().observe(viewLifecycleOwner) {filmsList ->
            adapter.apply {
                films = filmsList
                notifyDataSetChanged()
            }
        }

        viewModel.apply {
            hasErrors().observe(viewLifecycleOwner) { binding.viewModel = viewModel }
            currentMode().observe(viewLifecycleOwner) { binding.viewModel = viewModel }
        }

        return binding.root
    }

    override fun filmSelected(film: FilmDto) {
        aboutModel.setFilm(film)
        FilmsFragmentDirections.actionFilmsFragmentToAboutFragment()
            .apply {
                findNavController().navigate(this)
            }
    }

    override fun onQueryTextSubmit(query: String?): Boolean = false

    override fun onQueryTextChange(newText: String): Boolean {
        val found = ArrayList<FilmDto>()

        viewModel.films().value?.forEach {
            if (it.nameRu.lowercase(Locale.getDefault()).contains(newText.lowercase(Locale.getDefault()))) {
                found.add(it)
            }
        }

        if (found.isEmpty()) Snackbar.make(requireContext(), binding.root, getString(R.string.not_found_snack), Snackbar.LENGTH_LONG).show()
        else {
            adapter.films = found
            adapter.notifyDataSetChanged()
        }

        return false
    }
}
