package fr.appsolute.tp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import fr.appsolute.tp.data.model.Episode
import fr.appsolute.tp.data.networking.HttpClientManager
import fr.appsolute.tp.data.networking.api.EpisodeApi
import fr.appsolute.tp.data.networking.createApi
import fr.appsolute.tp.data.networking.datasource.EpisodeDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.IllegalStateException

private class EpisodeRepositoryImpl(
    private val api: EpisodeApi
) : EpisodeRepository {
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

    override fun getPaginatedList(scope: CoroutineScope): LiveData<PagedList<Episode>> {
        return LivePagedListBuilder(
            EpisodeDataSource.Factory(api, scope),
            paginationConfig
        ).build()
    }

    override suspend fun getEpisodeDetail(id: Int): Episode?{
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getEpisodeById(id)
                check(response.isSuccessful){
                    "Response is not a sucess : code = ${response.code()}"
                }
                val data = response.body()?: throw IllegalStateException("Body is nukl")
                data
            }catch (t:Throwable){
                Log.d("Erreur",t.message,t)
                null
            }
        }
    }
}

/**
 * Repository of model [Character]
 */
interface EpisodeRepository {

    /**
     * Return a LiveData (Observable Design Pattern) of a Paged List of Character
     */
    fun getPaginatedList(scope: CoroutineScope): LiveData<PagedList<Episode>>

    suspend  fun getEpisodeDetail(id: Int): Episode?

    companion object {
        /**
         * Singleton for the interface [EpisodeRepository]
         */
        val instance: EpisodeRepository by lazy {
            // Lazy means "When I need it" so here this block will be launch
            // the first time you need the instance,
            // then, the reference will be stored in the value `instance`
            EpisodeRepositoryImpl(HttpClientManager.instance.createApi())
        }
    }

}