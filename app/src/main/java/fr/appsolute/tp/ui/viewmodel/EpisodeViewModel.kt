package fr.appsolute.tp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import fr.appsolute.tp.data.model.Episode
import fr.appsolute.tp.data.repository.EpisodeRepository
import kotlinx.coroutines.launch

class EpisodeViewModel private constructor(
    private val repository: EpisodeRepository
) : ViewModel() {

    /**
     *  Return the paginated list of character from the API
     */
    val episodesPagedList = repository.getPaginatedList(viewModelScope)

    fun getEpisodeById(id: Int, onSuccess: OnSuccess<Episode>) {
        viewModelScope.launch {
            repository.getEpisodeDetail(id)?.run(onSuccess)
        }
    }

    companion object Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return EpisodeViewModel(EpisodeRepository.instance) as T
        }
    }
}