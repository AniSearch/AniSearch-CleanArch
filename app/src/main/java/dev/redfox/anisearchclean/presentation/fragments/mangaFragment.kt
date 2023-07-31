package dev.redfox.anisearchclean.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.redfox.anisearchclean.data.dataclasses.topMangaModel.Data
import dev.redfox.anisearchclean.data.utils.Snacker
import dev.redfox.anisearchclean.databinding.FragmentMangaBinding
import dev.redfox.anisearchclean.presentation.adapters.AnimeTopSearchAdapter
import dev.redfox.anisearchclean.presentation.adapters.MangaTopSearchAdapter
import dev.redfox.anisearchclean.presentation.sheets.AnimeDetailsBottomSheet
import dev.redfox.anisearchclean.presentation.sheets.MangaDetailsBottomSheet
import dev.redfox.anisearchclean.presentation.viewmodels.AnimeViewModel


@AndroidEntryPoint
class mangaFragment : Fragment() {

    private lateinit var mangaAdapter: MangaTopSearchAdapter
    val animeViewModel: AnimeViewModel by viewModels()
    private var _binding: FragmentMangaBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMangaBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callTopAnime()
        initClicks()
        attachObservers()
    }

    fun initClicks() {

        binding.btnSearch.setOnClickListener {

            binding.shimmerEffect.visibility = View.VISIBLE

            var queryString: String = binding.searchEditText.text.toString()

            if (queryString.isNotEmpty()) {
                animeViewModel.getMangaSearch(queryString)
            }
        }
    }

    private fun attachObservers() {
        animeViewModel.topMangaeResponse.observe(viewLifecycleOwner, Observer {
            val data: MutableList<Data> = it.body()!!.data as MutableList<Data>

            binding.shimmerEffect.visibility = View.GONE

            mangaAdapter = MangaTopSearchAdapter(data)
            mangaAdapter.notifyDataSetChanged()
            binding.searchRecyclerView.setHasFixedSize(true)
            binding.searchRecyclerView.adapter = mangaAdapter
            binding.searchRecyclerView.layoutManager = GridLayoutManager(context, 2)
            mangaAdapter.notifyDataSetChanged()

            mangaAdapter.onItemClick = {
                val dialog = MangaDetailsBottomSheet(it)
                dialog.setCancelable(true)
                dialog.show(parentFragmentManager, "AnimeBottomSheetDialog")
            }

            mangaAdapter.onItemLongClick = {
                Toast.makeText(context, "Feature to be added soon", Toast.LENGTH_SHORT).show()
            }

        })

        animeViewModel.searchMangaResponse.observe(viewLifecycleOwner, Observer {
            val sData: MutableList<Data> = it.body()!!.data as MutableList<Data>

            binding.shimmerEffect.visibility = View.GONE

            mangaAdapter = MangaTopSearchAdapter(sData)

            var adapter = mangaAdapter

            adapter.notifyDataSetChanged()
            binding.searchRecyclerView.setHasFixedSize(true)
            binding.searchRecyclerView.adapter = adapter
            binding.searchRecyclerView.layoutManager = GridLayoutManager(context, 2)
            adapter.notifyDataSetChanged()

            mangaAdapter.onItemClick = {
                val dialog = MangaDetailsBottomSheet(it)
                dialog.setCancelable(true)
                dialog.show(parentFragmentManager, "AnimeBottomSheetDialog")
            }

            mangaAdapter.onItemLongClick = {
                Toast.makeText(context, "Feature to be added soon", Toast.LENGTH_SHORT).show()
            }

        })

    }


    private fun callTopAnime() {
        animeViewModel.getTopManga()
    }
}