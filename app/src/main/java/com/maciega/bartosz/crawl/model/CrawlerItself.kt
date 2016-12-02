package com.maciega.bartosz.crawl.model

import org.jsoup.Jsoup
import java.io.IOException
import java.util.*

/**
 * Created by bartoszmaciega on 17/10/16.
 */

class CrawlerItself(private val crawlListener: CrawlListener) {
    private val links = ArrayList<String>()


    fun crawl(nextUrl: String?): Boolean {
        if (nextUrl == null || nextUrl.isEmpty()) {
            return false
        }
        val connection = Jsoup.connect(nextUrl).userAgent(USER_AGENT)
        try {
            val htmlDocument = connection.get() ?: return false
            if (connection.response().statusCode() == 200) {
                if (!connection.response().contentType().contains("text/html")) {
                    return false
                }
                val linksOnPage = htmlDocument.select("a[href]")

                for (element in linksOnPage) {
                    links.add(element.absUrl("href"))
                }

                return true

            }

            return false

        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }

    }

    fun getLinks(): List<String> {
        return links
    }

    companion object {
        private val USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1"
    }

}
