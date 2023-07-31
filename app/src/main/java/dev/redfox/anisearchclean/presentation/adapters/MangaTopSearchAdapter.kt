package dev.redfox.anisearchclean.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.redfox.anisearchclean.R
import dev.redfox.anisearchclean.data.dataclasses.topMangaModel.Data


class MangaTopSearchAdapter(
    val mangaList: MutableList<Data>
) : RecyclerView.Adapter<MangaTopSearchAdapter.MangaViewHolder>() {

    var onItemClick : ((Data) -> Unit)? = null
    var onItemLongClick : ((Data) -> Unit)? = null

    class MangaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val animePic: ImageView = itemView.findViewById(R.id.animePic)
        val animeName: TextView = itemView.findViewById(R.id.animeName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.anime_item_layout, parent, false)
        return MangaViewHolder(itemView)
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onBindViewHolder(holder: MangaViewHolder, position: Int) {
        val manga = mangaList[position]

        holder.animeName.text = manga.title
        Picasso.get().load(manga.images.jpg.image_url).into(holder.animePic)

        holder.itemView.setOnClickListener(){
            onItemClick?.invoke(manga)
        }

        holder.itemView.setOnLongClickListener {
            onItemLongClick?.invoke(manga)
            true
        }
    }

    override fun getItemCount(): Int {
        return mangaList.size
    }
}