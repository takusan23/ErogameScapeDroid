package io.github.takusan23.erogamescapedroid.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.github.takusan23.erogamescapedroid.database.database.FavouriteGameDB
import io.github.takusan23.erogamescapedroid.database.entity.FavouriteGameEntity
import io.github.takusan23.erogamescapedroid.network.ErogameScape
import io.github.takusan23.erogamescapedroid.network.GameData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

/**
 * エロゲ詳細画面で使っているViewModel
 *
 * @param gameId ゲームID
 * */
class InfoViewModel(application: Application, private val gameId: Int) : AndroidViewModel(application) {

    val context = application.applicationContext

    /** シングルトンなデータベースのインスタンス */
    private val favouriteGameDB = FavouriteGameDB.getInstance(context)

    private val _gameDataLiveData = MutableLiveData<GameData>()

    /** ゲーム情報を送信するLiveData */
    val gameDataLiveData = _gameDataLiveData as LiveData<GameData>

    private val _isAddedFavouriteGameList = MutableLiveData(false)

    /** 指定したゲームが気になっているリストに登録されているか */
    val isAddedFavouriteGameList = _isAddedFavouriteGameList as LiveData<Boolean>


    init {
        // データを取りに行く
        viewModelScope.launch {
            val data = ErogameScape.getGameInfoFromId(gameId)
            if (data != null) {
                _gameDataLiveData.postValue(data!!)
            }
        }
        // 気になっているゲームに登録しているか。Flowで検知
        viewModelScope.launch {
            favouriteGameDB
                .gameDao()
                .getExistsFlowEdition(gameId)
                .collect { isExists ->
                    println(isExists)
                    _isAddedFavouriteGameList.postValue(isExists)
                }
        }
    }

    /** 気になるゲームとして登録する */
    fun addFavouriteGame(gameData: GameData) {
        val favouriteGameEntity = FavouriteGameEntity(
            primaryKey = 0,
            id = gameData.id,
            gamename = gameData.gamename,
            content = gameData.content,
            name = gameData.name,
            end_time_stamp = gameData.end_time_stamp,
            sale_url = gameData.sale_url,
            furigana = gameData.furigana,
            sellday = gameData.sellday,
            brandname_id = gameData.brandname_id,
            brandname = gameData.brandname,
            model = gameData.model,
            median = gameData.median,
            average2 = gameData.average2,
            stdev = gameData.stdev,
            count2 = gameData.count2,
            dmm = gameData.dmm,
            max2 = gameData.max2,
            min2 = gameData.min2,
            shoukai = gameData.shoukai,
        )
        viewModelScope.launch(Dispatchers.IO) { favouriteGameDB.gameDao().insertAll(favouriteGameEntity) }
    }

    /** 気になるゲームから削除する */
    fun deleteFavouriteGame() {
        viewModelScope.launch(Dispatchers.IO) { favouriteGameDB.gameDao().deleteFromErogameScapeGameId(gameId) }
    }

}