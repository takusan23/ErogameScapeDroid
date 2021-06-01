package io.github.takusan23.erogamescapedroid.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.github.takusan23.erogamescapedroid.database.database.FavouriteGameDB

/**
 * 気になっているゲーム一覧画面で利用するViewModel
 * */
class FavouriteViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext

    /** シングルトンなデータベースのインスタンス */
    private val favouriteGameDB = FavouriteGameDB.getInstance(context)

    /** データベースの内容をFlowで受け取る*/
    val favouriteGameListFlow = favouriteGameDB.gameDao().getAllFlowEdition()

}