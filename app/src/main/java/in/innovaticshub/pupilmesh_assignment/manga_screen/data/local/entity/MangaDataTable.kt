package `in`.innovaticshub.pupilmesh_assignment.manga_screen.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "manga_data_entity")
data class MangaDataTable(
    @PrimaryKey(autoGenerate = false)
     val id: String,

    @ColumnInfo("authors")
    val authors: List<String>,

    @ColumnInfo("create_at")
    val create_at: Long,

    @ColumnInfo("genres")
    val genres: List<String>,



    @ColumnInfo("nsfw")
    val nsfw: Boolean,

    @ColumnInfo("status")
    val status: String,

    @ColumnInfo("sub_title")
    val sub_title: String,

    @ColumnInfo("summary")
    val summary: String,

    @ColumnInfo("thumb")
    val thumb: String,

    @ColumnInfo("title")
    val title: String,

    @ColumnInfo("total_chapter")
    val total_chapter: Int,

    @ColumnInfo("type")
    val type: String,

    @ColumnInfo("update_at")
    val update_at: Long
)