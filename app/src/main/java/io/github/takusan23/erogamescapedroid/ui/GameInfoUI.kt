package io.github.takusan23.erogamescapedroid.ui

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
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

/**
 * インターネット上の画像を表示する
 * */
@Composable
fun InternetImage(url: String) {
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    Glide.with(LocalContext.current).asBitmap().load(url).into(object : CustomTarget<Bitmap>() {
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            bitmap.value = resource
        }

        override fun onLoadCleared(placeholder: Drawable?) {

        }
    })
    if (bitmap.value != null) {
        Image(
            bitmap = bitmap.value!!.asImageBitmap(),
            contentDescription = "写真",
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .width(200.dp)
                .height(200.dp)
        )
    }
}

/**
 * 二段のText。上の段のほうが文字が大きい
 * */
@Composable
fun RankText(modifier: Modifier, title: String, value: String) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontSize = 20.sp,
            fontWeight = FontWeight.W500,
            textAlign = TextAlign.Center,
        )
        Text(text = title)
    }
}
