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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.modura.app.ui.components.DisplayableItem
import com.modura.app.ui.components.LargeButton
import com.modura.app.ui.components.ListBottomSheet
import com.modura.app.ui.components.TextField
import com.modura.app.ui.components.TextFieldPN
import com.modura.app.ui.components.TextFieldRNN

data class Telecom(val id: String, override val displayName: String) : DisplayableItem

class SignupScreen(
    val onSignupComplete: () -> Unit
) : Screen {

    private val telecomOptions = listOf(
        Telecom("SKT", "SKT"),
        Telecom("KT", "KT"),
        Telecom("LGU", "LG U+"),
        Telecom("SKT_MVNO", "SKT 알뜰폰"),
        Telecom("KT_MVNO", "KT 알뜰폰"),
        Telecom("LGU_MVNO", "LG U+ 알뜰폰")
    )

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow // 👈 2. navigator 객체 가져오기

        var currentStep by remember { mutableStateOf(1) }
        var nickname by remember { mutableStateOf("") }
        var rnnFront by remember { mutableStateOf("") }
        var rnnBack by remember { mutableStateOf("") }

        var pnMiddle by remember { mutableStateOf("") }
        var pnLast by remember { mutableStateOf("") }

        var showTelecomSheet by remember { mutableStateOf(false) }
        var selectedTelecom by remember { mutableStateOf<Telecom?>(null) }

        // 버튼 활성화 로직
        val isButtonEnabled = nickname.isNotBlank()

        Box(modifier = Modifier.fillMaxSize()) {
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
                            text = when (currentStep) {
                                1 -> "이름을 입력해주세요."
                                2 -> "주민등록번호를 입력해주세요."
                                3 -> "통신사를 선택해주세요."
                                4 -> "휴대폰 번호를 입력해주세요."
                                else -> ""
                            },
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(top = 90.dp, bottom = 20.dp)
                        )
                        if (currentStep >= 4) {
                            TextFieldPN(
                                middleValue = pnMiddle,
                                onMiddleValueChange = { pnMiddle = it },
                                lastValue = pnLast,
                                onLastValueChange = { pnLast = it },
                                onDone = {
                                    println("전화번호 입력 완료: 010-$pnMiddle-$pnLast")
                                    // TODO: 최종 완료 로직 (예: onSignupComplete() 호출)
                                },
                                onClear = {
                                    pnMiddle = ""
                                    pnLast = ""
                                }
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                        if (currentStep >= 3) {
                            TextField(
                                value = selectedTelecom?.displayName ?: "",
                                onValueChange = {},
                                title = "통신사",
                                placeholder = "통신사",
                                enabled = false,
                                onBodyClick = {
                                    println("통신사 TextField 클릭됨!")
                                    showTelecomSheet = true
                                }
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                        if (currentStep >= 2) {
                            TextFieldRNN(
                                frontValue = rnnFront,
                                onFrontValueChange = { rnnFront = it },
                                backValue = rnnBack,
                                onBackValueChange = { rnnBack = it },
                                onDone = { newBackValue ->
                                    println("주민등록번호 입력 완료: $rnnFront-$newBackValue")
                                    if (currentStep < 3) currentStep = 3
                                },
                                onClear = {
                                    rnnFront = ""
                                    rnnBack = ""
                                }
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                        TextField(
                            value = nickname,
                            onValueChange = { nickname = it },
                            title = "이름",
                            placeholder = "입력"
                        )
                    }

                    if (currentStep == 1 || currentStep == 4) {
                        LargeButton(
                            text = "확인",
                            onClick = {
                                if (isButtonEnabled) {
                                    if (currentStep == 1) {
                                        currentStep = 2
                                    } else {
                                        println("인증 문자 받기 클릭됨. SignupVCScreen으로 이동합니다.")
                                        navigator.push(SignupVCScreen())
                                    }
                                }
                            },
                            enabled = isButtonEnabled
                        )
                    }
                }
            }
            if (showTelecomSheet) {
                ListBottomSheet(
                    title = "통신사 선택",
                    items = telecomOptions,
                    onDismissRequest = { showTelecomSheet = false },
                    onSelect = { telecom ->
                        selectedTelecom = telecom
                        currentStep = 4
                        showTelecomSheet = false
                    }
                )
            }
        }
    }
}