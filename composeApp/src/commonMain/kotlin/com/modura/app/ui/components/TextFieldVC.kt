package com.modura.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.modura.app.ui.theme.Blue500
import com.modura.app.ui.theme.Gray500
import com.modura.app.ui.theme.Gray800
import com.modura.app.ui.theme.White

@Composable
fun TextFieldVC(
    value: String,
    onValueChange: (String) -> Unit,
    remainingTime: Int
) {
    val shape = RoundedCornerShape(8.dp)
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    // 분/초 포맷팅
    val minutes = (remainingTime / 60).toString().padStart(2, '0')
    val seconds = (remainingTime % 60).toString().padStart(2, '0')

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(White)
            .border(width = 1.dp, color = White, shape = shape)
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "인증번호",
                style = MaterialTheme.typography.bodySmall,
                color = Gray500
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "$minutes:$seconds",
                style = MaterialTheme.typography.bodySmall,
                color = Blue500
            )
        }
        Spacer(modifier = Modifier.height(5.dp))

        BasicTextField(
            value = value,
            onValueChange = { newText ->
                if (newText.length <= 6) {
                    val filteredText = newText.filter { char -> char.isDigit() }
                    onValueChange(filteredText)
                    if (filteredText.length == 6) {
                        keyboardController?.hide()
                    }
                }
            },
            modifier = Modifier.focusRequester(focusRequester),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            decorationBox = {
                Row {
                    value.padEnd(6, ' ').forEach { char ->
                        Text(
                            text = char.toString(),
                            style = MaterialTheme.typography.titleLarge,
                            color = Gray800
                        )
                    }
                }
            }
        )

    }
}
