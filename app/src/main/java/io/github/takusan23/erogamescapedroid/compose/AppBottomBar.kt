package io.github.takusan23.erogamescapedroid.compose

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import io.github.takusan23.erogamescapedroid.R

/**
 * BottomNavigationBar
 *
 * @param selectRoute 選択中のルート
 * @param onClick メニュー押したら呼ばれる。
 * */
@Composable
fun AppBottomBar(
    selectRoute: String?,
    onClick: (route: String) -> Unit
) {
    BottomNavigation {
        BottomNavigationItem(
            selected = selectRoute == "search",
            onClick = { onClick("search") },
            label = { Text(text = "検索") },
            icon = { Icon(painter = painterResource(id = R.drawable.ic_baseline_search_24), contentDescription = "検索") }
        )
        // Next Generation FAVOURITE
        BottomNavigationItem(
            selected = selectRoute == "favourite",
            onClick = { onClick("favourite") },
            label = { Text(text = "気になる作品") },
            icon = { Icon(painter = painterResource(id = R.drawable.ic_baseline_favorite_border_24), contentDescription = "気になってる") }
        )
    }
}