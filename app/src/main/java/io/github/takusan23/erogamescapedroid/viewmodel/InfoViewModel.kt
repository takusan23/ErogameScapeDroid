package io.github.takusan23.erogamescapedroid.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.github.takusan23.erogamescapedroid.network.ErogameScape
import io.github.takusan23.erogamescapedroid.network.GameData
import kotlinx.coroutines.launch

/**
 * エロゲ詳細画面で使っているViewModel
 *
 * @param gameId ゲームID
 * */
class InfoViewModel(application: Application, private val gameId: Int) : AndroidViewModel(application) {

    val context = application.applicationContext

    /**  */
    private val _gameDataLiveData = MutableLiveData<GameData>()

    /** ゲーム情報を送信するLiveData */
    val gameDataLiveData = _gameDataLiveData as LiveData<GameData>

    init {
        // データを取りに行く
        viewModelScope.launch {
            val data = ErogameScape.getGameInfoFromId(gameId)
            if (data != null) {
                _gameDataLiveData.postValue(data!!)
            }
        }
    }

}