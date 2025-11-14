package com.modura.app.ui.screens.camera

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import coil3.ImageLoader
import coil3.compose.LocalPlatformContext
import com.modura.app.LocalRootNavigator
import com.modura.app.data.dev.DummyProvider.stillcut
import com.modura.app.ui.components.SceneScoreDetail
import com.modura.app.ui.components.StillcutItem
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

class StillcutScreen(placeId:Int) : Screen {

    @Composable
    override fun Content() {
        val factory = rememberPermissionsControllerFactory()
        val coroutineScope = rememberCoroutineScope()
        val context = LocalPlatformContext.current
        val permissionsController: PermissionsController = remember(factory) { factory.createPermissionsController() }
        val imageLoader = remember { ImageLoader(context) }
        val rootNavigator = LocalRootNavigator.current

        var capturedImage by remember { mutableStateOf<ImageBitmap?>(null) }
        var isLoading by remember { mutableStateOf(false) }

        //점수 저장
        var totalScore by remember { mutableStateOf<Double?>(null) }
        var structureScore by remember { mutableStateOf<Double?>(null) }
        var clarityScore by remember { mutableStateOf<Double?>(null) }
        var toneScore by remember { mutableStateOf<Double?>(null) }
        var paletteScore by remember { mutableStateOf<Double?>(null) }


        val launchCamera = rememberCameraManager { imageBitmap -> capturedImage = imageBitmap }
        var originalBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

        LaunchedEffect(capturedImage) {
            if (capturedImage != null && originalBitmap != null) {
                isLoading = true
                withContext(kotlinx.coroutines.Dispatchers.Default) {
                    println("백그라운드 스레드에서 이미지 분석 시작")
                    val captured = capturedImage!!
                    val original = originalBitmap!!

                    val toneSim = ImageComparator.calculateSimilarity(original, captured)
                    val paletteSim = ImageComparator.calculatePaletteSimilarity(
                        ImageComparator.extractPalette(original, 5),
                        ImageComparator.extractPalette(captured, 5)
                    )
                    val structSim = ImageComparator.calculateStructuralSimilarity(original, captured)
                    val clarity = ImageComparator.calculateClarity(captured)

                    withContext(kotlinx.coroutines.Dispatchers.Main) {
                        println("메인 스레드로 복귀하여 UI 상태 업데이트")
                        // 가중치: 구도(30%), 선명도(20%), 색감(25%), 색구성(25%)
                        totalScore =
                            (structSim * 0.3) + (clarity * 0.2) + (toneSim * 0.25) + (paletteSim * 0.25)
                        structureScore = structSim
                        clarityScore = clarity
                        toneScore = toneSim
                        paletteScore = paletteSim

                        // 4. 모든 UI 상태 업데이트가 끝나면 로딩 상태를 해제한다.
                        isLoading = false
                    }
                }
            } else {
                // '다시 찍기' 시 모든 상태 초기화
                totalScore = null; structureScore = null; clarityScore = null; toneScore =
                    null; paletteScore = null
                isLoading = false
            }
        }



        if (capturedImage != null) {
            Column(
                modifier = Modifier.fillMaxSize().padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 1. 종합 점수
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
                    Image(
                        bitmap = originalBitmap!!,
                        contentDescription = "원본 사진",
                        modifier = Modifier.weight(1f).clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Image(
                        bitmap = capturedImage!!,
                        contentDescription = "촬영한 사진",
                        modifier = Modifier.weight(1f).clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.height(80.dp))

                // 3. 세부 점수
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

                // 4. 하단 버튼
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    TextButton(onClick = { capturedImage = null }) {
                        Text("다시 찍기", fontSize = 16.sp)
                    }
                    TextButton(onClick =  {
                        rootNavigator?.push(PlaceDetailScreen(id = 1))
                    }) {
                        Text("저장하기", fontSize = 16.sp)
                    }
                }
            }
        } else {
            val onLaunchCamera:() -> Unit = {
                coroutineScope.launch {
                    val hasPermission = withContext(kotlinx.coroutines.Dispatchers.IO) {
                        permissionsController.getPermissionState(Permission.CAMERA) == PermissionState.Granted
                    }
                    if (hasPermission) launchCamera() else println("카메라 권한이 없습니다.")
                }
            }
            Column(modifier = Modifier.fillMaxSize().padding(top=40.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(20.dp)
                ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_back),
                            contentDescription = "뒤로가기",
                            Modifier.size(24.dp).clickable{rootNavigator?.pop()}
                        )
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues( bottom = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(stillcut) { scene ->
                        var isImageLoading by remember { mutableStateOf(false) }
                        StillcutItem(
                            scene = scene,
                            onClick = {
                                isImageLoading = true
                            }
                        )
                        if (isImageLoading) {
                            rememberImageBitmapFromUrl(
                                url = scene.imageResId,
                                onSuccess = { loadedBitmap ->
                                    originalBitmap = loadedBitmap
                                    onLaunchCamera()
                                    println("${scene.title} 선택됨, 이미지 URL: ${scene.imageResId}")
                                    println(originalBitmap)
                                    isImageLoading = false // 로딩 완료
                                },
                                onFailure = { errorMessage ->
                                    println("이미지 로딩 실패: $errorMessage")
                                    isImageLoading = false // 로딩 완료
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}