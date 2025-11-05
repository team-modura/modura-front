package com.modura.app.ui.screens.camera

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.modura.app.ui.components.SceneScoreDetail
import com.modura.app.util.platform.ImageComparator
import com.modura.app.util.platform.rememberCameraManager
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modura.composeapp.generated.resources.Res
import modura.composeapp.generated.resources.img_example
import modura.composeapp.generated.resources.img_scene_example
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource

data class SceneCameraScreen(val sceneImageRes: String) : Screen {

    @Composable
    override fun Content() {
        val factory = rememberPermissionsControllerFactory()
        val coroutineScope = rememberCoroutineScope()
        val permissionsController: PermissionsController =
            remember(factory) { factory.createPermissionsController() }

        var capturedImage by remember { mutableStateOf<ImageBitmap?>(null) }
        var isLoading by remember { mutableStateOf(false) }

        //점수 저장
        var totalScore by remember { mutableStateOf<Double?>(null) }
        var structureScore by remember { mutableStateOf<Double?>(null) }
        var clarityScore by remember { mutableStateOf<Double?>(null) }
        var toneScore by remember { mutableStateOf<Double?>(null) }
        var paletteScore by remember { mutableStateOf<Double?>(null) }


        val launchCamera = rememberCameraManager { imageBitmap -> capturedImage = imageBitmap }
        val originalBitmap = imageResource(Res.drawable.img_scene_example)

        LaunchedEffect(capturedImage) {
            if (capturedImage != null) {
                isLoading = true
                val captured = capturedImage!!
                val toneSim = ImageComparator.calculateSimilarity(originalBitmap, captured)
                val paletteSim = ImageComparator.calculatePaletteSimilarity(
                    ImageComparator.extractPalette(originalBitmap, 5),
                    ImageComparator.extractPalette(captured, 5)
                )
                val structSim =
                    ImageComparator.calculateStructuralSimilarity(originalBitmap, captured)
                val clarity = ImageComparator.calculateClarity(captured)

                // 가중치: 구도(30%), 선명도(20%), 색감(25%), 색구성(25%)
                totalScore =
                    (structSim * 0.3) + (clarity * 0.2) + (toneSim * 0.25) + (paletteSim * 0.25)
                structureScore = structSim
                clarityScore = clarity
                toneScore = toneSim
                paletteScore = paletteSim

                isLoading = false
            } else {
                // '다시 찍기' 시 모든 상태 초기화
                totalScore = null; structureScore = null; clarityScore = null; toneScore =
                    null; paletteScore = null
                isLoading = false
            }
        }



        if (capturedImage != null) {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 1. 종합 점수
                Text("유사도", style = MaterialTheme.typography.titleMedium)
                Text(
                    "${totalScore?.toInt() ?: 0}%",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))

                // 2. 이미지 비교
                Row(
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Image(
                        bitmap = originalBitmap,
                        contentDescription = "원본 사진",
                        modifier = Modifier.weight(1f).clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Image(
                        bitmap = capturedImage!!,
                        contentDescription = "촬영한 사진",
                        modifier = Modifier.weight(1f).clip(androidx.compose.foundation.shape.RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))

                // 3. 세부 점수
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
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
                    TextButton(onClick =  { println("저장하기 버튼 클릭됨. 저장 기능은 구현 필요.")
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
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(Res.drawable.img_scene_example),
                    contentDescription = "원본 명장면",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Button(
                    onClick = onLaunchCamera,
                    modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 32.dp)
                ) {
                    Text("카메라 열기")
                }
            }
        }
    }
}