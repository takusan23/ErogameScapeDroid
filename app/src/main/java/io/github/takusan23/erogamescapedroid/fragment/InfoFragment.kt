package io.github.takusan23.erogamescapedroid.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import io.github.takusan23.erogamescapedroid.compose.GameInfoScreen
import io.github.takusan23.erogamescapedroid.compose.theme.ErogameScapeDroidTheme
import io.github.takusan23.erogamescapedroid.viewmodel.InfoViewModel
import io.github.takusan23.erogamescapedroid.viewmodel.factory.InfoViewModelFactory

/**
 * ゲーム詳細画面
 *
 * ゲームのIDをgame_idとしてargumentに入れてください。
 * */
class InfoFragment : Fragment() {

    /** ViewModel */
    private val viewModel by lazy {
        val gameId = requireArguments().getInt("game_id")
        ViewModelProvider(this, InfoViewModelFactory(requireActivity().application, gameId)).get(InfoViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ErogameScapeDroidTheme {
                    Surface(color = MaterialTheme.colors.background) {
                        GameInfoScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }

}