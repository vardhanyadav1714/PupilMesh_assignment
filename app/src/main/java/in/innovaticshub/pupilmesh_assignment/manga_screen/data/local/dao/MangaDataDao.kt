package `in`.innovaticshub.pupilmesh_assignment.manga_screen.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import `in`.innovaticshub.pupilmesh_assignment.manga_screen.data.local.entity.MangaDataTable

@Dao
interface MangaDataDao{

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertMangaData(data:List<MangaDataTable>)

    @Query("SELECT * FROM manga_data_entity")
    suspend fun getMangaDataEntity():List<MangaDataTable>

    @Query("DELETE FROM manga_data_entity")
    suspend fun clearAll()
}