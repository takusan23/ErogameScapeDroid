package io.github.takusan23.erogamescapedroid.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.takusan23.erogamescapedroid.network.GameData

/**
 * 気になってるリストのデータベースの定義
 *
 * めんどいので[GameData]を使い回す
 * */
@Entity(tableName = "favourite_game")
class FavouriteGameEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "_id") val primaryKey: Int = 0,
    id: Int,
    gamename: String,
    content: String,
    name: String,
    end_time_stamp: String,
    sale_url:String,
    furigana: String,
    sellday: String,
    brandname_id: Int,
    brandname: String,
    model: String,
    median: Int,
    average2: Int,
    stdev: Int,
    count2: Int,
    dmm: String,
    max2: Int,
    min2: Int,
    shoukai: String,
) : GameData(
    id,
    gamename,
    content,
    name,
    end_time_stamp,
    sale_url,
    furigana,
    sellday,
    brandname_id,
    brandname,
    model,
    median,
    average2,
    stdev,
    count2,
    dmm,
    max2,
    min2,
    shoukai
)