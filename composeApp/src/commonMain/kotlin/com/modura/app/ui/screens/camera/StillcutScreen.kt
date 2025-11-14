package com.modura.app.ui.screens.camera

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import coil3.ImageLoader
import coil3.compose.LocalPlatformContext
import com.modura.app.LocalRootNavigator
import com.modura.app.data.dev.DummyProvider.stillcut
import com.modura.app.ui.components.SceneScoreDetail
import com.modura.app.ui.components.StillcutItem
import com.modura.app.ui.screens.detail.DetailScreenModel
import com.modura.app.ui.screens.detail.PlaceDetailScreen
import com.modura.app.util.platform.ImageComparator
import com.modura.app.util.platform.rememberCameraManager
import com.modura.app.util.platform.rememberImageBitmapFromUrl
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modura.composeapp.generated.resources.Res
import modura.composeapp.generated.resources.ic_back
import org.jetbrains.compose.resources.painterResource

class StillcutScreen(private val placeId: Int) : Screen {

    @Composable
    override fun Content() {
        val context = LocalPlatformContext.current
        var recomposeTrigger by remember { mutableStateOf(false) }
        val screenModel = getScreenModel<DetailScreenModel>()
        val updatedOnImageCaptured by rememberUpdatedState(screenModel::onImageCaptured)
        var selectedStillcutId by remember { mutableStateOf<Int?>(null) }
        val launchCamera = rememberCameraManager { imageBitmap ->
            if (imageBitmap != null) {
                updatedOnImageCaptured(imageBitmap)
                recomposeTrigger = !recomposeTrigger
            }
        }
        val stillcutList by screenModel.stillcut.collectAsState()
        val capturedImage = screenModel.capturedImage
        val originalBitmap = screenModel.originalBitmap
        val isLoading = screenModel.isLoading
        val totalScore = screenModel.totalScore
        val structureScore = screenModel.structureScore
        val clarityScore = screenModel.clarityScore
        val toneScore = screenModel.toneScore
        val paletteScore = screenModel.paletteScore
        val rootNavigator = LocalRootNavigator.current
         DisposableEffect(Unit) {
            screenModel.getStillcut(placeId)
            onDispose {
                screenModel.clearStillcutState()
            }
        }

        if (capturedImage != null && recomposeTrigger != null)  {
            // [상태 2] 촬영 후 결과 화면
            Column(
                modifier = Modifier.fillMaxSize().padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(60.dp))
                Text("유사도", style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.height(12.dp))
                if (isLoading) {
                    Text("계산중입니다", style = MaterialTheme.typography.headlineLarge)
                } else {
                    Text("${totalScore?.toInt() ?: 0}%", style = MaterialTheme.typography.headlineLarge)
                }
                Spacer(modifier = Modifier.height(80.dp))

                Row(
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    originalBitmap?.let {
                        Image(
                            bitmap = it,
                            contentDescription = "원본 사진",
                            modifier = Modifier.weight(1f).clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Image(
                        bitmap = capturedImage,
                        contentDescription = "촬영한 사진",
                        modifier = Modifier.weight(1f).clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.height(80.dp))

                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(50.dp)) {
                        SceneScoreDetail(label = "구도", score = structureScore)
                        SceneScoreDetail(label = "선명도", score = clarityScore)
                        SceneScoreDetail(label = "색감", score = toneScore)
                        SceneScoreDetail(label = "색 구성", score = paletteScore)
                    }
                }

                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    // ✨ '다시 찍기' 클릭 시 ScreenModel의 초기화 함수 호출
                    TextButton(onClick = { screenModel.clearStillcutState() }) {
                        Text("다시 찍기", fontSize = 16.sp)
                    }
                    TextButton(onClick = {
                        selectedStillcutId?.let { id ->
                        screenModel.postStillcut(context, placeId, id)
                        }
                        //rootNavigator?.push(PlaceDetailScreen(id = placeId))
                    }) {
                        Text("저장하기", fontSize = 16.sp)
                    }
                }
            }
        } else {
            // [상태 1] 스틸컷 선택 화면
            Column(modifier = Modifier.fillMaxSize().padding(top = 40.dp)) {
                Row(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_back),
                        contentDescription = "뒤로가기",
                        Modifier.size(24.dp).clickable { rootNavigator?.pop() }
                    )
                }
                if (stillcutList.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(stillcutList) { scene ->
                            var isImageLoading by remember { mutableStateOf(false) }
                            StillcutItem(
                                id = scene.stillcutId,
                                stillcut = scene.imageUrl,
                                title = scene.title,
                                onClick = { isImageLoading = true
                                    selectedStillcutId = scene.stillcutId}
                            )
                            if (isImageLoading) {
                                rememberImageBitmapFromUrl(
                                    url = scene.imageUrl,
                                    onSuccess = { loadedBitmap ->
                                        screenModel.selectOriginalBitmap(loadedBitmap)
                                        //permissionsController.providePermission(Permission.CAMERA)
                                        launchCamera()
                                        isImageLoading = false
                                        println("onSuccess")
                                    },
                                    onFailure = { errorMessage ->
                                        println("이미지 로딩 실패: $errorMessage")
                                        isImageLoading = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}