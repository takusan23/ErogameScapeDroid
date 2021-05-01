package io.github.takusan23.erogamescapedroid.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import io.github.takusan23.erogamescapedroid.MainActivity
import io.github.takusan23.erogamescapedroid.compose.GameListScreen
import io.github.takusan23.erogamescapedroid.compose.theme.ErogameScapeDroidTheme
import io.github.takusan23.erogamescapedroid.viewmodel.SearchViewModel

/**
 * 検索結果Fragment
 * */
class SearchFragment : Fragment() {

    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ErogameScapeDroidTheme {
                    Surface(color = MaterialTheme.colors.background) {
                        // ゲーム詳細画面
                        GameListScreen(
                            viewModel = viewModel,
                            onClickItem = { gameData ->
                                // Fragment切り替え
                                (requireActivity() as? MainActivity)?.setFragment(InfoFragment().apply {
                                    arguments = Bundle().apply {
                                        putInt("game_id", gameData.id)
                                    }
                                })
                            }
                        )
                    }
                }
            }
        }
    }

}