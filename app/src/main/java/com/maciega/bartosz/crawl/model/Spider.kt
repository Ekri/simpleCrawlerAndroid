package com.maciega.bartosz.crawl.model

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast

import com.maciega.bartosz.crawl.storage.DbStorage
import com.maciega.bartosz.crawl.storage.DbTransactionListener
import com.maciega.bartosz.crawl.storage.Storage
import com.maciega.bartosz.crawl.storage.StorageTransactionResult
import io.realm.RealmObject

import java.util.ArrayList
import java.util.Arrays
import java.util.HashSet
import java.util.LinkedList

import rx.Observable

/**
 * Created by bartoszmaciega on 17/10/16.
 */

class Spider(private val context: Context, pagesToSearch: Int, private val crawlListener: CrawlListener) : DbTransactionListener {
    private var max_pages = 20
    private val visitedPages = HashSet<String>()
    private val pagesToVisit = LinkedList<String>()


    init {
        this.max_pages = if (pagesToSearch > 0) pagesToSearch else 20
    }


    @Synchronized private fun nextUrl(): String {
        var nextUrl: String = ""

        synchronized(pagesToVisit) {
            do {
                nextUrl = pagesToVisit.removeAt(0)
            } while (this.visitedPages.contains(nextUrl))
        }

        this.visitedPages.add(nextUrl)
        return nextUrl
    }


    fun search(url: String) {

        val spiderAsync = SpiderAsync()
        spiderAsync.execute(url)
    }

    override fun onResult(result: StorageTransactionResult<*>) {

    }


    internal inner class SpiderAsync : AsyncTask<String, String, Void>(), CrawlListener, DbTransactionListener {


        override fun doInBackground(vararg params: String): Void? {
            val url = params[0]
            pagesToVisit.add(url)

            while (visitedPages.size < max_pages && !pagesToVisit.isEmpty()) {
                try {
                    val currentUrl: String
                    val crawler = CrawlerItself(this)
                    currentUrl = nextUrl()
                    crawler.crawl(currentUrl)
                    publishProgress(currentUrl)

                    pagesToVisit.addAll(crawler.getLinks())
                } catch (e: Throwable) {
                    return null
                }

            }


            return null
        }

        override fun onProgressUpdate(vararg values: String) {
            super.onProgressUpdate(*values)
            crawlListener.onCrawled(values[0])
        }


        override fun onPostExecute(aVoid: Void) {
            super.onPostExecute(aVoid)
            val storage = DbStorage(context)
            storage.saveList<RealmObject>(UrlsMapper.convert(visitedPages))
            Toast.makeText(context, "searching finished", Toast.LENGTH_SHORT).show()
        }

        override fun onCrawled(pageUrl: String) {

        }

        override fun onResult(result: StorageTransactionResult<*>) {
            if (result.isSuccess) {
                Log.d("success", "success")
            }
        }
    }


}
