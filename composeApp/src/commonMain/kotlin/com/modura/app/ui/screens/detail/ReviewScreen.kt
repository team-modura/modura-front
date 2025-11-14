package com.modura.app.ui.screens.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.currentOrThrow
import com.modura.app.LocalRootNavigator
import com.modura.app.domain.model.request.detail.ContentReviewRequestModel
import com.modura.app.domain.model.request.detail.PlaceReviewRequestModel
import com.modura.app.domain.model.response.detail.ContentReviewResponseModel
import com.modura.app.domain.model.response.detail.ContentReviewsResponseModel
import com.modura.app.ui.components.*
import com.modura.app.ui.theme.*
import com.modura.app.util.platform.rememberImagePicker
import modura.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource


data class ReviewScreen(val id: Int, val reviewType: String, val title: String ,val initialRating: Int = 0 ) : Screen {
    override val key: String = "ReviewScreen_${reviewType}_$id"

    @Composable
    override fun Content() {
        val rootNavigator = LocalRootNavigator.current
        val navigator = LocalRootNavigator.currentOrThrow
        val screenModel: DetailScreenModel = getScreenModel()
        val reviewUiState by screenModel.reviewUiState
        var userRating by remember(initialRating) { mutableStateOf(initialRating) }
        val selectedPhotos = remember { mutableStateListOf<String>() }
        var reviewText by remember { mutableStateOf("") }


        if (reviewUiState.inProgress) {
            CircularProgressIndicator()
        }

        LaunchedEffect(reviewUiState.success) {
            if (reviewUiState.success) {
                println()
            }
        }

        val imagePicker = rememberImagePicker { uris ->
            if (selectedPhotos.size + uris.size <= 5) {
                selectedPhotos.addAll(uris)
            } else {
                println("사진은 최대 5개까지 선택할 수 있습니다.")
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth().padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(modifier = Modifier.padding(20.dp)) {
                Icon(
                    painter = painterResource(Res.drawable.ic_delete),
                    contentDescription = "뒤로가기",
                    modifier = Modifier.size(24.dp)
                        .clickable {
                            rootNavigator?.pop()
                        }
                )
                Spacer(Modifier.weight(1f))
                Text("완료", style= MaterialTheme.typography.bodyLarge, modifier = Modifier.clickable {
                    if(reviewType=="장소"){
                        screenModel.placeReviewRegister(
                            placeId = id,
                            rating = userRating,
                            comment = reviewText,
                            photoUris = selectedPhotos
                        )
                    }else if(reviewType=="콘텐츠") {
                        val request = ContentReviewRequestModel(
                            rating = userRating,
                            comment = reviewText
                        )
                        screenModel.contentReviewRegister(id,request)
                    }
                })
            }
            Text(title, style = MaterialTheme.typography.bodyLarge)
            ReviewStarInput(
                rating = userRating,
                onRatingChange = { newRating ->
                    userRating = newRating
                }
            )

            TextField(
                value = reviewText,
                onValueChange = { reviewText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 200.dp)
                    .padding(horizontal = 20.dp),
                placeholder = {
                    Text(
                        text = "리뷰를 입력해주세요.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Gray500
                    )
                },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Gray300,
                    unfocusedContainerColor = Gray300,
                    disabledContainerColor = Gray300,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                textStyle = MaterialTheme.typography.bodyMedium
            )
            if (reviewType == "장소") {
                LazyRow(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        PhotoAddButton(
                            count = selectedPhotos.size,
                            limit = 5,
                            onClick = {
                                imagePicker.pickImages()
                            }
                        )
                    }
                    items(selectedPhotos) { photoUri ->
                        PhotoItem(
                            photoUri = photoUri,
                            onDelete = {
                                selectedPhotos.remove(photoUri)
                            }
                        )
                    }
                }
            }
        }
    }
}

fun getMimeType(fileUri: String): String {
    val extension = fileUri.substringAfterLast('.', "")
    return when (extension.lowercase()) {
        "jpg", "jpeg" -> "image/jpeg"
        "png" -> "image/png"
        else -> "image/jpeg"
    }
}


 fun extensionFromMimeType(mimeType: String): String {
    return when (mimeType) {
        "image/jpeg" -> "jpg"
        "image/png" -> "png"
        else -> "jpg"
    }
}