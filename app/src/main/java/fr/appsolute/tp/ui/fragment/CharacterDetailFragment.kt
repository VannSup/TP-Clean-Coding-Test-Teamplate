package fr.appsolute.tp.ui.fragment

import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.transition.Fade
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import fr.appsolute.tp.R
import fr.appsolute.tp.ui.activity.MainActivity
import fr.appsolute.tp.ui.viewmodel.CharacterViewModel
import kotlinx.android.synthetic.main.fragment_character_detail.view.*

class CharacterDetailFragment : Fragment() {

    private lateinit var characterViewModel: CharacterViewModel
    private var characterId: Int? = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.run {
            characterViewModel = ViewModelProvider(this, CharacterViewModel).get()
        } ?: throw IllegalStateException("Invalid Activity")
        characterId =
            arguments?.getInt(ARG_CHARACTER_ID_KEY) ?:throw java.lang.IllegalStateException("No ID found")
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
            characterViewModel.getCharacterById(it){
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
            }
        }
    }

    companion object{
        const val ARG_CHARACTER_ID_KEY = "arg_character_id_key"
    }
}