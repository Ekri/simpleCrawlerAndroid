package com.maciega.bartosz.crawl.model

/**
 * Created by bartoszmaciega on 17/10/16.
 */

interface CrawlListener {
    fun onCrawled(pageUrl: String)
}
