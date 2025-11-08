package com.modura.app.ui.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.modura.app.LocalRootNavigator
import com.modura.app.ui.components.*
import com.modura.app.ui.screens.camera.SceneCameraScreen
import com.modura.app.ui.theme.*
import modura.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


data class ReviewScreen(val id: Int) : Screen {
    override val key: String = "ReviewScreen_$id"

    @Composable
    override fun Content() {
        val rootNavigator = LocalRootNavigator.current
        val title = "작품명"
        var userRating by remember { mutableStateOf(0) }
        val selectedPhotos = remember { mutableStateListOf<String>() }

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
                    //완료 처리 후 이전 화면으로 돌아감
                    rootNavigator?.pop()
                })
            }
            Text(title, style = MaterialTheme.typography.bodyLarge)
            ReviewStarInput(
                rating = userRating,
                onRatingChange = { newRating ->
                    userRating = newRating
                }
            )
            var reviewText by remember { mutableStateOf("") }

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
            LazyRow(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp) // 아이템 간 간격
            ) {
                // 3. 사진 추가 버튼
                item {
                    PhotoAddButton(
                        count = selectedPhotos.size,
                        limit = 5,
                        onClick = {
                            // TODO: 갤러리 또는 카메라 실행 로직
                            // 임시로 예시 이미지 추가
                            if (selectedPhotos.size < 5) {
                                selectedPhotos.add("Res.drawable.img_stillcut_example") // 실제로는 URI나 Bitmap 추가
                            }
                        }
                    )
                }

                // 4. 추가된 사진 목록
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
