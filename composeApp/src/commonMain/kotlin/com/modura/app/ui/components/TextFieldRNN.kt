package com.modura.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
    onDone: (String) -> Unit,
    onClear: () -> Unit,
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

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
//                indication = null,
                onClick = {
                    if (frontValue.isNotBlank()) {
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
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        frontValue.padEnd(6, ' ').forEach { char ->
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
                    val newText = it.filter { char -> char.isDigit() }
                    if (newText.length == 1 && backValue.isEmpty()) {
                        onBackValueChange(newText)
                        keyboardController?.hide()
                        onDone(newText)
                    } else if (newText.isEmpty()) {
                        onBackValueChange(newText)
                    }
                },
                modifier = Modifier.focusRequester(backFocusRequester),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                decorationBox = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        val firstChar = backValue.getOrNull(0)?.toString() ?: " "
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