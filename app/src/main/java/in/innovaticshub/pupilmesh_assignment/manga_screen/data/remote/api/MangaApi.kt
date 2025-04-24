package `in`.innovaticshub.pupilmesh_assignment.manga_screen.data.remote.api

import `in`.innovaticshub.pupilmesh_assignment.manga_screen.data.remote.model.MangaResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MangaApi{


        @GET("manga/fetch")
        suspend fun getMangaData(
            @Query("page") page: Int,
            @Query("limit") limit: Int = 20,
            @Query("genres") genres: String? = null,
            @Query("nsfw") nsfw: Boolean? = null,
            @Query("type") type: String? = null,
            @Header("X-RapidAPI-Key") rapidApiKey: String,
            @Header("X-RapidAPI-Host") rapidApiHost: String
        ): MangaResponse

}