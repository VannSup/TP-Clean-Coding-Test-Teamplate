package fr.appsolute.tp.ui.widget.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.appsolute.tp.R
import fr.appsolute.tp.data.model.Episode
import kotlinx.android.synthetic.main.holder_episode.view.*


class EpisodeViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(model: Episode) {
        itemView.apply {
            holder_episode_name.text = model.name
            holder_episode.text = model.episode
            holder_episode_date.text = model.air_date
        }
    }

    companion object {
        /**
         * Create a new Instance of [EpisodeViewHolder]
         */
        fun create(parent: ViewGroup): EpisodeViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.holder_episode,
                parent,
                false
            )
            return EpisodeViewHolder(view)
        }
    }
}