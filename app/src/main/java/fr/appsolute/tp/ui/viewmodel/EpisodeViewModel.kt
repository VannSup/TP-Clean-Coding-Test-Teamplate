package fr.appsolute.tp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import fr.appsolute.tp.data.model.Episode
import fr.appsolute.tp.data.repository.EpisodeRepository
import kotlinx.coroutines.launch


class EpisodeViewModel(
    private val episodeRepository: EpisodeRepository
) : ViewModel() {

    fun getEpisodes(onSuccess: OnSuccess<List<Episode>>){
        viewModelScope.launch {
            episodeRepository.getAllEpisode()?.run(onSuccess)
        }
    }

    fun getEpisodeFiltered(idList: List<Int>, block : OnSuccess<List<Episode>>) {
        viewModelScope.launch {
            episodeRepository.getFilteredEpisode(idList).run(block)
        }
    }

    companion object Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return EpisodeViewModel(episodeRepository = EpisodeRepository.newInstance()) as T
        }
    }
}
