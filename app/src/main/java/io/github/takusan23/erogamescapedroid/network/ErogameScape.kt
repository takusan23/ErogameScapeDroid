package io.github.takusan23.erogamescapedroid.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup

/**
 * https://erogamescape.dyndns.org/~ap2/ero/toukei_kaiseki/sql_for_erogamer_tablelist.php
 * */
object ErogameScape {

    private val okHttpClient = OkHttpClient()

    /**
     * ゲーム情報を取得する。ゲームのタイトルで
     *
     * 部分一致検索が利用できます。
     *
     * @param gameName ゲーム名。「彼女のセイイキ」など
     * @return 成功すれば[GameData]の配列（コンシューマー移植などがあるため）
     * */
    suspend fun getGameInfoFromName(gameName: String) = withContext(Dispatchers.Default) {
        // POST内容
        val sql = """ 
 SELECT DISTINCT g.id,
    g.gamename,
    c.content,
    c.name,
    c.end_timestamp,
    g.furigana,
    g.sellday,
    g.brandname,
    b.brandname,
    g.model,
    COALESCE(g.median, -1) AS median,
    COALESCE(g.average2, -1) AS average2,
    COALESCE(g.stdev, -1) AS stdev,
    COALESCE(g.count2, -1) AS count2,
    g.dmm,
    COALESCE(g.max2, -1) AS max2,
    COALESCE(g.min2, -1) AS min2,
    g.shoukai
FROM gamelist g
    INNER JOIN brandlist b ON g.brandname = b.id
    LEFT OUTER JOIN (
        SELECT g.content,
            l.name,
            g.game,
            l.end_timestamp
        FROM campaign_game g
            INNER JOIN campaignlist l ON g.campaign = l.id
        WHERE l.end_timestamp > now()
    ) c ON g.id = c.game
WHERE g.gamename LIKE '%${gameName}%'
 """
        val formData = FormBody.Builder().apply {
            add("sql", sql)
        }.build()
        val request = Request.Builder().apply {
            url("https://erogamescape.dyndns.org/~ap2/ero/toukei_kaiseki/sql_for_erogamer_form.php")
            post(formData)
        }.build()
        // POSTリクエストを飛ばす
        val response = okHttpClient.newCall(request).execute()
        // スクレイピング
        return@withContext if (response.isSuccessful) {
            parseHTML(response.body!!.string())
        } else {
            null
        }
    }

    /**
     * ゲーム情報を取得する。IDで
     *
     * @param gameName ゲーム名。「彼女のセイイキ」など
     * @return 成功すれば[GameData]
     * */
    suspend fun getGameInfoFromId(gameId: Int) = withContext(Dispatchers.Default) {
        // POST内容
        val sql = """ 
 SELECT DISTINCT g.id,
    g.gamename,
    c.content,
    c.name,
    c.end_timestamp,
    g.furigana,
    g.sellday,
    g.brandname,
    b.brandname,
    g.model,
    COALESCE(g.median, -1) AS median,
    COALESCE(g.average2, -1) AS average2,
    COALESCE(g.stdev, -1) AS stdev,
    COALESCE(g.count2, -1) AS count2,
    g.dmm,
    COALESCE(g.max2, -1) AS max2,
    COALESCE(g.min2, -1) AS min2,
    g.shoukai
FROM gamelist g
    INNER JOIN brandlist b ON g.brandname = b.id
    LEFT OUTER JOIN (
        SELECT g.content,
            l.name,
            g.game,
            l.end_timestamp
        FROM campaign_game g
            INNER JOIN campaignlist l ON g.campaign = l.id
        WHERE l.end_timestamp > now()
    ) c ON g.id = c.game
WHERE g.id = '$gameId'
 """
        val formData = FormBody.Builder().apply {
            add("sql", sql)
        }.build()
        val request = Request.Builder().apply {
            url("https://erogamescape.dyndns.org/~ap2/ero/toukei_kaiseki/sql_for_erogamer_form.php")
            post(formData)
        }.build()
        // POSTリクエストを飛ばす
        val response = okHttpClient.newCall(request).execute()
        // スクレイピング
        return@withContext if (response.isSuccessful) {
            parseHTML(response.body!!.string())[0]
        } else {
            null
        }
    }

    /** スクレイピングをする */
    private fun parseHTML(html: String): ArrayList<GameData> {
        // ゲーム情報配列
        val gameInfoList = arrayListOf<GameData>()
        val document = Jsoup.parse(html)
        val trElementList = document.getElementsByTag("tr")
        repeat(trElementList.size - 1) {
            // テーブル一行目はいらない
            val index = it + 1
            val trElement = trElementList[index]
            val tdList = trElement.getElementsByTag("td")
            val id = tdList[0].text().toInt()
            val gamename = tdList[1].text()
            val content = tdList[2].text()
            val name = tdList[3].text()
            val end_time_stamp = tdList[4].text()
            val furigana = tdList[5].text()
            val sellday = tdList[6].text()
            val brandname_id = tdList[7].text().toInt()
            val brandname = tdList[8].text()
            val model = tdList[9].text()
            val median = tdList[10].text().toInt()
            val average2 = tdList[11].text().toInt()
            val stdev = tdList[12].text().toInt()
            val count2 = tdList[13].text().toInt()
            val dmm = tdList[14].text()
            val max2 = tdList[15].text().toInt()
            val min2 = tdList[16].text().toInt()
            val shoukai = tdList[17].text()
            // データクラスへ
            val gameData = GameData(
                id,
                gamename,
                content,
                name,
                end_time_stamp,
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
                shoukai,
            )
            gameInfoList.add(gameData)
        }
        return gameInfoList
    }

}