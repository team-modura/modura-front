package com.modura.app.ui.screens.login

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.modura.app.ui.components.LargeButton
import com.modura.app.ui.components.TextField
import com.modura.app.ui.components.TextFieldRNN

class SignupScreen(
    val onSignupComplete: () -> Unit
) : Screen {

    @Composable
    override fun Content() {
        // --- 상태 변수 정의 ---
        // 1. 주민등록번호 필드를 보여줄지 여부를 결정하는 상태
        var showRnnField by remember { mutableStateOf(false) }

        // 이름 입력 상태
        var nickname by remember { mutableStateOf("") }

        // 주민등록번호 입력 상태
        var rnnFront by remember { mutableStateOf("") }
        var rnnBack by remember { mutableStateOf("") }

        // 버튼 활성화 로직
        val isButtonEnabled = nickname.isNotBlank()


        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .weight(1f) // 하단 버튼을 제외한 모든 공간을 차지
                ) {
                    Text(
                        text = if (showRnnField) "주민등록번호를 입력해주세요." else "이름을 입력해주세요.",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(top = 90.dp, bottom = 20.dp)
                    )

                    if (showRnnField) {
                        TextFieldRNN(
                            frontValue = rnnFront,
                            onFrontValueChange = { rnnFront = it },
                            backValue = rnnBack,
                            onBackValueChange = { rnnBack = it },
                            onDone = {
                                // 주민번호 입력 완료 시 로그 출력
                                println("주민등록번호 입력 완료: $rnnFront-$rnnBack")
                                // TODO: 서버로 모든 정보 전송 및 로그인 완료 처리
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp)) // 이름 필드와의 간격
                    TextField(
                        value = nickname,
                        onValueChange = { nickname = it },
                        title = "이름",
                        placeholder = "입력"
                    )
                }

                // --- 하단 버튼 ---
                // 4. showRnnField가 true이면 버튼을 숨깁니다.
                if (!showRnnField) {
                    LargeButton(
                        text = "다음",
                        onClick = {
                            if (isButtonEnabled) {
                                // 버튼 클릭 시 주민번호 필드를 보이도록 상태 변경
                                showRnnField = true
                            }
                        },
                        enabled = isButtonEnabled
                    )
                }
            }
        }
    }
}