package io.github.takusan23.erogamescapedroid.compose

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import io.github.takusan23.erogamescapedroid.R
import io.github.takusan23.erogamescapedroid.viewmodel.InfoViewModel

/**
 * ゲーム情報表示画面
 * */
@Composable
fun GameInfoScreen(viewModel: InfoViewModel, onBack: () -> Unit) {
    val context = LocalContext.current
    // ゲーム情報
    val gameDataLiveData = viewModel.gameDataLiveData.observeAsState()
    // 気になっているDBに登録済みか
    val isAddedFavouriteGameDB = viewModel.isAddedFavouriteGameList.observeAsState(initial = false)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24), contentDescription = "Menu Btn")
                    }
                },
                elevation = 0.dp,
                backgroundColor = Color.Transparent,
            )
        }
    ) {
        if (gameDataLiveData.value != null) {
            val gameData = gameDataLiveData.value!!
            // 今回は一個だけ表示
            // パッケージ写真URL
            val imgUrl = "https://pics.dmm.co.jp/digital/pcgame/${gameData.dmm}/${gameData.dmm}ps.jpg"
            val bitmap = getInternetImage(url = imgUrl)
            Column {
                // 情報
                Row {
                    // 画像
                    if (bitmap != null) {
                        Image(
                            bitmap = bitmap,
                            contentDescription = "写真",
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .width(200.dp)
                                .height(200.dp)
                        )
                    }
                    // その他
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(text = gameData.gamename, fontSize = 25.sp)
                        Text(text = gameData.furigana)
                        Text(text = gameData.brandname, fontSize = 20.sp)
                        Text(text = "発売日 : ${gameData.sellday}")
                    }
                }
                // セール情報があれば
                if (gameData.content.isNotEmpty()) {
                    Card(
                        border = BorderStroke(1.dp, MaterialTheme.colors.primaryVariant),
                        elevation = 0.dp,
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth()
                        ) {
                            Text(text = "${gameData.name} (${gameData.end_time_stamp} まで)")
                            Text(text = gameData.content, fontSize = 20.sp)
                            // セール会場URL転送
                            TextButton(
                                modifier = Modifier.align(alignment = Alignment.End),
                                onClick = { context.startActivity(Intent(Intent.ACTION_VIEW, gameData.sale_url.toUri())) }
                            ) {
                                Icon(painter = painterResource(id = R.drawable.ic_open_in_browser_black_24dp), contentDescription = "sale")
                                Text(text = "セール会場へ")
                            }
                        }
                    }
                }
                Row {
                    // 気になっている登録ボタン
                    OutlinedButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(5.dp)
                            .fillMaxWidth(),
                        onClick = { if (isAddedFavouriteGameDB.value) viewModel.deleteFavouriteGame() else viewModel.addFavouriteGame(gameData) },
                    ) {
                        Icon(
                            painter = if (isAddedFavouriteGameDB.value) painterResource(id = R.drawable.ic_baseline_done_24) else painterResource(id = R.drawable.ic_baseline_post_add_24),
                            contentDescription = "favourite"
                        )
                        Text(text = if (isAddedFavouriteGameDB.value) "登録済み" else "気になるDBへ登録")
                    }
                    // DMMで購入するボタン
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .padding(5.dp)
                            .fillMaxWidth(),
                        onClick = {
                            context.startActivity(Intent(Intent.ACTION_VIEW, "https://dlsoft.dmm.co.jp/detail/${gameData.dmm}".toUri()))
                        },
                    ) {
                        Icon(painter = painterResource(id = R.drawable.ic_store_black_24dp), contentDescription = "shop")
                        Text(text = "DMMで購入する")
                    }
                }
                // 評価など
                Divider(Modifier.padding(5.dp))
                Row {
                    RankText(
                        modifier = Modifier
                            .weight(1f)
                            .padding(5.dp),
                        "最高得点",
                        gameData.max2.toString()
                    )
                    RankText(
                        modifier = Modifier
                            .weight(1f)
                            .padding(5.dp),
                        "得点中央値",
                        gameData.median.toString()
                    )
                    RankText(
                        modifier = Modifier
                            .weight(1f)
                            .padding(5.dp),
                        "最低得点",
                        gameData.min2.toString()
                    )
                }
                Row {
                    RankText(
                        modifier = Modifier
                            .weight(1f)
                            .padding(5.dp),
                        "得点データ数",
                        gameData.count2.toString()
                    )
                    RankText(
                        modifier = Modifier
                            .weight(1f)
                            .padding(5.dp),
                        "平均値",
                        gameData.average2.toString()
                    )
                    RankText(
                        modifier = Modifier
                            .weight(1f)
                            .padding(5.dp),
                        "標準偏差",
                        gameData.stdev.toString()
                    )
                }
                Divider(Modifier.padding(5.dp))
                // ErogameScapeで開く
                OutlinedButton(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    onClick = {
                        context.startActivity(Intent(Intent.ACTION_VIEW, "https://erogamescape.dyndns.org/~ap2/ero/toukei_kaiseki/game.php?game=${gameData.id}".toUri()))
                    },
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_open_in_browser_black_24dp), contentDescription = "shop")
                    Text(text = "ErogameScape -エロゲー批評空間- で開く")
                }
            }
        }
    }
}