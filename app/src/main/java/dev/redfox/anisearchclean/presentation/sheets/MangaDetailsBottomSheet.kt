package dev.redfox.anisearchclean.presentation.sheets

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import dev.redfox.anisearchclean.data.dataclasses.topMangaModel.Data
import dev.redfox.anisearchclean.databinding.AnimeBottomSheetBinding


@AndroidEntryPoint
class MangaDetailsBottomSheet(val anime: Data): BottomSheetDialogFragment(){


    lateinit var binding: AnimeBottomSheetBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val animeInflater = LayoutInflater.from(requireContext())
        binding = AnimeBottomSheetBinding.inflate(animeInflater)

        binding.apply{
            Picasso.get().load(anime.images.jpg.image_url).into(ivAnimePic)
            tvAnimeName.text = anime.title
            tvRating.text = "Rating: " + anime.score.toString()
            tvStatus.text = "Status: " + anime.status
            tvEpisodes.text = "Chapters: " + anime.chapters
            tvSynopsis.text = anime.synopsis
            tvSeason.text = "Type: " + anime.type

            tvKnowMore.setOnClickListener {
                openCustomTab(activity, Uri.parse(anime.url))
            }

            tvWatchTrailer.setOnClickListener{
                Toast.makeText(context, "No Trailer for Mangas", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun openCustomTab(activity: FragmentActivity?, url: Uri){
        val builder = CustomTabsIntent.Builder()
        builder.setShowTitle(true)
        builder.build().launchUrl(requireActivity(),url)
    }

}