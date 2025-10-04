package com.modura.app.ui.screens.login

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.modura.app.ui.screens.main.MainScreen
import com.modura.app.ui.theme.White
import kotlinx.coroutines.delay
import modura.composeapp.generated.resources.Res
import modura.composeapp.generated.resources.img_file
import org.jetbrains.compose.resources.painterResource

class StartScreen : Screen {
    override val key: String = "StartScreen"

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val coroutineScope = rememberCoroutineScope()
        var isLoading by remember { mutableStateOf(false) }
        var startAnimation by remember { mutableStateOf(false) }

        var showBottomSheet by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            startAnimation = true
            delay(5000)
            navigator.replaceAll(MainScreen)
            println("5초 경과! HomeScreen으로 이동합니다.")
        }

        val image1OffsetY by animateDpAsState(
            targetValue = if (startAnimation) 358.dp else 1000.dp, // 목표 위치 vs 화면 아래
            animationSpec = tween(durationMillis = 1000, delayMillis = 200) // 1초 동안, 0.2초 후 시작
        )
        val image2OffsetY by animateDpAsState(
            targetValue = if (startAnimation)438.dp else 1100.dp, // 목표 위치 vs 화면 아래
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
                    contentDescription = "Background Image 1",
                    modifier = Modifier
                        .size(300.dp, 850.dp)
                        .offset(x = 129.dp, y = image1OffsetY),
                    contentScale = ContentScale.Fit
                )
                Image(
                    painter = painterResource(Res.drawable.img_file),
                    contentDescription = "Background Image 2",
                    modifier = Modifier
                        .size(300.dp, 850.dp)
                        .offset(x = 49.dp, y = image2OffsetY),
                    contentScale = ContentScale.Fit
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 20.dp, top = 120.dp, end=20.dp, bottom=20.dp), // 화면 전체 패딩
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.alpha(textAlpha)
                    ) {
                        Text(
                            text = "로그인 완료!",
                            style = MaterialTheme.typography.labelLarge
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                        Text(
                            text = "김승혁님\n환영합니다!",
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "modura",
                        style = MaterialTheme.typography.headlineSmall,
                        color = White
                    )
                }
            }
        }
    }
}