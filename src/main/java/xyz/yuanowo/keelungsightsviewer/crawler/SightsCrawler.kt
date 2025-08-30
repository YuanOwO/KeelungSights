package xyz.yuanowo.keelungsightsviewer.crawler

import kotlinx.coroutines.*
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import xyz.yuanowo.keelungsightsviewer.model.Sight
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    println("Hello, SightsCrawler!")
    val crawler = SightsCrawler()

    for (district in listOf("七堵", "中山", "中正", "仁愛", "安樂", "信義", "暖暖", "大安", "大安鄉")) {
        val sights = crawler.fetchSightsListSync(district)
        println("Found ${sights.size} sights in $district")
    }
}

val pool = ConnectionPool(10, 5, TimeUnit.MINUTES)

val client = OkHttpClient.Builder()
    .connectionPool(pool)
    .connectTimeout(5, TimeUnit.SECONDS)
    .readTimeout(5, TimeUnit.SECONDS)
    .build()

class SightsCrawler {

    companion object {

        private const val BASE_URL = "https://www.travelking.com.tw"
        private const val UA =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/137.0.0.0"

    }

    private suspend fun fetchHtml(url: String): Document? = withContext(Dispatchers.IO) {
        try {
            println("Fetching URL: ${Companion.BASE_URL}$url")
            val request = Request.Builder()
                .url(Companion.BASE_URL + url)
                .header("User-Agent", Companion.UA)
                .build()
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful)
                    response.body?.let { Jsoup.parse(it.string()) }
                else
                    null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private suspend fun fetchHtmlWithRetry(url: String, retries: Int = 3, delayMillis: Long = 1000): Document? {
        var waitTime = delayMillis
        repeat(retries) { attempt ->
            try {
                return fetchHtml(url)
            } catch (ex: SocketTimeoutException) {
                println("Timeout when fetching $url (attempt ${attempt + 1}/$retries)")
                delay(waitTime)
                waitTime *= 2
            }
        }
        return null
    }

    fun fetchSightsListSync(district: String): List<Sight> = runBlocking { fetchSightsList(district) }

    fun fetchSightDetailsSync(url: String): Sight? = runBlocking { fetchSightDetails(url) }

    suspend fun fetchSightsList(district: String): List<Sight> = withContext(Dispatchers.IO) {
        val doc = fetchHtmlWithRetry("/tourguide/taiwan/keelungcity/")
        val ul = doc?.selectFirst("h4:contains(${district}) + ul") ?: return@withContext emptyList()
        ul.children().map { li ->
            async {
                val url = li.child(0).attr("href")
                fetchSightDetails(url)
            }
        }.awaitAll().filterNotNull()
    }

    suspend fun fetchSightDetails(url: String): Sight? = withContext(Dispatchers.IO) {
        val doc = fetchHtmlWithRetry(url) ?: return@withContext null
        val address = doc.selectFirst(".address a")
        val img = doc.selectFirst("#galleria .swiper-slide img");
        Sight(
            id = CrawlerUtils.getIDFromURL(url),
            name = doc.selectFirst("h1 > span")?.text(),
            city = doc.selectFirst(".breadcrumb .bc_li:nth-last-child(2) a")?.text(),
            district = doc.selectFirst(".breadcrumb .bc_last a")?.text(),
            category = doc.selectFirst(".point_type > span:nth-child(2)")?.text(),
            description = CrawlerUtils.getTextFromElement(doc.selectFirst(".text")),
            address = address?.text(),
            mapUrl = address?.attr("href"),
            photoUrl = img?.attr("data-src") ?: img?.attr("src"),
            sourceUrl = Companion.BASE_URL + url
        )
    }

}