package com.maciega.bartosz.crawl.model;

import android.content.Context;

import com.maciega.bartosz.crawl.storage.DbStorage;
import com.maciega.bartosz.crawl.storage.DbTransactionListener;
import com.maciega.bartosz.crawl.storage.DbUrlHelper;
import com.maciega.bartosz.crawl.storage.StorageTransactionResult;
import com.maciega.bartosz.crawl.storage.UrlStorage;

import java.util.List;

import io.realm.RealmObject;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Bartosz on 2016-10-18.
 */

public class SpiderDbServant implements SpiderServantActions {
    private final Scheduler IO_SCHEDULER = Schedulers.io();
    private final Scheduler MAIN_SCHEDULER = AndroidSchedulers.mainThread();

    private DbTransactionListener dbListener;
    private DbUrlHelper urlDbHelper;

    public static SpiderServantActions get(Context context, DbTransactionListener dbListener) {
        return new SpiderDbServant(context, dbListener);
    }


    private SpiderDbServant(Context context, DbTransactionListener dbListener) {
        this.dbListener = dbListener;
        UrlStorage urlStorage = new DbStorage(context, dbListener);
        urlDbHelper = new DbUrlHelper(context, urlStorage);
    }

    @Override
    public void saveUrl(final Url url) {
        Observable.just(true)
                .observeOn(MAIN_SCHEDULER)
                .subscribeOn(IO_SCHEDULER)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        urlDbHelper.saveAsync(url);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        dbListener.onResult(new StorageTransactionResult(throwable));
                    }
                });
    }

    @Override
    public void saveUrls(final List<Url> list) {
        Observable.just(true)
                .observeOn(MAIN_SCHEDULER)
                .subscribeOn(IO_SCHEDULER)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        urlDbHelper.saveAsync(list);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        dbListener.onResult(new StorageTransactionResult(throwable));
                    }
                });
    }

    @Override
    public void deleteUrl(final Url url) {
        Observable.just(true)
                .observeOn(MAIN_SCHEDULER)
                .subscribeOn(IO_SCHEDULER)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        urlDbHelper.deleteAsync(url);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        dbListener.onResult(new StorageTransactionResult(throwable));
                    }
                });
    }

    @Override
    public void deleteAll(final Url url) {
        Observable.just(true)
                .observeOn(MAIN_SCHEDULER)
                .subscribeOn(IO_SCHEDULER)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        urlDbHelper.deleteAllAsync(url);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        dbListener.onResult(new StorageTransactionResult(throwable));
                    }
                });
    }

    @Override
    public void clearDb() {
        Observable.just(true)
                .observeOn(MAIN_SCHEDULER)
                .subscribeOn(IO_SCHEDULER)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        urlDbHelper.clearAsync();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        dbListener.onResult(new StorageTransactionResult(throwable));
                    }
                });
    }

    @Override
    public void getUrls() {
        urlDbHelper.getUrlList()
                .observeOn(MAIN_SCHEDULER)
                .subscribeOn(IO_SCHEDULER)
                .subscribe(new Action1<List<Url>>() {
                    @Override
                    public void call(List<Url> urls) {
                        dbListener.onResult(new StorageTransactionResult(urls));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        dbListener.onResult(new StorageTransactionResult(throwable));
                    }
                });
    }

    @Override
    public void getUrl(String primaryKey) {
        urlDbHelper.get(primaryKey)
                .observeOn(MAIN_SCHEDULER)
                .subscribeOn(IO_SCHEDULER)
                .subscribe(new Action1<Url>() {
                    @Override
                    public void call(Url url) {
                        dbListener.onResult(new StorageTransactionResult(url));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        dbListener.onResult(new StorageTransactionResult(throwable));
                    }
                });
    }
}
