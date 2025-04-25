package `in`.innovaticshub.pupilmesh_assignment.manga_screen.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import `in`.innovaticshub.pupilmesh_assignment.manga_screen.data.local.entity.MangaDataTable
import `in`.innovaticshub.pupilmesh_assignment.manga_screen.data.remote.repository.MangaApiRepository
import `in`.innovaticshub.pupilmesh_assignment.manga_screen.utils.toMangaDataTable

class MangaPagingSource(
    private val repository: MangaApiRepository,
    private val isOnline: Boolean
) : PagingSource<Int, MangaDataTable>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MangaDataTable> {
        return try {
            val page = params.key ?: 1

            if (isOnline) {
                val response = repository.fetchMangaData(page)

                 if (page == 1) {
                    repository.clearCache()
                }

                 repository.cacheMangaData(response.data)

                LoadResult.Page(
                    data = response.data.map { it.toMangaDataTable() },
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (response.data.isEmpty()) null else page + 1
                )
            } else {
                val cachedData = repository.getCachedMangaData()
                val pageSize = params.loadSize
                val pagedData = cachedData
                    .drop((page - 1) * pageSize)
                    .take(pageSize)

                LoadResult.Page(
                    data = pagedData,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (pagedData.size < pageSize) null else page + 1
                )
            }
        } catch (e: Exception) {
            try {
                val cachedData = repository.getCachedMangaData()
                LoadResult.Page(
                    data = cachedData,
                    prevKey = null,
                    nextKey = null
                )
            } catch (cacheEx: Exception) {
                LoadResult.Error(e)
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MangaDataTable>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
