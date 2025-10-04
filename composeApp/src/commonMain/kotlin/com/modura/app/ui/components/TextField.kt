package com.modura.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.modura.app.ui.theme.Gray500
import com.modura.app.ui.theme.Gray800
import com.modura.app.ui.theme.White

@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    title: String = "주제",
    placeholder: String = "입력란"
){
    val shape = RoundedCornerShape(8.dp)
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxWidth()
            .clip(shape)
            .background(White)
            .border(
                width = 1.dp,
                color = White,
                shape = shape
            )
            .padding(horizontal = 10.dp, vertical = 10.dp) // 패딩은 가장 안쪽에 적용합니다.
    ) {
        Text(
            text=title,
            style=MaterialTheme.typography.bodySmall,
            color=Gray500
        )
        Spacer(modifier = Modifier.height(5.dp))
        Box {
            BasicTextField(
                value = value,
                onValueChange = { newText ->
                    onValueChange(newText.replace(" ", ""))
                },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    color = Gray800
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                )
            )
            if (value.isEmpty()) {
                Text(
                    text = placeholder,
                    color = Gray500,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}