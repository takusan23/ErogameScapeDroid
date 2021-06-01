package io.github.takusan23.erogamescapedroid.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.github.takusan23.erogamescapedroid.database.entity.FavouriteGameEntity
import kotlinx.coroutines.flow.Flow

/**
 * 気になっているゲームDBへアクセスする関数
 * */
@Dao
interface FavouriteGameDao {

    /** 全件取得 */
    @Query("SELECT * FROM favourite_game")
    fun getAll(): List<FavouriteGameEntity>

    /** 追加 */
    @Insert
    fun insertAll(favouriteGameEntity: FavouriteGameEntity)

    /** 削除 */
    @Delete
    fun delete(favouriteGameEntity: FavouriteGameEntity)

    /** 指定したデータベースのIDのデータを返す */
    @Query("SELECT * FROM favourite_game WHERE _id = :id")
    fun getDataFromId(id: Int): FavouriteGameEntity

    /** 指定したErogameScapeのIDのデータを返す */
    @Query("SELECT * FROM favourite_game WHERE id = :gameId")
    fun getFromErogameScapeGameId(gameId: Int): FavouriteGameEntity

    /** 指定したErogameScapeのIDでデータが存在するか */
    @Query("SELECT EXISTS(SELECT * FROM favourite_game WHERE id = :gameId)")
    fun existsFromErogameScapeGameId(gameId: Int): Boolean

    /** 指定したErogameScapeのIDのデータを削除 */
    @Query("DELETE FROM favourite_game WHERE id = :gameId")
    fun deleteFromErogameScapeGameId(gameId: Int)

    /** データベースの変更をFlowで送信 */
    @Query("SELECT * FROM favourite_game")
    fun getAllFlowEdition(): Flow<List<FavouriteGameEntity>>

    /** [existsFromErogameScapeGameId]のリアルタイム版 */
    @Query("SELECT EXISTS(SELECT * FROM favourite_game WHERE id = :gameId)")
    fun getExistsFlowEdition(gameId: Int): Flow<Boolean>

}