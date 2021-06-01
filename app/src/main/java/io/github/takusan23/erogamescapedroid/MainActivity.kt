package io.github.takusan23.erogamescapedroid

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.takusan23.erogamescapedroid.compose.AppBottomBar
import io.github.takusan23.erogamescapedroid.compose.GameInfoScreen
import io.github.takusan23.erogamescapedroid.compose.SearchScreen
import io.github.takusan23.erogamescapedroid.compose.favourite.FavouriteScreen
import io.github.takusan23.erogamescapedroid.compose.theme.ErogameScapeDroidTheme
import io.github.takusan23.erogamescapedroid.viewmodel.factory.InfoViewModelFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Navigation Compose ライブラリで画面遷移を行う
            val navController = rememberNavController()
            val navBackStackEntry = navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry.value?.destination?.route

            ErogameScapeDroidTheme {
                Scaffold(
                    bottomBar = {
                        AppBottomBar(
                            selectRoute = currentRoute,
                            onClick = { navController.navigate(it) }
                        )
                    }
                ) {
                    Surface(color = MaterialTheme.colors.background) {
                        // ここを画面遷移
                        NavHost(navController = navController, startDestination = "search") {
                            composable("search") {
                                // 検索
                                SearchScreen(viewModel = viewModel()) {
                                    // 画面をエロゲ詳細画面へ飛ばす
                                    navController.navigate("game/${it.id}")
                                }
                            }
                            // Next Generation FAVOURITE
                            composable("favourite") {
                                // 気になってるリスト
                                FavouriteScreen(viewModel()) {
                                    // 画面をエロゲ詳細画面へ飛ばす
                                    navController.navigate("game/${it.id}")
                                }
                            }
                            composable("game/{id}") { backStackEntry ->
                                // 詳細画面
                                val gameId = backStackEntry.arguments?.getString("id")?.toInt()!!
                                // ViewModelに引数を渡すためのFactoryもちゃんと対応している
                                GameInfoScreen(viewModel = viewModel(factory = InfoViewModelFactory(application, gameId))) {
                                    // 前の画面（検索画面に戻る）
                                    navController.popBackStack()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
