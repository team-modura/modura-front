package com.modura.app.ui.screens.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.modura.app.ui.components.CommonChips
import com.modura.app.ui.components.CommonToast
import com.modura.app.ui.components.LargeButton
import com.modura.app.ui.components.TextFieldVC
import com.modura.app.util.getCurrentTimeMillis
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

class SignupVCScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        var verificationCode by remember { mutableStateOf("") }
        val correctCode = "123456"

        val initialTime = 5 * 60 // 5분 = 300초
        var remainingTime by remember { mutableStateOf(initialTime) }

        val resendCoolDown = 30 // 재전송 대기 시간 30초
        var lastResendTimestamp by remember { mutableStateOf(0L) }
        val canResend = (getCurrentTimeMillis() - lastResendTimestamp) / 1000 > resendCoolDown

        var toastMessage by remember { mutableStateOf<String?>(null) }
        var showToast by remember { mutableStateOf(false) }

        val isButtonEnabled = verificationCode.length == 6

        LaunchedEffect(key1 = remainingTime) {
            if (remainingTime > 0) {
                delay(1.seconds)
                remainingTime--
            }
        }

        LaunchedEffect(key1 = showToast) {
            if (showToast) {
                delay(2.seconds)
                showToast = false
            }
        }

        Scaffold { innerPadding ->
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(horizontal = 20.dp)
                    ) {
                        Text(
                            text = "문자로 전송된\n인증번호 6자리를 입력해주세요.",
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(top = 90.dp, bottom = 20.dp)
                        )

                        TextFieldVC(
                            value = verificationCode,
                            onValueChange = { verificationCode = it },
                            remainingTime = remainingTime
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        CommonChips(
                            text = "인증 문자가 안 와요",
                            enabled = canResend,
                            onClick = {
                                val currentTime = getCurrentTimeMillis()
                                if (canResend) {
                                    println("인증번호 재전송 요청")
                                    lastResendTimestamp = currentTime
                                    toastMessage = "인증번호를 다시 전송했어요"
                                    showToast = true
                                } else {
                                    val timeLeft =
                                        resendCoolDown - ((currentTime - lastResendTimestamp) / 1000)
                                    toastMessage = "${timeLeft}초 뒤에 다시 시도해주세요"
                                    showToast = true
                                }
                            }
                        )
                    }

                    LargeButton(
                        text = "다음",
                        enabled = isButtonEnabled,
                        onClick = {
                            if(isButtonEnabled) {
                                if (verificationCode == correctCode) {
                                    println("인증 성공! StartScreen으로 이동합니다.")
                                    navigator.popUntilRoot()
                                } else {
                                    toastMessage = "인증번호가 틀렸습니다."
                                    showToast = true
                                }
                            }
                        }
                    )
                }
            }

            // --- 토스트 메시지 ---
            if (showToast && toastMessage != null) {
                CommonToast(message = toastMessage!!)
            }
        }
    }
}