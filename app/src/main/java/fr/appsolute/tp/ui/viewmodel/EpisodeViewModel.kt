package fr.appsolute.tp.ui.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fr.appsolute.tp.RickAndMortyApplication
import fr.appsolute.tp.data.repository.EpisodeRepository


class EpisodeViewModel(
    application: RickAndMortyApplication
) : AndroidViewModel(application) {

    val episodeRepository = EpisodeRepository.newInstance(application)

    class Factory (
        private val application: RickAndMortyApplication
    ): ViewModelProvider.AndroidViewModelFactory(application){
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return EpisodeViewModel(application) as T
        }
    }
}
