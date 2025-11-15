package com.modura.app.ui.screens.login

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import com.modura.app.LocalRootNavigator
import com.modura.app.data.datasourceImpl.LoginDataSourceImpl
import com.modura.app.data.repositoryImpl.LoginRepositoryImpl
import com.modura.app.ui.components.LoginBottomSheet
import com.modura.app.ui.screens.detail.ReviewScreen
import com.modura.app.ui.screens.home.HomeScreen
import com.modura.app.ui.screens.main.MainScreen
import com.modura.app.util.network.rememberKakaoAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import modura.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

class LoginScreen : Screen {
    override val key: String = "LoginScreenKey"

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val rootNavigator = LocalRootNavigator.current

        val screenModel = getScreenModel<LoginScreenModel>()

        val coroutineScope = rememberCoroutineScope()
        val uiState by screenModel.uiState.collectAsState()
        var startAnimation by remember { mutableStateOf(false) }
        var showBottomSheet by remember { mutableStateOf(false) }

        LaunchedEffect(uiState.success) {
            if (uiState.success) {
                if (screenModel.isNewUser.value) {
                    showBottomSheet = true
                } else {
                    rootNavigator?.replaceAll(MainScreen)
                }
            }
        }

        val kakaoAuth = rememberKakaoAuth { code, error ->
            if (code != null) {
                screenModel.login(code)
            } else {
                println("카카오 로그인 실패: $error")
            }
        }

        val gradientBrush = Brush.verticalGradient(colors = listOf(Color(0xFFCADBDB), Color(0xFF90D8D8)))

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
            modifier = Modifier.fillMaxSize().background(gradientBrush)
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center).alpha(animationAlpha),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(Res.drawable.ic_logo),
                    contentDescription = "Logo",
                    modifier = Modifier.width(75.dp).height(92.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = painterResource(Res.drawable.img_logo_text),
                    contentDescription = "Logo Text",
                    modifier = Modifier.width(133.dp)
                )
            }
            Image(
                painter = painterResource(Res.drawable.img_kakao_login),
                contentDescription = "Kakao Login",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .alpha(animationAlpha)
                    .padding(bottom=30.dp,start=50.dp, end=50.dp)
                    .fillMaxWidth()
                    .height(45.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        kakaoAuth.login()
                }
            )
        }
    }
}