package fr.appsolute.tp.data.networking.api

import fr.appsolute.tp.data.model.Character
import fr.appsolute.tp.data.model.Episode
import fr.appsolute.tp.data.model.PaginatedResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EpisodeApi {
    /**
     * Suspended function (must be call in a coroutine) to get paginated result to fetch characters
     *
     * @param page : Int for the current page to fetch
     *
     * @return A [Response] of [PaginatedResult] of [Episode]
     */
    @GET(GET_ALL_EPISODE_PATH)
    suspend fun getAllEpisode(
        @Query("page") page: Int
    ): Response<PaginatedResult<Episode>>

    @GET(GET_ALL_EPISODE_DETAILS_PATH)
    suspend fun getEpisodeById(
        @Path("episode_id") episode_id: Int
    ): Response<PaginatedResult<Episode>>

    companion object {
        const val GET_ALL_EPISODE_PATH = "episode/"
        const val GET_ALL_EPISODE_DETAILS_PATH = "episode/{episode_id}"
    }
}