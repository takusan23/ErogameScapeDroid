# ErogameScapeDroid
JetpackComposeとErogameScapeでできている。  
ErogameScapeの情報を見るだけ。

<img src="https://imgur.com/n2hMIpV.png" width="300">

# おまけ
使ったSQL。

```sql
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
WHERE g.gamename = 'アオナツライン'
```
