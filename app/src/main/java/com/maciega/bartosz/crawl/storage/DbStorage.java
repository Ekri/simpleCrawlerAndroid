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
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Bartosz on 2016-10-18.
 */

public class DbStorage implements UrlStorage {

    private ThreadPoolExecutor executor;
    private Context context;
    private DbTransactionListener transactionListener;
    private final Scheduler IO_SCHEDULER = Schedulers.io();
    private final Scheduler MAIN_SCHEDULER = AndroidSchedulers.mainThread();

    public DbStorage(Context context, DbTransactionListener transactionListener){
        this.context = context;
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        this.transactionListener = transactionListener;
    }

    @Override
    public void save(RealmObject object) {
        Observable.just(true)
                .subscribeOn(IO_SCHEDULER)
                .observeOn(MAIN_SCHEDULER)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {


                        transactionListener.onResult(StorageTransactionResult.emptySuccessResult());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                       passErrorToListener(throwable);
                    }
                });
    }

    @Override
    public void saveList(List<RealmObject> objects) {

    }

    @Override
    public void delete(RealmObject object) {

    }

    @Override
    public void deleteAllObjects(RealmObject object) {

    }

    @Override
    public void clearDatabase() {

    }

    @Override
    public <T extends RealmObject> List<T> getList(Class<T> classToGet) {
        return null;
    }

    @Override
    public Url get(String primaryKey) {
        return null;
    }


    private void passErrorToListener(Throwable throwable){
        StorageTransactionResult result = new StorageTransactionResult(throwable);
        transactionListener.onResult(result);
    }
}
