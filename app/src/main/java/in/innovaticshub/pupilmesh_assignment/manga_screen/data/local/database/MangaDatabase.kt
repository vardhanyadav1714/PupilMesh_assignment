package `in`.innovaticshub.pupilmesh_assignment.manga_screen.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import `in`.innovaticshub.pupilmesh_assignment.utils.Converters
import `in`.innovaticshub.pupilmesh_assignment.manga_screen.data.local.dao.MangaDataDao
import `in`.innovaticshub.pupilmesh_assignment.manga_screen.data.local.entity.MangaDataTable

@Database(entities = [MangaDataTable::class], version = 1)
@TypeConverters(Converters::class)
abstract class MangaDatabase : RoomDatabase() {
    abstract fun mangaDao(): MangaDataDao
}