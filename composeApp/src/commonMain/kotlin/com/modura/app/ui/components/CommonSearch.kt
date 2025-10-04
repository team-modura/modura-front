package com.modura.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.modura.app.ui.theme.Gray500
import com.modura.app.ui.theme.Green700
import com.modura.app.ui.theme.White
import modura.composeapp.generated.resources.Res
import modura.composeapp.generated.resources.ic_delete
import modura.composeapp.generated.resources.ic_search
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonSearch(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "검색어를 입력해주세요"
) {
    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .background(White, RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .height(50.dp),
        singleLine = true,
        decorationBox = { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = value,
                innerTextField = innerTextField,
                enabled = true,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                placeholder = {
                    Text(text = hint, color = Gray500, style = MaterialTheme.typography.bodyMedium)
                },
                trailingIcon = {
                    if (value.isNotEmpty()) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_delete),
                            tint = Green700,
                            contentDescription = "입력 내용 지우기",
                            modifier = Modifier.clickable {
                                onValueChange("")
                            }
                        )
                    } else {
                        Icon(
                            painter = painterResource(Res.drawable.ic_search),
                            tint = Green700,
                            contentDescription = "검색 아이콘"
                        )
                    }
                },
                contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                    start = 12.dp, end = 12.dp, top = 0.dp, bottom = 0.dp
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
            )
        }
    )
}