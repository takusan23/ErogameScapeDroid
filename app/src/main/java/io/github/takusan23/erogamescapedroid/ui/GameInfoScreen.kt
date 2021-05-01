package io.github.takusan23.erogamescapedroid.ui

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import io.github.takusan23.erogamescapedroid.MainViewModel
import io.github.takusan23.erogamescapedroid.R

/**
 * ゲーム情報表示画面
 * */
@Composable
fun GameInfoScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    // 検索ワード
    val searchText = remember { mutableStateOf("") }
    // ゲーム情報
    val gameInfoList = viewModel.gameInfoLiveDataList.observeAsState()

    Scaffold(
        topBar = {
            SearchBox(searchText = searchText.value, onChangeSearchText = { searchText.value = it }) {
                // 検索ボタン押したとき
                viewModel.getGameInfo(searchText.value)
            }
        }
    ) {
        if (gameInfoList.value != null) {
            // 今回は一個だけ表示
            val gameInfo = gameInfoList.value!![0]
            // パッケージ写真URL
            val imgUrl = "https://pics.dmm.co.jp/digital/pcgame/${gameInfo.dmm}/${gameInfo.dmm}ps.jpg"

            Column {
                // 情報
                Row {
                    InternetImage(url = imgUrl)
                    Column {
                        Text(text = gameInfo.gamename, fontSize = 25.sp)
                        Text(text = gameInfo.furigana)
                        Text(text = gameInfo.brandname, fontSize = 20.sp)
                        Text(text = "発売日\n${gameInfo.sellday}")
                    }
                }
                // セール情報があれば
                if (gameInfo.content.isNotEmpty()) {
                    Card(
                        border = BorderStroke(1.dp, MaterialTheme.colors.primaryVariant),
                        elevation = 0.dp,
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(text = "${gameInfo.name} (${gameInfo.end_time_stamp} まで)")
                            Text(text = gameInfo.content, fontSize = 20.sp)
                        }
                    }
                }
                // DMMで購入するボタン
                Button(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    onClick = {
                        context.startActivity(Intent(Intent.ACTION_VIEW, "https://dlsoft.dmm.co.jp/detail/${gameInfo.dmm}".toUri()))
                    },
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_store_black_24dp), contentDescription = "shop")
                    Text(text = "DMMで購入する")
                }
                // 評価など
                Divider()
                Row {
                    RankText(
                        modifier = Modifier
                            .weight(1f)
                            .padding(5.dp),
                        "最高得点",
                        gameInfo.max2.toString()
                    )
                    RankText(
                        modifier = Modifier
                            .weight(1f)
                            .padding(5.dp),
                        "得点中央値",
                        gameInfo.median.toString()
                    )
                    RankText(
                        modifier = Modifier
                            .weight(1f)
                            .padding(5.dp),
                        "最低得点",
                        gameInfo.min2.toString()
                    )
                }
                Row {
                    RankText(
                        modifier = Modifier
                            .weight(1f)
                            .padding(5.dp),
                        "得点データ数",
                        gameInfo.count2.toString()
                    )
                    RankText(
                        modifier = Modifier
                            .weight(1f)
                            .padding(5.dp),
                        "平均値",
                        gameInfo.average2.toString()
                    )
                    RankText(
                        modifier = Modifier
                            .weight(1f)
                            .padding(5.dp),
                        "標準偏差",
                        gameInfo.stdev.toString()
                    )
                }
                Divider()
                // ErogameScapeで開く
                OutlinedButton(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    onClick = {
                        context.startActivity(Intent(Intent.ACTION_VIEW, "https://erogamescape.dyndns.org/~ap2/ero/toukei_kaiseki/game.php?game=${gameInfo.id}".toUri()))
                    },
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_open_in_browser_black_24dp), contentDescription = "shop")
                    Text(text = "ErogameScape -エロゲー批評空間- で開く")
                }
            }
        }
    }
}