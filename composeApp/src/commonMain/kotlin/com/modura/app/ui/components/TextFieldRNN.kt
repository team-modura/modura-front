package com.modura.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.modura.app.ui.theme.Gray500
import com.modura.app.ui.theme.Gray600
import com.modura.app.ui.theme.Gray800
import com.modura.app.ui.theme.White

@Composable
fun TextFieldRNN(
    frontValue: String,
    onFrontValueChange: (String) -> Unit,
    backValue: String,
    onBackValueChange: (String) -> Unit,
    onDone: () -> Unit,
    modifier: Modifier = Modifier
){
    val shape = RoundedCornerShape(8.dp)
    val keyboardController = LocalSoftwareKeyboardController.current

    val backFocusRequester = remember { FocusRequester() }

    LaunchedEffect(frontValue) {
        if (frontValue.length == 6) {
            backFocusRequester.requestFocus()
        }
    }

    LaunchedEffect(backValue) {
        if (backValue.length == 7) {
            keyboardController?.hide()
            onDone()
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(White)
            .border(width = 1.dp, color = White, shape = shape)
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        Text(
            text="주민등록번호",
            style=MaterialTheme.typography.bodySmall,
            color=Gray500
        )
        Spacer(modifier = Modifier.height(5.dp))
        Row(verticalAlignment = Alignment.CenterVertically){
            BasicTextField(
                value = frontValue,
                onValueChange = {
                    if (it.length <= 6) {
                        onFrontValueChange(it.filter { char -> char.isDigit() })
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                decorationBox = {
                    Row {
                        frontValue.padEnd(6, '_').forEach { char ->
                            Text(
                                text = char.toString(),
                                style = MaterialTheme.typography.titleLarge,
                                color = Gray800,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text("-", style = MaterialTheme.typography.titleLarge, color = Gray800)
            Spacer(modifier = Modifier.width(10.dp))
            BasicTextField(
                value = backValue,
                onValueChange = {
                    if (it.length <= 7) {
                        onBackValueChange(it.filter { char -> char.isDigit() })
                    }
                },
                modifier = Modifier.focusRequester(backFocusRequester),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    onDone()
                }),
                decorationBox = {
                    Row {
                        val firstChar = backValue.getOrNull(0)?.toString() ?: "_"
                        Text(
                            text = firstChar,
                            style = MaterialTheme.typography.titleLarge,
                            color = Gray800,
                            textAlign = TextAlign.Center
                        )
                        repeat(6) {
                            Text(
                                text = "●",
                                style = MaterialTheme.typography.titleLarge,
                                color = Gray600,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            )
        }
    }
}