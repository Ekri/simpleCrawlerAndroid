package com.maciega.bartosz.crawl.model

import java.util.ArrayList

import io.realm.RealmObject

/**
 * Created by Bartosz on 2016-10-18.
 */

object UrlsMapper {

    fun convert(list: List<String>): List<Url> {
        val urlsList = ArrayList<Url>()
        for (urlString in list) {
            val url = Url()
            url.url = urlString
            urlsList.add(url)
        }

        return urlsList
    }

    fun convert(urls: Set<String>): List<RealmObject> {
        val urlList = ArrayList<RealmObject>()
        for (url in urls) {
            val urlInstance = Url()
            urlInstance.url = url
            urlList.add(urlInstance)
        }
        return urlList
    }

}
