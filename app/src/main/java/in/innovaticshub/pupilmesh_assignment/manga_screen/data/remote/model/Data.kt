package `in`.innovaticshub.pupilmesh_assignment.manga_screen.data.remote.model

data class Data(
    val authors: List<String>,
    val create_at: Long,
    val genres: List<String>,
    val id: String,
    val nsfw: Boolean,
    val status: String,
    val sub_title: String,
    val summary: String,
    val thumb: String,
    val title: String,
    val total_chapter: Int,
    val type: String,
    val update_at: Long
)