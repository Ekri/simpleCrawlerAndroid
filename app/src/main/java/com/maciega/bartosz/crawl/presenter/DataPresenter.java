package com.maciega.bartosz.crawl.presenter;

import android.content.Context;
import android.util.Log;

import com.maciega.bartosz.crawl.model.Url;
import com.maciega.bartosz.crawl.storage.DbStorage;
import com.maciega.bartosz.crawl.storage.Storage;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by bartoszmaciega on 24/10/16.
 */

public class DataPresenter {

    public interface DataView {
        void showUrls(List<Url> urls);

    }

    private DataView dataView;
    private Context context;
    private Storage storage;

    public DataPresenter(Context context, DataView dataView) {
        this.dataView = dataView;
        this.context = context;
        this.storage = new DbStorage(context);
    }


    public void loadData() {
        Observable.create(new Observable.OnSubscribe<List<Url>>() {

            @Override
            public void call(Subscriber<? super List<Url>> subscriber) {
                subscriber.onNext(storage.getList(Url.class));
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Url>>() {
                               @Override
                               public void call(List<Url> urls) {
                                    dataView.showUrls(urls);
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Log.d("error", throwable.getLocalizedMessage());
                            }
                        });


    }


}
