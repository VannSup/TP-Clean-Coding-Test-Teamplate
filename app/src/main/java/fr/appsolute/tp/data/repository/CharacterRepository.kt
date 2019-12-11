package fr.appsolute.tp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import fr.appsolute.tp.data.model.Character
import fr.appsolute.tp.data.networking.HttpClientManager
import fr.appsolute.tp.data.networking.api.CharacterApi
import fr.appsolute.tp.data.networking.createApi
import fr.appsolute.tp.data.networking.datasource.CharacterDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private class CharacterRepositoryImpl(
    private val api: CharacterApi
) : CharacterRepository {
    /**
     * Config for pagination
     */
    private val paginationConfig = PagedList.Config
        .Builder()
        // If you set true you will have to catch
        // the place holder case in the adapter
        .setEnablePlaceholders(false)
        .setPageSize(20)
        .build()

    override fun getPaginatedList(scope: CoroutineScope): LiveData<PagedList<Character>> {
        return LivePagedListBuilder(
            CharacterDataSource.Factory(api, scope),
            paginationConfig
        ).build()
    }

    override suspend fun getCharacterDetail(id: Int): Character? {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getCharacterById(id)
                check(response.isSuccessful) {
                    "Response is not a sucess : code = ${response.code()}"
                }
                val data = response.body() ?: throw IllegalStateException("Body is null")
                data
            } catch (t: Throwable) {
                Log.d("Erreur", t.message, t)
                null
            }
        }
    }
}

/**
 * Repository of model [Character]
 */
interface CharacterRepository {

    /**
     * Return a LiveData (Observable Design Pattern) of a Paged List of Character
     */
    fun getPaginatedList(scope: CoroutineScope): LiveData<PagedList<Character>>

    suspend fun getCharacterDetail(id: Int): Character?

    companion object {
        /**
         * Singleton for the interface [CharacterRepository]
         */
        val instance: CharacterRepository by lazy {
            // Lazy means "When I need it" so here this block will be launch
            // the first time you need the instance,
            // then, the reference will be stored in the value `instance`
            CharacterRepositoryImpl(HttpClientManager.instance.createApi())
        }
    }

}