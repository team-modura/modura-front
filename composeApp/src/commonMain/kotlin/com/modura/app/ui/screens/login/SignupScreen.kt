package com.modura.app.ui.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import org.jetbrains.compose.ui.tooling.preview.Preview

class SignupScreen(
    val onSignupComplete: () -> Unit
) : Screen {

    @Composable
    override fun Content() {
        var nickname by remember { mutableStateOf("") }
        val isButtonEnabled = nickname.isNotBlank()

        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
                ) {
                    Text(
                        text = "이름을 입력해주세요.",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(top = 90.dp, bottom = 20.dp)
                    )

                    TextField(
                        value = nickname,
                        onValueChange = { nickname = it },
                        title = "이름",
                        placeholder = "입력"
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                LargeButton(
                    onClick = {
                        if (isButtonEnabled) {
                            onSignupComplete()
                        }
                    },
                    enabled = isButtonEnabled
                )
            }
        }
    }
}

@Preview
@Composable
fun SignupScreenPreview() {
    SignupScreen(
        onSignupComplete = {}
    )
}