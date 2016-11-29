package com.maciega.bartosz.crawl.model

/**
 * Created by Bartosz on 2016-10-18.
 */

interface SpiderServantActions {
    fun saveUrl(url: Url)
    fun saveUrls(list: List<Url>)
    fun deleteUrl(url: Url)
    fun deleteAll(url: Url)
    fun clearDb()
    fun getUrls()
    fun getUrl(primaryKey: String)
}
