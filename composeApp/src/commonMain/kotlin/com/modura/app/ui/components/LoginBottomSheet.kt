package com.modura.app.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginBottomSheet(
    onDismissRequest: () -> Unit,
    onLoginClicked: () -> Unit,
    isPreview: Boolean = false
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { true },
    )

    if (isPreview) {
        LaunchedEffect(Unit) {
            sheetState.show()
        }
    }
    var agreePrivacyPolicy by remember { mutableStateOf(false) }
    var agreeTermsOfService by remember { mutableStateOf(false) }
    var agreePrivacyProcessing by remember { mutableStateOf(false) }

    val allRequiredAgreed = agreePrivacyPolicy && agreeTermsOfService && agreePrivacyProcessing

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = Color.White,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp,50.dp,20.dp,30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Text("회원가입을 위해 동의가 필요해요", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier=Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(20.dp))
            CheckText(
                checked = allRequiredAgreed,
                onCheckedChange = {
                    val newCheckedState = !allRequiredAgreed
                    agreePrivacyPolicy = newCheckedState
                    agreeTermsOfService = newCheckedState
                    agreePrivacyProcessing = newCheckedState
                },
                text = "필수 전체 동의 선택",
                textStyle = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(10.dp))
            CheckTextDetail(
                checked = agreePrivacyPolicy,
                onCheckedChange = { agreePrivacyPolicy = it },
                onDetailClick = { /* TODO: 상세 보기 화면으로 이동 */ },
                text = "개인정보 수집 및 이용 동의"
            )
            Spacer(modifier = Modifier.height(5.dp))

            CheckTextDetail(
                checked = agreeTermsOfService,
                onCheckedChange = { agreeTermsOfService = it },
                onDetailClick = { /* TODO: 상세 보기 화면으로 이동 */ },
                text = "서비스 이용약관"
            )
            Spacer(modifier = Modifier.height(5.dp))

            CheckTextDetail(
                checked = agreePrivacyProcessing,
                onCheckedChange = { agreePrivacyProcessing = it },
                onDetailClick = { /* TODO: 상세 보기 화면으로 이동 */ },
                text = "개인정보 처리방침"
            )
            Spacer(modifier = Modifier.height(30.dp))

            MiddleButton(
                text = "확인",
                onClick = onLoginClicked,
                enabled = allRequiredAgreed
            )
        }
    }
}


@Preview
@Composable
fun LoginBottomSheetPreview(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("여기가 원래 화면입니다.") // BottomSheet 뒤에 보일 내용
    }
    LoginBottomSheet(
        onDismissRequest = {},
        onLoginClicked = {},
        isPreview = true
    )
}