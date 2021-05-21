package io.github.takusan23.erogamescapedroid

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import io.github.takusan23.erogamescapedroid.compose.GameInfoScreen
import io.github.takusan23.erogamescapedroid.compose.SearchScreen
import io.github.takusan23.erogamescapedroid.compose.theme.ErogameScapeDroidTheme
import io.github.takusan23.erogamescapedroid.viewmodel.factory.InfoViewModelFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Navigation Compose ライブラリで画面遷移を行う
            val navController = rememberNavController()

            ErogameScapeDroidTheme {
                Surface(color = MaterialTheme.colors.background) {

                    // ここを画面遷移
                    // Navigation Composeライブラリを使うと一つのActivityでも各ベージにそれぞれViewModelが持てる
                    NavHost(navController = navController, startDestination = "search") {
                        composable("search") {
                            // 検索
                            SearchScreen(viewModel = viewModel()) {
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
