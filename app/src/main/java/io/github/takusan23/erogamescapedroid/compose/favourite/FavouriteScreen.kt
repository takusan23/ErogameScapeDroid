package io.github.takusan23.erogamescapedroid.compose.favourite

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import io.github.takusan23.erogamescapedroid.compose.GameList
import io.github.takusan23.erogamescapedroid.network.GameData
import io.github.takusan23.erogamescapedroid.viewmodel.FavouriteViewModel
import io.github.takusan23.erogamescapedroid.viewmodel.InfoViewModel

/**
 * 気になっているリスト
 *
 * @param viewModel 気になっているリストで使うViewModel
 * */
@Composable
fun FavouriteScreen(viewModel: FavouriteViewModel, onClickGame: (GameData) -> Unit) {
    // ゲーム一覧
    val gameList = viewModel.favouriteGameListFlow.collectAsState(initial = listOf())

    GameList(
        gameList = gameList.value,
        onClick = { onClickGame(it) }
    )
}