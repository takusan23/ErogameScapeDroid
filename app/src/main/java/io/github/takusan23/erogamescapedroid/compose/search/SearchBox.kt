package io.github.takusan23.erogamescapedroid.compose.search

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import io.github.takusan23.erogamescapedroid.R

/**
 * 検索ボックス
 * */
@Composable
fun SearchBox(
    searchText: String,
    onChangeSearchText: (String) -> Unit,
    onClickSearchButton: () -> Unit,
) {
    Card(
        modifier = Modifier.padding(5.dp),
        elevation = 10.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(modifier = Modifier.padding(start = 10.dp, end = 10.dp)) {
            TextField(
                modifier = Modifier.weight(1f),
                trailingIcon = {
                    IconButton(onClick = { onClickSearchButton() }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_baseline_search_24),
                            contentDescription = "search"
                        )
                    }
                },
                value = searchText,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { onClickSearchButton() }),
                onValueChange = { text -> onChangeSearchText(text) },
                placeholder = { Text(text = "ギャルゲ、エロゲのタイトルを入力") },
                maxLines = 1,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )
        }
    }
}
