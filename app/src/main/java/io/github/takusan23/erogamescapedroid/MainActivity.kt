package io.github.takusan23.erogamescapedroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import io.github.takusan23.erogamescapedroid.ui.GameInfoScreen
import io.github.takusan23.erogamescapedroid.ui.theme.ErogameScapeDroidTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ErogameScapeDroidTheme {
                Surface(color = MaterialTheme.colors.background) {
                    // ゲーム詳細画面
                    GameInfoScreen(viewModel = viewModel)
                }
            }
        }
    }
}
