package com.modura.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import com.modura.app.ui.theme.Gray800
import com.modura.app.ui.theme.White

@Composable
fun TextFieldPN(
    middleValue: String,
    onMiddleValueChange: (String) -> Unit,
    lastValue: String,
    onLastValueChange: (String) -> Unit,
    onDone: () -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
){
    val shape = RoundedCornerShape(8.dp)
    val keyboardController = LocalSoftwareKeyboardController.current

    val middleFocusRequester = remember { FocusRequester() }
    val lastFocusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        middleFocusRequester.requestFocus()
    }

    LaunchedEffect(middleValue) {
        if (middleValue.length == 4) {
            lastFocusRequester.requestFocus()
        }
    }

    LaunchedEffect(lastValue) {
        if (lastValue.length == 4) {
            keyboardController?.hide()
            onDone()
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    if (middleValue.isNotBlank() || lastValue.isNotBlank()) {
                        onClear()
                    }
                }
            )
            .clip(shape)
            .background(White)
            .border(width = 1.dp, color = White, shape = shape)
            .clip(shape)
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        Text(
            text="휴대폰 번호",
            style=MaterialTheme.typography.bodySmall,
            color=Gray500
        )
        Spacer(modifier = Modifier.height(5.dp))
        Row(verticalAlignment = Alignment.CenterVertically){
            // --- 010 (고정) ---
            Text(
                text = "010",
                style = MaterialTheme.typography.titleLarge,
                color = Gray800,
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text("-", style = MaterialTheme.typography.titleLarge, color = Gray800)
            Spacer(modifier = Modifier.width(10.dp))

            // --- 중간 4자리 ---
            BasicTextField(
                value = middleValue,
                onValueChange = {
                    if (it.length <= 4) {
                        onMiddleValueChange(it.filter { char -> char.isDigit() })
                    }
                },
                modifier = Modifier.focusRequester(middleFocusRequester),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                decorationBox = {
                    Row {
                        middleValue.padEnd(4, ' ').forEach { char ->
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

            // --- 마지막 4자리 ---
            BasicTextField(
                value = lastValue,
                onValueChange = {
                    if (it.length <= 4) {
                        onLastValueChange(it.filter { char -> char.isDigit() })
                    }
                },
                modifier = Modifier.focusRequester(lastFocusRequester),
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
                        lastValue.padEnd(4, ' ').forEach { char ->
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
        }
    }
}