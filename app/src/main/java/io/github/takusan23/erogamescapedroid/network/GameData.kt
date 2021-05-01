package io.github.takusan23.erogamescapedroid.network

/**
 * ゲーム情報
 *
 * https://erogamescape.dyndns.org/~ap2/ero/toukei_kaiseki/sql_for_erogamer_tablelist.php
 *
 * @param id ID
 * @param gamename 名前
 * @param brandname_id ブランドのID
 * @param brandname ブランド名
 * @param content セール情報
 * @param end_time_stamp セール終了日時
 * @param model PCとかPSVとか
 * @param name セール名
 * @param furigana 名前のふりがな
 * @param sellday 発売日
 * @param median 得点中央値
 * @param average2 平均値
 * @param stdev 標準偏差
 * @param count2 得点データ数
 * @param dmm DMM（FANZA）のURLの一部
 * @param max2 最高得点
 * @param min2 最低得点
 * @param shoukai オフィシャルHPのURL
 * */
data class GameData(
    val id: Int,
    val gamename: String,
    val content: String,
    val name: String,
    val end_time_stamp: String,
    val furigana: String,
    val sellday: String,
    val brandname_id: Int,
    val brandname: String,
    val model: String,
    val median: Int,
    val average2: Int,
    val stdev: Int,
    val count2: Int,
    val dmm: String,
    val max2: Int,
    val min2: Int,
    val shoukai: String,
)