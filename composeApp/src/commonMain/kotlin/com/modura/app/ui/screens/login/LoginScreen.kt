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
import androidx.compose.foundation.layout.fillMaxWidth
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
import cafe.adriel.voyager.navigator.LocalNavigator
import com.modura.app.LocalRootNavigator
import com.modura.app.ui.components.LoginBottomSheet
import com.modura.app.ui.screens.detail.ReviewScreen
import com.modura.app.ui.screens.home.HomeScreen
import com.modura.app.ui.screens.main.MainScreen
import kotlinx.coroutines.flow.flowOf
import modura.composeapp.generated.resources.Res
import modura.composeapp.generated.resources.img_file
import modura.composeapp.generated.resources.img_flicker_1
import modura.composeapp.generated.resources.img_flicker_2
import modura.composeapp.generated.resources.img_kakao_login
import modura.composeapp.generated.resources.img_logo_text
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

class LoginScreen : Screen {
    override val key: String = "LoginScreenKey"

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val rootNavigator = LocalRootNavigator.current

        val coroutineScope = rememberCoroutineScope()
        var isLoading by remember { mutableStateOf(false) }   //loading effect 조건
        var startAnimation by remember { mutableStateOf(false) }   //추후 애니메이션 추가 예정
        var showBottomSheet by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            startAnimation = true
        }

        //text 나타나는 효과
        val animationAlpha by animateFloatAsState(
            targetValue = if (startAnimation) 1f else 0f,
            animationSpec = tween(durationMillis = 1000, delayMillis = 600)
        )

        if (showBottomSheet) {
            LoginBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                onLoginClicked = {
                    showBottomSheet = false
                    rootNavigator?.push(SignupScreen())
                }
            )
        }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                Image(
                    painter = painterResource(Res.drawable.img_flicker_1),
                    contentDescription = "Background Image 1",
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(y=120.dp)
                )
                Image(
                    painter = painterResource(Res.drawable.img_flicker_2),
                    contentDescription = "Background Image 2",
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(x=50.dp,y=120.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp, vertical = 60.dp), // 화면 전체 패딩
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.alpha(animationAlpha).padding(start = 40.dp, top=120.dp, end = 40.dp, bottom =0.dp)
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.img_logo_text),
                            contentDescription = "Logo Text",
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "AI가 추천하는 K-콘텐츠 로드맵",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        painter = painterResource(Res.drawable.img_kakao_login),
                        contentDescription = "Kakao Login",
                        modifier =Modifier
                            .alpha(animationAlpha)
                            .width(300.dp)
                            .height(45.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                showBottomSheet = true
                            }
                    )
                }
            }
        }
    }
}