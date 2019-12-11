package fr.appsolute.tp.data.repository

import fr.appsolute.tp.data.database.DatabaseManager
import fr.appsolute.tp.data.database.EpisodeDao
import fr.appsolute.tp.data.model.Episode
import fr.appsolute.tp.data.networking.HttpClientManager
import fr.appsolute.tp.data.networking.api.EpisodeApi
import fr.appsolute.tp.data.networking.createApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private class EpisodeRepositoryImpl(
    private val api: EpisodeApi,
    private val dao: EpisodeDao
) : EpisodeRepository {

    override suspend fun getAllEpisode(): List<Episode>? {

        return withContext(Dispatchers.IO) {

            try {
                val episodeList = mutableListOf<Episode>()
                val body = api.getAllEpisode(0).body()
                val episodeCount = body?.information?.count ?: 0
                val pagesCount = body?.information?.pages ?: 0
                if (!getDatabaseCount(episodeCount)) {
                    body?.results?.let { episodeList.addAll(it) }
                    for (pages in 1..pagesCount) {
                        api.getAllEpisode(pages).body()?.results?.let { episodeList.addAll(it) }
                    }
                } else {
                    episodeList.addAll(dao.selectAll())
                }

                dao.insertAll(episodeList)
                episodeList

            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
    override suspend fun getEpisodeById(id: Int): Episode? {
        return withContext(Dispatchers.IO){
            try {
                dao.selectById(id)
            }catch (e:Exception){
                e.printStackTrace()
                null
            }
        }
    }
    override suspend fun getFilteredEpisode(idList: List<Int>): List<Episode> {
        return withContext(Dispatchers.IO) {
            val episodeList = mutableListOf<Episode>()
            idList.map {
                val episode = getEpisodeById(it) ?: throw IllegalStateException("No episode found")
                episodeList.add(episode)
            }
            episodeList
        }
    }
    private fun getDatabaseCount(apiCount: Int): Boolean {
        return apiCount == dao.getCount()
    }
}
/**
 * Repository of model [Episode]
 */
interface EpisodeRepository {
    suspend fun getAllEpisode(): List<Episode>?
    suspend fun getEpisodeById(id:Int): Episode?
    suspend fun getFilteredEpisode(idList: List<Int>) : List<Episode>
    companion object {
        fun newInstance(): EpisodeRepository =
            EpisodeRepositoryImpl(
                HttpClientManager.instance.createApi(),
                DatabaseManager.getInstance().database.episodeDao
            )
    }
}