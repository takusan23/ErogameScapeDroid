# ErogameScapeDroid
JetpackComposeとErogameScapeでできている。  
ErogameScapeの情報を見るだけ。

<p align="center">
<img src="https://imgur.com/n2hMIpV.png" width="200">
<img src="https://imgur.com/Nn9Gf4v.png" width="200">
<img src="https://imgur.com/rsYJCmA.png" width="200">
<img src="https://imgur.com/CKjtFmo.png" width="200">
</p>

# 使ったものなど

- Jetpack Compose
    - xmlとおさらば
    - Navigation Compose
        - 後述
- ViewModel / LiveData
    - SQLをPOSTするリクエストなどはここに書く
- OkHttp
    - HTTPクライアント
- Coroutine
    - コールバック関数しんどいので
- Jsoup
    - https://erogamescape.dyndns.org/~ap2/ero/toukei_kaiseki/sql_for_erogamer_form.php をスクレイピングするため

# Jetpack Compose で ViewModel
なんかViewModelが普通に使えた。
同じスコープ（Activity）だから駄目だと思ってたんだけど、ViewModelにKeyを付けることで同じスコープでも複数のViewModelが持てるらしい。

```kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Navigation Compose ライブラリで画面遷移を行う
            val navController = rememberNavController()

            ErogameScapeDroidTheme {
                Surface(color = MaterialTheme.colors.background) {

                    // ここを画面遷移
                    NavHost(navController = navController, startDestination = "search") {
                        composable("search") {
                            // 検索
                            SearchScreen(viewModel = viewModel()) {
                                // 画面をエロゲ詳細画面へ飛ばす
                                navController.navigate("game/${it.id}")
                            }
                        }
                        composable("game/{id}") { backStackEntry ->
                            // 詳細画面
                            val gameId = backStackEntry.arguments?.getString("id")?.toInt()!!
                            // ViewModelに引数を渡すためのFactoryもちゃんと対応している
                            GameInfoScreen(viewModel = viewModel(factory = InfoViewModelFactory(application, gameId))) {
                                // 前の画面（検索画面に戻る）
                                navController.popBackStack()
                            }
                        }
                    }

                }
            }
        }
    }

}
```


```kotlin
/**
 * ゲーム情報表示画面
 * */
@Composable
fun GameInfoScreen(viewModel: InfoViewModel, onBack: () -> Unit) {
    // 省略
}
```


```kotlin
/**
 * 検索画面
 * */
@Composable
fun SearchScreen(viewModel: SearchViewModel, onClickItem: (GameData) -> Unit) {
    // 省略
}
```

また、ViewModelに引数を渡すときに使う`ViewModelProvider.Factory`にもちゃんと対応しています。

```kotlin
/**
 * エロゲ詳細画面のViewModelへゲームIDを渡すためのFactory
 * */
class InfoViewModelFactory(val application: Application, val gameId: Int) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return InfoViewModel(application, gameId) as T
    }

}
```

`{id}`の部分は`backStackEntry#arguments#getString`で取れる。

```kotlin
// 省略
composable("game/{id}") { backStackEntry ->
    // 詳細画面
    val gameId = backStackEntry.arguments?.getString("id")?.toInt()!!
    // ViewModelに引数を渡すためのFactoryもちゃんと対応している
    GameInfoScreen(viewModel = viewModel(factory = InfoViewModelFactory(application, gameId))) {
        // 前の画面（検索画面に戻る）
        navController.popBackStack()
    }
}
```


# おまけ
使ったSQL。

```sql
 SELECT DISTINCT g.id,
    g.gamename,
    c.content,
    c.name,
    c.end_timestamp,
    c.url,
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
            l.url,
            l.end_timestamp
        FROM campaign_game g
            INNER JOIN campaignlist l ON g.campaign = l.id
        WHERE l.end_timestamp > now()
    ) c ON g.id = c.game
WHERE g.gamename LIKE '%アオナツライン%'
```

叩くとこんなのが返ってくる

| id    | gamename       | content | name | end_timestamp | furigana       | sellday    | brandname | brandname      | model | median | average2 | stdev | count2 | dmm     | max2 | min2 | shoukai                               |
|-------|----------------|---------|------|---------------|----------------|------------|-----------|----------------|-------|--------|----------|-------|--------|---------|------|------|---------------------------------------|
| 27418 | アオナツライン |         |      |               | アオナツライン | 2019-03-29 | 84        | 戯画           | PC    | 82     | 80       | 13    | 354    | eg_0012 | 100  | 0    | http://products.web-giga.com/aonatsu/ |
| 29203 | アオナツライン |         |      |               | アオナツライン | 2020-04-23 | 781       | エンターグラム | PS4   | 78     | 78       | 18    | 2      |         | 90   | 65   | http://www.entergram.co.jp/aonatsu/   |
| 29204 | アオナツライン |         |      |               | アオナツライン | 2020-04-23 | 781       | エンターグラム | PSV   | 87     | 87       | 3     | 3      |         | 90   | 85   | http://www.entergram.co.jp/aonatsu/   |

セール中ならこんな感じ

| id    | gamename                                       | content        | name                                    | end_timestamp       | furigana                                   | sellday    | brandname | brandname | model | median | average2 | stdev | count2 | dmm           | max2 | min2 | shoukai                                      |
|-------|------------------------------------------------|----------------|-----------------------------------------|---------------------|--------------------------------------------|------------|-----------|-----------|-------|--------|----------|-------|--------|---------------|------|------|----------------------------------------------|
| 26245 | さくら、もゆ。 -as the Night's, Reincarnation- | 8,330円 15%OFF | FAVORITE 新作記念ハッピーキャンペーン！ | 2021-06-17 23:59:00 | サクラモユアズザナイツレインカーネーション | 2019-01-31 | 2763      | FAVORITE  | PC    | 90     | 85       | 18    | 525    | favorite_0011 | 100  | 0    | http://www.favo-soft.jp/soft/product/sakura/ |
