package dev.redfox.anisearchclean.presentation.fragments

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.card.MaterialCardView
import dagger.hilt.android.AndroidEntryPoint
import dev.redfox.anisearchclean.R
import dev.redfox.anisearchclean.data.dataclasses.Animes
import dev.redfox.anisearchclean.data.utils.Snacker
import dev.redfox.anisearchclean.databinding.FragmentAnimeBinding
import dev.redfox.anisearchclean.presentation.adapters.AnimeTopSearchAdapter
import dev.redfox.anisearchclean.presentation.sheets.AnimeDetailsBottomSheet
import dev.redfox.anisearchclean.presentation.viewmodels.AnimeViewModel
import dev.redfox.anisearchclean.presentation.viewmodels.AnimesRoomDBViewModel
import dev.refox.anitrack.models.topAnimeModel.Data

@AndroidEntryPoint
class animeFragment : Fragment() {

    private lateinit var animeAdapter: AnimeTopSearchAdapter
    val animeViewModel: AnimeViewModel by viewModels()
    val animesRoomDBViewModel: AnimesRoomDBViewModel by viewModels()
    private var _binding: FragmentAnimeBinding? = null
    private val binding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAnimeBinding.inflate(inflater, container, false)
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

            hideKeyboard()

            var queryString: String = binding.searchEditText.text.toString()

            if (queryString.isNotEmpty()) {
                animeViewModel.getAnimeSearch(queryString)
            }
        }
    }

    private fun attachObservers() {
        animeViewModel.topAnimeResponse.observe(viewLifecycleOwner, Observer {
            val data: MutableList<Data> = it.body()!!.data as MutableList<Data>

            binding.shimmerEffect.visibility = View.GONE

            animeAdapter = AnimeTopSearchAdapter(data)
            animeAdapter.notifyDataSetChanged()
            binding.searchRecyclerView.setHasFixedSize(true)
            binding.searchRecyclerView.adapter = animeAdapter
            binding.searchRecyclerView.layoutManager = GridLayoutManager(context, 2)
            animeAdapter.notifyDataSetChanged()

            animeAdapter.onItemClick = {
                val dialog = AnimeDetailsBottomSheet(it)
                dialog.setCancelable(true)
                dialog.show(parentFragmentManager, "AnimeBottomSheetDialog")
            }

            animeAdapter.onItemLongClick = {

                val animeData = Animes(
                    name = it.title,
                    episodes = it.episodes,
                    status = it.status,
                    season = it.season,
                    url = it.images.jpg.imageUrl,
                    noOfEpisodes = 0
                )
                animeData.id = System.currentTimeMillis()

                val longPressDialogBinding =
                    layoutInflater.inflate(R.layout.add_to_lib_dialog, null)
                val longPressDialog = Dialog(requireContext())

                longPressDialog.setContentView(longPressDialogBinding)
                longPressDialog.setCancelable(true)
                longPressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                longPressDialog.show()

                val btnAdd = longPressDialogBinding.findViewById<MaterialCardView>(R.id.btnAdd)
                btnAdd.setOnClickListener {
                    animesRoomDBViewModel.insertAnimes(animeData)
                    Snacker(it, "Anime added to your WatchList").success()
                    longPressDialog.dismiss()
                }
            }

        })

        animeViewModel.searchAnimeResponse.observe(viewLifecycleOwner, Observer {
            val sData: MutableList<Data> = it.body()!!.data as MutableList<Data>

            binding.shimmerEffect.visibility = View.GONE

            animeAdapter = AnimeTopSearchAdapter(sData)

            var adapter = animeAdapter

            adapter.notifyDataSetChanged()
            binding.searchRecyclerView.setHasFixedSize(true)
            binding.searchRecyclerView.adapter = adapter
            binding.searchRecyclerView.layoutManager = GridLayoutManager(context, 2)
            adapter.notifyDataSetChanged()

            animeAdapter.onItemClick = {
                val dialog = AnimeDetailsBottomSheet(it)
                dialog.setCancelable(true)
                dialog.show(parentFragmentManager, "AnimeBottomSheetDialog")
            }

            animeAdapter.onItemLongClick = {

                val animeData = Animes(
                    name = it.title,
                    episodes = it.episodes,
                    status = it.status,
                    season = it.season,
                    url = it.images.jpg.imageUrl,
                    noOfEpisodes = 0
                )
                animeData.id = System.currentTimeMillis()

                val longPressDialogBinding =
                    layoutInflater.inflate(R.layout.add_to_lib_dialog, null)
                val longPressDialog = Dialog(requireContext())

                longPressDialog.setContentView(longPressDialogBinding)
                longPressDialog.setCancelable(true)
                longPressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                longPressDialog.show()

                val btnAdd = longPressDialogBinding.findViewById<MaterialCardView>(R.id.btnAdd)
                btnAdd.setOnClickListener {
                    animesRoomDBViewModel.insertAnimes(animeData)
                    Toast.makeText(context, "Added Anime to your library", Toast.LENGTH_SHORT)
                        .show()
                    longPressDialog.dismiss()
                }
            }

        })

    }


    private fun callTopAnime() {
        animeViewModel.getTopAnime()
    }

    fun Fragment.hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}