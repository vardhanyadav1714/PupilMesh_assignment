package `in`.innovaticshub.pupilmesh_assignment.manga_screen.data.local.repository

import `in`.innovaticshub.pupilmesh_assignment.manga_screen.data.local.dao.MangaDataDao
import `in`.innovaticshub.pupilmesh_assignment.manga_screen.data.local.entity.MangaDataTable
import javax.inject.Inject

class MangaDatabaseRepository @Inject constructor(private val mangaDao: MangaDataDao){

    suspend fun insertMangaData(list:List<MangaDataTable>){
        mangaDao.insertMangaData(list)
    }

    suspend fun getMangaData():List<MangaDataTable>{
        return mangaDao.getMangaDataEntity()
    }
}