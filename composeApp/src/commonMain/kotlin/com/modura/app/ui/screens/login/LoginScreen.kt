package com.modura.app.ui.screens.login

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import cafe.adriel.voyager.core.screen.Screen
import com.modura.app.data.AuthRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import modura.composeapp.generated.resources.Res
import modura.composeapp.generated.resources.img_file
import modura.composeapp.generated.resources.img_kakao_login
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

class LoginScreen(private val authRepository: AuthRepository) : Screen {
    override val key: String = "LoginScreenKey"

    @Composable
    override fun Content() {
        val coroutineScope = rememberCoroutineScope()
        var isLoading by remember { mutableStateOf(false) }
        var startAnimation by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            startAnimation = true
        }

        val image1OffsetY by animateDpAsState(
            targetValue = if (startAnimation) 458.dp else 1000.dp, // 목표 위치 vs 화면 아래
            animationSpec = tween(durationMillis = 1000, delayMillis = 200) // 1초 동안, 0.2초 후 시작
        )
        val image2OffsetY by animateDpAsState(
            targetValue = if (startAnimation) 538.dp else 1100.dp, // 목표 위치 vs 화면 아래
            animationSpec = tween(durationMillis = 1000, delayMillis = 400) // 1초 동안, 0.4초 후 시작
        )
        val textAlpha by animateFloatAsState(
            targetValue = if (startAnimation) 1f else 0f, // 1f: 불투명, 0f: 투명
            animationSpec = tween(durationMillis = 1000, delayMillis = 600) // 0.6초 뒤에 시작
        )

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                Image(
                    painter = painterResource(Res.drawable.img_file),
                    contentDescription = "Background Image 2",
                    modifier = Modifier
                        .size(300.dp, 500.dp)
                        .offset(x = 49.dp, y = image2OffsetY),
                    contentScale = ContentScale.Crop
                )
                Image(
                    painter = painterResource(Res.drawable.img_file),
                    contentDescription = "Background Image 1",
                    modifier = Modifier
                        .size(300.dp, 500.dp)
                        .offset(x = 129.dp, y = image1OffsetY),
                    contentScale = ContentScale.Crop
                )

                // 상단 텍스트 및 하단 로그인 버튼
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp, vertical = 120.dp), // 화면 전체 패딩
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.alpha(textAlpha)
                    ) {
                        Text(
                            text = "Modura",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "나에게 필요했던 복지",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        painter = painterResource(Res.drawable.img_kakao_login),
                        contentDescription = "Kakao Login",
                        modifier =Modifier
                            .width(300.dp)
                            .height(45.dp)
                            .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null // 클릭 시 물결 효과 제거
                        ) {
                            isLoading = true
                            coroutineScope.launch {
                                authRepository.saveToken("DUMMY_TOKEN_FOR_LOGGED_IN_USER")
                            }
                        }
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun LoginScreenPreview() {
    val dummyDataStore: DataStore<Preferences> = object : DataStore<Preferences> {
        override val data = flowOf<Preferences>()
        override suspend fun updateData(transform: suspend (t: Preferences) -> Preferences): Preferences {
            throw NotImplementedError("This dummy implementation should not be called.")
        }
    }

    val dummyAuthRepository = AuthRepository(dummyDataStore)

    LoginScreen(dummyAuthRepository).Content()
}
