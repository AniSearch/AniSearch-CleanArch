package dev.redfox.anisearchclean.presentation.adapters

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso
import dev.redfox.anisearchclean.R
import dev.redfox.anisearchclean.data.dataclasses.Animes
import dev.redfox.anisearchclean.data.utils.Snacker
import dev.redfox.anisearchclean.data.utils.SwipeGesture
import dev.redfox.anisearchclean.databinding.WatchListItemBinding
import dev.redfox.anisearchclean.presentation.viewmodels.AnimesRoomDBViewModel

class AnimeWatchListAdapter(
    val animesDBViewModel: AnimesRoomDBViewModel,
    val animesWatchList: MutableList<Animes>,
    val context: Context
) : RecyclerView.Adapter<AnimeWatchListAdapter.AnimeDBViewHolder>() {

    class AnimeDBViewHolder(val binding: WatchListItemBinding, val context: Context): RecyclerView.ViewHolder(binding.root) {
        val animeDBPic: ImageView = itemView.findViewById(R.id.ivAnimeWatchPic)
        val animeDBName: TextView = itemView.findViewById(R.id.tvWatchAnimeName)
        val animeDBEpisodes: TextView = itemView.findViewById(R.id.tvWatchEpisodes)
        val animeDBStatus: TextView = itemView.findViewById(R.id.tvWatchStatus)
        val animeDBNoOfEpisodes: TextView = itemView.findViewById(R.id.tvNoOfEpidoes)
        val btnSubEpisodes: ImageView = itemView.findViewById(R.id.btnSubEpisodes)
        val btnAddEpisodes: ImageView = itemView.findViewById(R.id.btnAddEpisodes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeDBViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = WatchListItemBinding.inflate(layoutInflater, parent, false)
        return AnimeDBViewHolder(binding, parent.context)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AnimeDBViewHolder, position: Int) {

        val animeItem = animesWatchList[position]


        holder.animeDBName.text = animeItem.name
        holder.animeDBEpisodes.text = "Episodes: " + animeItem.episodes.toString()
        holder.animeDBStatus.text = "Status: " + animeItem.status
        holder.animeDBNoOfEpisodes.text = animeItem.noOfEpisodes.toString()
        holder.binding.tvWatchSeason.text = "Season: " + animeItem.season
        Picasso.get().load(animeItem.url).into(holder.animeDBPic)

        holder.btnAddEpisodes.setOnClickListener {
            if(animeItem.noOfEpisodes >= animeItem.episodes){
                Snacker(it,"Maximum Episodes reached").error()
            } else {
                animeItem.noOfEpisodes += 1
                animesDBViewModel.updateAnimes(animeItem)
                holder.animeDBNoOfEpisodes.text = animeItem.noOfEpisodes.toString()
            }

        }

        holder.btnSubEpisodes.setOnClickListener {
            if(holder.animeDBNoOfEpisodes.text.toString() == "0"){
                Snacker(it,"Episodes cannot be in negative").error()
            } else {
                animeItem.noOfEpisodes -= 1
                animesDBViewModel.updateAnimes(animeItem)
                holder.animeDBNoOfEpisodes.text = animeItem.noOfEpisodes.toString()
            }

        }

    }



    override fun getItemCount(): Int {
        return animesWatchList.size
    }
}