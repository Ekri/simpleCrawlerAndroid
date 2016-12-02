package com.maciega.bartosz.crawl.presenter

import android.content.Context
import android.util.Log

import com.maciega.bartosz.crawl.model.Url
import com.maciega.bartosz.crawl.storage.DbStorage
import com.maciega.bartosz.crawl.storage.Storage

import java.util.Collections

import io.realm.Realm
import io.realm.RealmResults
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.schedulers.Schedulers

/**
 * Created by bartoszmaciega on 24/10/16.
 */

class DataPresenter(private val context: Context, private val dataView: DataPresenter.DataView) {

    interface DataView {
        fun showUrls(urls: List<Url>)

    }

    private val storage: Storage

    init {
        this.storage = DbStorage(context)
    }


    fun loadData() {
        Observable.create(Observable.OnSubscribe<List<Url>> { subscriber ->
            subscriber.onNext(storage.getList(Url::class.java))
            subscriber.onCompleted()
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ urls -> dataView.showUrls(urls) }
                ) { throwable -> Log.d("error", throwable.message) }


    }


    fun submitQuery(query: String) {
        Observable.create(Observable.OnSubscribe<List<Url>> { subscriber -> subscriber.onNext(proceedQuery(query)) }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ urls -> dataView.showUrls(urls) }) { throwable -> Log.d("error", throwable.message) }


    }

    fun finishQuery() {
        loadData()
    }


    /**
     * temporary fast written method,
     * rethink with database model in future
     */
    private fun proceedQuery(query: String): List<Url> {
        val realm = Realm.getDefaultInstance()
        val results = realm.where(Url::class.java)
                .contains("url", query).findAll()
        return realm.copyFromRealm(results)
    }


}
