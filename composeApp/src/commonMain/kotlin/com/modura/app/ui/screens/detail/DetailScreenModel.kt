package com.modura.app.ui.screens.detail

import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.modura.app.data.dto.response.detail.ContentDetailResponseDto
import com.modura.app.di.NetworkQualifiers
import com.modura.app.domain.model.request.detail.ContentReviewRequestModel
import com.modura.app.domain.model.request.detail.PlaceReviewRequestModel
import com.modura.app.domain.model.request.detail.UploadImageRequestModel
import com.modura.app.domain.model.response.detail.ContentDetailResponseModel
import com.modura.app.domain.model.response.detail.ContentReviewResponseModel
import com.modura.app.domain.model.response.detail.PlaceDetailResponseModel
import com.modura.app.domain.model.response.detail.UploadImageResponseModel
import com.modura.app.domain.model.response.youtube.YoutubeModel
import com.modura.app.domain.repository.DetailRepository
import com.modura.app.ui.components.ReviewList
import com.modura.app.util.platform.readFileAsBytes
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
import kotlinx.io.IOException
import kotlinx.io.files.FileNotFoundException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

data class YoutubeUiState(
    val videos: List<YoutubeModel> = emptyList(),
    val isYoutubeLoading: Boolean = false,
    val youtubeErrorMessage: String? = null
)

data class DetailContentUiState(
    val inProgress: Boolean = false,
    val success: Boolean = false,
    val data: ContentDetailResponseModel? = null,
    val errorMessage: String? = null
)

data class DetailPlaceUiState(
    val inProgress: Boolean = false,
    val success: Boolean = false,
    val data: PlaceDetailResponseModel? = null,
    val errorMessage: String? = null
)

data class ReviewUiState(
    val inProgress: Boolean = false,
    val success: Boolean = false,
    val errorMessage: String? = null
)

class DetailScreenModel(
    private val repository: DetailRepository
) : ScreenModel,KoinComponent {

    val youtubeUiState = mutableStateOf(YoutubeUiState())
    var detailUiState = mutableStateOf(DetailContentUiState())
    var detailPlaceUiState = mutableStateOf(DetailPlaceUiState())
    var reviewUiState = mutableStateOf(ReviewUiState())

    private var _contentReviews = MutableStateFlow<List<ContentReviewResponseModel>>(emptyList())
    val contentReviews = _contentReviews.asStateFlow()

    private val s3HttpClient: HttpClient by inject(named(NetworkQualifiers.NO_AUTH_HTTP_CLIENT))
    fun detailContent(contentId: Int) {
        screenModelScope.launch {
            detailUiState.value = DetailContentUiState(inProgress = true)
            repository.detailContent(contentId).onSuccess {
                detailUiState.value = DetailContentUiState(inProgress = false, data = it)
                getYoutubeVideos("${it.titleKr} 공식 예고편")
            }.onFailure {
                detailUiState.value =
                    DetailContentUiState(inProgress = false, errorMessage = "상세 정보를 불러오는데 실패했습니다.")
                it.printStackTrace()
            }
        }
    }

    fun detailPlace(placeId: Int) {
        screenModelScope.launch {
            detailPlaceUiState.value = DetailPlaceUiState(inProgress = true)
            repository.detailPlace(placeId).onSuccess {
                detailPlaceUiState.value = DetailPlaceUiState(inProgress = false, data = it)
            }.onFailure {
                detailPlaceUiState.value =
                    DetailPlaceUiState(inProgress = false, errorMessage = "상세 정보를 불러오는데 실패했습니다.")
                it.printStackTrace()
            }
        }
    }

    fun getYoutubeVideos(query: String) {
        screenModelScope.launch {
            youtubeUiState.value =
                youtubeUiState.value.copy(isYoutubeLoading = true, youtubeErrorMessage = null)
            repository.getYoutubeVideos(query)
                .onSuccess {
                    youtubeUiState.value =
                        youtubeUiState.value.copy(videos = it, isYoutubeLoading = false)
                }.onFailure {
                    it.printStackTrace()
                    youtubeUiState.value = youtubeUiState.value.copy(
                        isYoutubeLoading = false,
                        youtubeErrorMessage = "영상을 불러오는 중 오류가 발생했습니다."
                    )
                }
        }
    }

    fun contentLike(contentId: Int) {
        screenModelScope.launch {
            repository.contentLike(contentId).onSuccess {
                println(it)
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    fun placeLike(placeId: Int) {
        screenModelScope.launch {
            repository.placeLike(placeId).onSuccess {
                println(it)
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    fun contentLikeCancel(contentId: Int) {
        screenModelScope.launch {
            repository.contentLikeCancel(contentId).onSuccess {
                println(it)
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    fun placeLikeCancel(placeId: Int) {
        screenModelScope.launch {
            repository.placeLikeCancel(placeId).onSuccess {
                println(it)
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    fun contentReviews(contentId: Int){
        screenModelScope.launch {
            repository.contentReviews(contentId).onSuccess {
                _contentReviews.value = it.reviews
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    fun placeReviews(placeId: Int){
        screenModelScope.launch {
            repository.placeReviews(placeId).onSuccess {
                println(it)
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    fun contentReviewRegister(contentId: Int, request: ContentReviewRequestModel) {
        screenModelScope.launch {
            repository.contentReviewRegister(contentId, request).onSuccess {
                println(it)
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    fun placeReviewRegister(placeId: Int, rating: Int, comment: String, photoUris: List<String>) {
        println("${placeId} ${rating} ${comment}")
        screenModelScope.launch {
            reviewUiState.value = ReviewUiState(inProgress = true)
            val finalImageUrls: List<String>

            if (photoUris.isNotEmpty()) {
                println(photoUris)
                val mimeTypes = photoUris.map { getMimeType(it) }
                val fileNames = photoUris.mapIndexed { index, uri ->
                    val originalName = uri.substringAfterLast('/')
                    val mimeType = mimeTypes[index]
                    "$originalName.${extensionFromMimeType(mimeType)}"
                }
                val uploadRequest = UploadImageRequestModel("reviews", fileNames, mimeTypes)
                var presignedUrlResponses: List<UploadImageResponseModel>? = null
                repository.uploadImage(uploadRequest)
                    .onSuccess { responses ->
                        reviewUiState.value = ReviewUiState(success = true)
                        presignedUrlResponses = responses
                    }
                    .onFailure { error ->
                        error.printStackTrace()
                        reviewUiState.value = ReviewUiState(errorMessage = "이미지 업로드에 실패했습니다.")
                        throw error
                    }
                val uploadJobs = presignedUrlResponses!!.mapIndexed { index, response ->
                    async(Dispatchers.IO) {
                        val originalUri = photoUris[index]
                        val mimeType = mimeTypes[index]
                        val fileBytes: ByteArray = readFileAsBytes(originalUri)
                        val httpResponse = s3HttpClient.put(response.presignedUrl) {
                            setBody(fileBytes);header(
                            HttpHeaders.ContentType,
                            mimeType
                        )
                        }
                        if (!httpResponse.status.isSuccess()) {
                            throw IOException("S3 Upload Failed for ${response.key}: ${httpResponse.status}")
                        }
                    }
                }
                uploadJobs.awaitAll()

                finalImageUrls = presignedUrlResponses!!.map {
                    it.presignedUrl.substringBefore("?")
                }
            } else {
                finalImageUrls = emptyList()
            }

            val reviewRequest = PlaceReviewRequestModel(rating, comment, finalImageUrls)
            repository.placeReviewRegister(placeId, reviewRequest).onSuccess {
                println("✅ 리뷰 등록 성공")
            }.onFailure {
                it.printStackTrace()
            }
        }
    }
}

