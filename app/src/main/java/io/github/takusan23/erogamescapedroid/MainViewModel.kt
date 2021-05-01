package io.github.takusan23.erogamescapedroid

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * [MainActivity]で使うViewModel
 * */
class MainViewModel(application: Application) : AndroidViewModel(application) {

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
            val data = ErogameScape.getGameInfo(gameName)
            _gameInfoLiveDataList.postValue(data)
        }
    }

}