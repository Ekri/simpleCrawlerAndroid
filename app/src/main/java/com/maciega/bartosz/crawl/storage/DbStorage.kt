package com.maciega.bartosz.crawl.storage

import android.content.Context

import com.maciega.bartosz.crawl.model.Url
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor

import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.RealmResults
import rx.Observable
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.schedulers.Schedulers

import android.icu.lang.UCharacter.GraphemeClusterBreak.T

/**
 * Created by Bartosz on 2016-10-18.
 */

class DbStorage(private val context: Context) : UrlStorage {

    override fun save(`object`: RealmObject) {
        val realm = Realm.getDefaultInstance()
        try {
            realm.beginTransaction()
            realm.insertOrUpdate(`object`)
            realm.commitTransaction()
        } catch (e: Throwable) {
            e.printStackTrace()
            realm.cancelTransaction()
        }

        realm.close()

    }

    override fun <T : RealmObject> saveList(objects: List<RealmObject>) {
        val realm = Realm.getDefaultInstance()
        try {
            realm.beginTransaction()
            realm.insertOrUpdate(objects)
            realm.commitTransaction()
        } catch (e: Throwable) {
            e.printStackTrace()
            realm.cancelTransaction()
        }

        realm.close()
    }


    override fun delete(`object`: RealmObject) {
        val realm = Realm.getDefaultInstance()
        try {
            realm.beginTransaction()
            realm.delete(`object`.javaClass)
            realm.commitTransaction()

        } catch (e: Throwable) {
            e.printStackTrace()
            realm.cancelTransaction()
        }

        realm.close()

    }

    override fun deleteAllObjects(`object`: RealmObject) {
        val realm = Realm.getDefaultInstance()
        try {
            realm.beginTransaction()
            realm.where(`object`.javaClass).findAll().deleteAllFromRealm()
            realm.commitTransaction()

        } catch (e: Throwable) {
            e.printStackTrace()
            realm.cancelTransaction()
        }

        realm.close()
    }

    override fun clearDatabase() {
        val realm = Realm.getDefaultInstance()
        try {
            realm.beginTransaction()
            realm.deleteAll()
            realm.commitTransaction()
        } catch (e: Throwable) {
            e.printStackTrace()
            realm.cancelTransaction()
        }

        realm.close()
    }

    override fun <T : RealmObject> getList(classToGet: Class<T>): List<T> {
        val realm = Realm.getDefaultInstance()
        val result = realm.where(classToGet).findAll()
        val copy = realm.copyFromRealm(result)
        return copy
    }

    override fun get(primaryKey: String): Url {
        val realm = Realm.getDefaultInstance()
        val result = realm.where(Url::class.java).equalTo("url", primaryKey).findFirst()
        return result
    }

}
