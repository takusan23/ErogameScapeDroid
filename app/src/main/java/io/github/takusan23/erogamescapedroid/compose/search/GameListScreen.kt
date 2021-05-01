package io.github.takusan23.erogamescapedroid.compose

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import io.github.takusan23.erogamescapedroid.compose.search.SearchBox
import io.github.takusan23.erogamescapedroid.network.GameData
import io.github.takusan23.erogamescapedroid.viewmodel.SearchViewModel

/**
 * 検索画面
 * */
@Composable
fun GameListScreen(viewModel: SearchViewModel, onClickItem: (GameData) -> Unit) {
    // 検索ワード
    val searchText = remember { mutableStateOf("") }
    // ゲーム情報
    val gameInfoList = viewModel.gameInfoLiveDataList.observeAsState()

    Scaffold(
        topBar = {
            SearchBox(
                searchText = searchText.value,
                onChangeSearchText = { searchText.value = it },
                onClickSearchButton = { viewModel.getGameInfo(searchText.value) }
            )
        }
    ) {
        if (gameInfoList.value != null) {
            // 一覧
            GameList(
                gameList = gameInfoList.value!!,
                onClick = { onClickItem(it) }
            )
        }
    }
}