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
 * [io.github.takusan23.erogamescapedroid.SearchFragment]で使うViewModel
 * */
class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext

    private val _gameInfoLiveDataList = MutableLiveData<List<GameData>>()

    /** 結果を送信するLiveData */
    val gameInfoLiveDataList = _gameInfoLiveDataList as LiveData<List<GameData>>

    /**
     * ゲームの情報を取得する関数
     * @param gameName ゲーム名。「彼女のセイイキ」など
     * */
    fun getGameInfo(gameName: String) {
        viewModelScope.launch {
            val data = ErogameScape.getGameInfoFromName(gameName)
            if (data != null) {
                _gameInfoLiveDataList.postValue(data!!)
            }
        }
    }

}