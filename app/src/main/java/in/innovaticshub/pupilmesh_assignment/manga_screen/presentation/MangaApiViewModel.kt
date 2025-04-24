package `in`.innovaticshub.pupilmesh_assignment.manga_screen.presentation

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.innovaticshub.pupilmesh_assignment.manga_screen.domain.MangaPagingSource
import `in`.innovaticshub.pupilmesh_assignment.manga_screen.data.local.entity.MangaDataTable
import `in`.innovaticshub.pupilmesh_assignment.manga_screen.data.remote.repository.MangaApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangaViewModel @Inject constructor(
    private val repository: MangaApiRepository,
    private val connectivityManager: ConnectivityManager
) : ViewModel() {

    private val _isOnline = MutableStateFlow(false)
    val isOnline = _isOnline.asStateFlow()

     private val refreshTrigger = MutableStateFlow(Unit)

     val mangaPagingFlow: Flow<PagingData<MangaDataTable>> =
        refreshTrigger
            .flatMapLatest {
                Pager(
                    config = PagingConfig(
                        pageSize = 20,
                        enablePlaceholders = false,
                        initialLoadSize = 20
                    ),
                    pagingSourceFactory = {
                        MangaPagingSource(

                            repository, _isOnline.value,

                        )
                    }
                ).flow
            }
            .cachedIn(viewModelScope)

    init {
        checkNetworkStatus()
    }

    fun refreshData() {
        viewModelScope.launch {
            if (_isOnline.value) {
                repository.clearCache()
            }
        }
    }



    private fun checkNetworkStatus() {
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                _isOnline.value = true
            }

            override fun onLost(network: Network) {
                _isOnline.value = false
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            val request = NetworkRequest.Builder().build()
            connectivityManager.registerNetworkCallback(request, networkCallback)
        }

        _isOnline.value = connectivityManager.activeNetwork != null
    }
}

