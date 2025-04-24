package `in`.innovaticshub.pupilmesh_assignment.manga_screen.utils

import `in`.innovaticshub.pupilmesh_assignment.manga_screen.data.local.entity.MangaDataTable
import `in`.innovaticshub.pupilmesh_assignment.manga_screen.data.remote.model.Data

fun Data.toMangaDataTable(): MangaDataTable {
    return MangaDataTable(
        id = this.id,
        authors = this.authors,
        create_at = this.create_at,
        genres = this.genres,
        nsfw = this.nsfw,
        status = this.status,
        sub_title = this.sub_title,
        summary = this.summary,
        thumb = this.thumb,
        title = this.title,
        total_chapter = this.total_chapter,
        type = this.type,
        update_at = this.update_at
    )
}

