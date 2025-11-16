package com.modura.app.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.modura.app.data.dto.response.detail.ContentDetailResponseDto
import com.modura.app.data.dto.response.search.SearchPlaceResponseDto
import com.modura.app.di.NetworkQualifiers
import com.modura.app.domain.model.request.detail.ContentReviewRequestModel
import com.modura.app.domain.model.request.detail.PlaceReviewRequestModel
import com.modura.app.domain.model.request.detail.StillcutRequestModel
import com.modura.app.domain.model.request.detail.UploadImageRequestModel
import com.modura.app.domain.model.response.detail.ContentDetailResponseModel
import com.modura.app.domain.model.response.detail.ContentReviewResponseModel
import com.modura.app.domain.model.response.detail.PlaceDetailResponseModel
import com.modura.app.domain.model.response.detail.PlaceReviewResponseModel
import com.modura.app.domain.model.response.detail.StillcutResponseModel
import com.modura.app.domain.model.response.detail.UploadImageResponseModel
import com.modura.app.domain.model.response.search.SearchContentResponseModel
import com.modura.app.domain.model.response.search.SearchPlaceResponseModel
import com.modura.app.domain.model.response.youtube.YoutubeModel
import com.modura.app.domain.repository.DetailRepository
import com.modura.app.domain.repository.ListRepository
import com.modura.app.ui.components.ReviewList
import com.modura.app.ui.screens.detail.extensionFromMimeType
import com.modura.app.ui.screens.detail.getMimeType
import com.modura.app.util.platform.ImageComparator
import com.modura.app.util.platform.readBytesFromFilePath
import com.modura.app.util.platform.readFileAsBytes
import com.modura.app.util.platform.saveBitmapToFile
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.header
import io.ktor.client.request.put
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.io.IOException
import kotlinx.io.files.FileNotFoundException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import kotlin.text.mapIndexed


data class HomeUiState(
    val inProgress: Boolean = false,
    val success: Boolean = false,
    val errorMessage: String? = null
)

class HomeScreenModel(
    private val repository: ListRepository
) : ScreenModel,KoinComponent {

    private val uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiStateFlow = uiState.asStateFlow()

    private val _topPlaces = MutableStateFlow<List<SearchPlaceResponseModel>>(emptyList())
    val topPlaces = _topPlaces.asStateFlow()

    private val _topSeries = MutableStateFlow<List<SearchContentResponseModel>>(emptyList())
    val topSeries = _topSeries.asStateFlow()

    private val _topMovie = MutableStateFlow<List<SearchContentResponseModel>>(emptyList())
    val topMovie = _topMovie.asStateFlow()

    fun topPlaces() {
        screenModelScope.launch {
            uiState.value = HomeUiState(inProgress = true)
            repository.topPlaces().onSuccess { response ->
                _topPlaces.value = response.placeList
                uiState.value = HomeUiState(success = true)
            }.onFailure {
                uiState.value = HomeUiState(errorMessage = it.message)
            }
        }
    }

    fun topSeries(){
        screenModelScope.launch {
            uiState.value = HomeUiState(inProgress = true)
            repository.topSeries().onSuccess { response ->
                _topSeries.value = response.contentList
                uiState.value = HomeUiState(success = true)
            }.onFailure {
                uiState.value = HomeUiState(errorMessage = it.message)
            }
        }
    }

    fun topMovie() {
        screenModelScope.launch {
            uiState.value = HomeUiState(inProgress = true)
            repository.topMovie().onSuccess { response ->
                _topMovie.value = response.contentList
                uiState.value = HomeUiState(success = true)
            }.onFailure {
                uiState.value = HomeUiState(errorMessage = it.message)
            }
        }
    }


}

