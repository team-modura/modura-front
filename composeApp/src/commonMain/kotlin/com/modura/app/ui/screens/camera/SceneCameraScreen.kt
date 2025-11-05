package com.modura.app.ui.screens.camera

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
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
import org.jetbrains.compose.resources.painterResource

data class SceneCameraScreen(val sceneImageRes: String) : Screen {

    @Composable
    override fun Content() {
        val factory = rememberPermissionsControllerFactory()
        val coroutineScope = rememberCoroutineScope() // 1. 코루틴 스코프를 다시 사용합니다.
        val permissionsController: PermissionsController = remember(factory) { factory.createPermissionsController() }
        var hasPermission by remember { mutableStateOf(false) }
        var capturedImage by remember { mutableStateOf<ImageBitmap?>(null) }

        val launchCamera = rememberCameraManager { imageBitmap ->
            capturedImage = imageBitmap
            if (imageBitmap != null) {
                println("촬영 성공!")
            } else {
                println("촬영이 취소되었거나 실패했습니다.")
            }
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (capturedImage != null) {
                // 촬영 후 화면
                Text("촬영된 사진")
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    bitmap = capturedImage!!,
                    contentDescription = "촬영된 사진",
                    modifier = Modifier.weight(1f),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { capturedImage = null }) {
                    Text("다시 찍기")
                }

            } else {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(Res.drawable.img_example),
                        contentDescription = "원본 명장면",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    Button(
                        onClick = {
                            coroutineScope.launch(kotlinx.coroutines.Dispatchers.Main) { println("카메라 열기 버튼 클릭, 메인 스레드 코루틴 시작!")

                                val permissionState = withContext(kotlinx.coroutines.Dispatchers.IO) {
                                    println("IO 스레드에서 getPermissionState 호출 시작...")
                                    permissionsController.getPermissionState(Permission.CAMERA)
                                }
                                println("현재 permissionState: $permissionState")

                                if (permissionState == PermissionState.Granted) {
                                    println("권한이 이미 있으므로 카메라를 실행합니다.")
                                    launchCamera()
                                } else {
                                    println("권한이 없으므로 권한을 요청합니다.")
                                    permissionsController.providePermission(Permission.CAMERA)

                                    val newState = permissionsController.getPermissionState(Permission.CAMERA)
                                    println("요청 후 newState: $newState")

                                    if (newState == PermissionState.Granted) {
                                        println("사용자가 권한을 허용했습니다. 카메라를 실행합니다.")
                                        launchCamera()
                                    } else {
                                        println("카메라 권한이 최종적으로 허용되지 않았습니다.")
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 32.dp)
                    ) {
                        Text("카메라 열기")
                    }
                }
            }
        }
    }
}