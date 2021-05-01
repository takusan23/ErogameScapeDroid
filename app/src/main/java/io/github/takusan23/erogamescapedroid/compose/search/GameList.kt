package io.github.takusan23.erogamescapedroid.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.takusan23.erogamescapedroid.network.GameData

/**
 * ゲーム一覧を表示する
 * @param gameList 表示するゲーム情報の配列
 * @param onClick 押したとき
 * */
@Composable
fun GameList(gameList: List<GameData>, onClick: (GameData) -> Unit) {
    LazyColumn {
        items(gameList) { gameData ->
            GameListItem(gameData = gameData, onClick = { onClick(it) })
            Divider()
        }
    }
}

/**
 * ゲーム一覧の各レイアウト
 * @param gameData ゲーム情報
 * @param onClick 押したとき
 * */
@Composable
fun GameListItem(gameData: GameData, onClick: (GameData) -> Unit) {
    val imgUrl = "https://pics.dmm.co.jp/digital/pcgame/${gameData.dmm}/${gameData.dmm}ps.jpg"
    val image = getInternetImage(url = imgUrl)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = { onClick(gameData) }
            )
    ) {
        if (image != null) {
            Image(
                bitmap = image,
                contentDescription = null,
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
            )
        }
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = gameData.gamename, fontSize = 20.sp)
            Text(text = gameData.brandname)
        }
    }
}