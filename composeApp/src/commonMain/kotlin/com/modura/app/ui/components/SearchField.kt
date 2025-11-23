package com.modura.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.modura.app.ui.screens.search.SearchScreenModel
import com.modura.app.ui.theme.Gray500
import com.modura.app.ui.theme.Gray800
import com.modura.app.ui.theme.White
import modura.composeapp.generated.resources.Res
import modura.composeapp.generated.resources.ic_delete
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    onSearch:(String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "검색어를 입력해주세요.",
    white: Boolean =  false
){
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(if(white)White else MaterialTheme.colorScheme.surface)
            .clip(RoundedCornerShape(8.dp))
            .height(50.dp)
            .padding(horizontal = 20.dp, vertical = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = if(white)MaterialTheme.colorScheme.onBackground else White
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(value)
                    keyboardController?.hide()
                }
            )
        )
        // placeholder 표시
        if (value.isEmpty()) {
            Text(
                text = placeholder,
                color = Gray500,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        if (value.isNotEmpty()) {
            Icon(
                painterResource(Res.drawable.ic_delete),
                contentDescription = "Clear text",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable { onValueChange("") },
                tint = if(white)White else MaterialTheme.colorScheme.onBackground
            )
        }
    }
}