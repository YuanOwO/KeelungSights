package xyz.yuanowo.keelungsightsviewer.crawler

import kotlinx.coroutines.*
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.slf4j.LoggerFactory
import xyz.yuanowo.keelungsightsviewer.model.Sight
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

fun main() {
    println("Hello, SightsCrawler!")
    val crawler = SightsCrawler()

    val sights = crawler.fetchSightsListSync()
    println("Found ${sights.size} sights in total")

//    for (district in listOf("七堵", "中山", "中正", "仁愛", "安樂", "信義", "暖暖", "大安", "大安鄉")) {
//        val sights = crawler.fetchSightsListSync(district)
//        println("Found ${sights.size} sights in $district")
//    }
}

val pool = ConnectionPool(10, 3, TimeUnit.MINUTES)

val client = OkHttpClient.Builder()
    .connectionPool(pool)
    .connectTimeout(5, TimeUnit.SECONDS)
    .readTimeout(10, TimeUnit.SECONDS)
    .build()

@OptIn(ExperimentalCoroutinesApi::class)
val limitedIO = Dispatchers.IO.limitedParallelism(10)

class SightsCrawler {

    companion object {

        private const val BASE_URL = "https://www.travelking.com.tw"
        private const val UA =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/137.0.0.0"
        private val log = LoggerFactory.getLogger(SightsCrawler::class.java)

    }

    private suspend fun fetchHtml(url: String): Document? = withContext(limitedIO) {
        log.info("Fetching URL {}", url)
        val request = Request.Builder().url(BASE_URL + url).header("User-Agent", UA).build()
        client.newCall(request).execute().use { response ->
            log.info("Fetched URL {} (Status: {})", url, response.code)
            if (response.isSuccessful) response.body?.let { Jsoup.parse(it.string()) }
            else null
        }
    }

    private suspend fun fetchHtmlWithRetry(url: String, retries: Int = 3, delayMillis: Long = 1000): Document? {
        var waitTime = delayMillis
        repeat(retries) { attempt ->
            try {
                return fetchHtml(url)
            } catch (ex: SocketTimeoutException) {
                if (attempt == retries - 1) {
                    log.error("Failed to fetch {} after {} attempts", url, retries, ex)
                } else {
                    log.warn("Timeout when fetching {} (attempt {}/{})", url, attempt + 1, retries)
                }
                delay(waitTime)
                waitTime *= 2
            }
        }
        return null
    }

    fun fetchSightsListSync(district: String? = null): List<Sight> = runBlocking { fetchSightsList(district) }

    fun fetchSightDetailsSync(url: String): Sight? = runBlocking { fetchSightDetails(url) }

    suspend fun fetchSightsList(district: String? = null): List<Sight> = withContext(limitedIO) {
        val doc = fetchHtmlWithRetry("/tourguide/taiwan/keelungcity/") ?: return@withContext emptyList()

        val cssQuery = if (district == null) "#guide-point ul a" else "h4:contains(${district}) + ul a"
        val links = doc.select(cssQuery)

        log.debug("Found {} sight links", links.size)
        for (link in links) log.debug(" - [{}]({})", link.text(), link?.attr("href"))

        links.map { li ->
            async {
                val url = li.attr("href")
                fetchSightDetails(url)
            }
        }.awaitAll().filterNotNull()

    }

    suspend fun fetchSightDetails(url: String): Sight? = withContext(limitedIO) {
        val doc = fetchHtmlWithRetry(url) ?: return@withContext null

        val address = doc.selectFirst(".address a")
        val img = doc.selectFirst("#galleria .swiper-slide img")

        Sight(
            sightId = CrawlerUtils.getIDFromURL(url),
            name = doc.selectFirst("h1 > span")?.text(),
            city = doc.selectFirst(".breadcrumb .bc_li:nth-last-child(2) a")?.text(),
            district = doc.selectFirst(".breadcrumb .bc_last a")?.text(),
            category = doc.selectFirst(".point_type > span:nth-child(2)")?.text(),
            description = CrawlerUtils.getTextFromElement(doc.selectFirst(".text")),
            address = address?.text(),
            mapUrl = address?.attr("href"),
            photoUrl = img?.attr("data-src") ?: img?.attr("src"),
            sourceUrl = BASE_URL + url
        )
    }

}
