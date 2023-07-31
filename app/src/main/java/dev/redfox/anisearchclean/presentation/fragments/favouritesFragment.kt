package dev.redfox.anisearchclean.presentation.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.redfox.anisearchclean.R
import dev.redfox.anisearchclean.data.Repositories.AnimeRoomDBRepository
import dev.redfox.anisearchclean.data.dataclasses.Animes
import dev.redfox.anisearchclean.data.room.AnimesRoomDatabase
import dev.redfox.anisearchclean.databinding.FragmentFavouritesBinding
import dev.redfox.anisearchclean.presentation.adapters.AnimeWatchListAdapter
import dev.redfox.anisearchclean.presentation.viewmodels.AnimesRoomDBViewModel

@AndroidEntryPoint
class favouritesFragment : Fragment() {

    val animesDBViewModel: AnimesRoomDBViewModel by viewModels()
    lateinit var animeWatchListAdapter: AnimeWatchListAdapter
    private var _binding: FragmentFavouritesBinding? = null
    private val binding
        get() = _binding!!


    private val database by lazy {
        AnimesRoomDatabase.getAnimesDatabase(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachObserver()
    }



    fun attachObserver() {
        animesDBViewModel.allAnimesLists.observe(viewLifecycleOwner, Observer{

            val watchData: MutableList<Animes> = it

            animeWatchListAdapter = AnimeWatchListAdapter(animesDBViewModel, watchData, requireContext())
            binding.rvWatchList.adapter = animeWatchListAdapter
            binding.rvWatchList.setHasFixedSize(true)
            binding.rvWatchList.layoutManager = LinearLayoutManager(context)

            animeWatchListAdapter.notifyDataSetChanged()
        })

    }

}