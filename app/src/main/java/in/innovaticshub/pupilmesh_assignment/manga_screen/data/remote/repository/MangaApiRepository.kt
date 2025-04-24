package `in`.innovaticshub.pupilmesh_assignment.manga_screen.data.remote.repository

import `in`.innovaticshub.pupilmesh_assignment.manga_screen.data.remote.model.Data
import `in`.innovaticshub.pupilmesh_assignment.manga_screen.data.local.dao.MangaDataDao
import `in`.innovaticshub.pupilmesh_assignment.manga_screen.data.local.entity.MangaDataTable
import `in`.innovaticshub.pupilmesh_assignment.manga_screen.data.remote.model.MangaResponse
import `in`.innovaticshub.pupilmesh_assignment.manga_screen.data.remote.api.MangaApi
import `in`.innovaticshub.pupilmesh_assignment.manga_screen.utils.toMangaDataTable
import javax.inject.Inject

class MangaApiRepository @Inject constructor(
    private val api: MangaApi,
    private val mangaDao: MangaDataDao
) {
    private val apiKey = "eafc924b41mshb78b3a5a3aa12ebp1dd7e1jsnb8c97b539c8c"
    private val apiHost = "mangaverse-api.p.rapidapi.com"

    suspend fun fetchMangaData(page: Int): MangaResponse {
        return api.getMangaData(
            page = page,
            genres = "Harem,Fantasy",
            nsfw = true,
            type = "all",
            rapidApiKey = apiKey,
            rapidApiHost = apiHost
        )
    }

    suspend fun getCachedMangaData(): List<MangaDataTable> {
        return mangaDao.getMangaDataEntity()
    }

    suspend fun cacheMangaData(data: List<Data>) {
        val entities = data.map { it.toMangaDataTable() }
        mangaDao.insertMangaData(entities)
    }

    suspend fun clearCache() {
        mangaDao.clearAll()
    }
}