package fr.appsolute.tp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import fr.appsolute.tp.data.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterViewModel private constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    /**
     *  Return the paginated list of character from the API
     */
    val charactersPagedList = repository.getPaginatedList(viewModelScope)

    /*fun getCharacterDetails(id: Int, onSuccess: (LiveData<Character>)-> Unit){
        viewModelScope.launch{
            val detailsOfCharacter = withContext(Dispatchers.IO){
                //personage = aller chercher mon user detail
                return@withContext getCharacterDetails(id,LiveData<Character>)
            }
            onSuccess(detailsOfCharacter)
        }
    }*/

    companion object Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CharacterViewModel(CharacterRepository.instance) as T
        }
    }
}