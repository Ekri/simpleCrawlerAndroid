package com.maciega.bartosz.crawl.storage;

import android.content.Context;

import com.maciega.bartosz.crawl.model.Url;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import io.realm.RealmObject;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Bartosz on 2016-10-18.
 */

public class DbUrlHelper {

    private ThreadPoolExecutor executor;
    private Context context;
    private UrlStorage storage;

    public DbUrlHelper(Context context, UrlStorage storage) {
        this.context = context;
        this.storage = storage;
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
    }

    public void saveAsync(final Url object) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                storage.save(object);
            }
        });
    }

    public void saveAsync(final List<RealmObject> list){
        executor.submit(new Runnable() {
            @Override
            public void run() {
                storage.saveList(list);
            }
        });
    }

    public void deleteAsync(final Url realmObject){
        executor.submit(new Runnable() {
            @Override
            public void run() {
                storage.delete(realmObject);
            }
        });
    }

    public void deleteAllAsync(final Url realmObject){
        executor.submit(new Runnable() {
            @Override
            public void run() {
                storage.deleteAllObjects(realmObject);
            }
        });
    }

    public void clearAsync(){
        executor.submit(new Runnable() {
            @Override
            public void run() {
                storage.clearDatabase();
            }
        });
    }

    public Observable<Url> get(String primaryKey){
        return Observable.just(storage.get(primaryKey));
    }

    public Observable<List<Url>> getUrlList(){
        return Observable.just(storage.getList(Url.class));
    }

}
