package fr.appsolute.tp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import fr.appsolute.tp.R
import fr.appsolute.tp.data.model.Character
import fr.appsolute.tp.data.model.Episode
import fr.appsolute.tp.ui.activity.MainActivity
import fr.appsolute.tp.ui.adapter.EpisodeAdapter
import fr.appsolute.tp.ui.viewmodel.CharacterViewModel
import fr.appsolute.tp.ui.viewmodel.EpisodeViewModel
import kotlinx.android.synthetic.main.fragment_character_detail.*
import kotlinx.android.synthetic.main.fragment_character_detail.view.*

class CharacterDetailFragment : Fragment() {

    private lateinit var characterViewModel: CharacterViewModel
    private lateinit var episodeViewModel: EpisodeViewModel
    private lateinit var episodeAdapter: EpisodeAdapter
    private var characterId: Int? = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.run {
            characterViewModel = ViewModelProvider(this, CharacterViewModel).get()
            episodeViewModel = ViewModelProvider(this,EpisodeViewModel).get()
        } ?: throw IllegalStateException("Invalid Activity")
        characterId =
            arguments?.getInt(ARG_CHARACTER_ID_KEY)
                ?: throw java.lang.IllegalStateException("No ID found")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_character_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterId?.let {
            characterViewModel.getCharacterById(it) {
                (activity as? MainActivity)?.supportActionBar?.apply {
                    this.title = it.name
                }
                view.apply {
                    this.character_name.text = it.name
                    this.character_species.text = it.species
                    this.character_gender.text = it.gender

                    Glide.with(this)
                        .load(it.image)
                        .into(this.character_image)
                }
                val episodeIdList = getEpisodeList(it)
                episodeAdapter = EpisodeAdapter()
                episodeViewModel.getEpisodeFiltered(episodeIdList) {
                    episodeAdapter.submitList(it)
                    episode_recyclerView.apply {
                        adapter = episodeAdapter
                        layoutManager = LinearLayoutManager(requireContext())
                    }
                }
            }
        }

    }

    private fun getEpisodeList(character: Character): List<Int> {
        val episodeIdList = mutableListOf<Int>()
        character.episode.map {
            val array = it.split("/")
            episodeIdList.add(array.last().toInt())
        }
        return episodeIdList
    }

    companion object {
        const val ARG_CHARACTER_ID_KEY = "arg_character_id_key"
    }
}