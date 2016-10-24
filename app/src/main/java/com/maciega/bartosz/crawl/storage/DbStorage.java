package com.maciega.bartosz.crawl.storage;

import android.content.Context;

import com.maciega.bartosz.crawl.model.Url;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Bartosz on 2016-10-18.
 */

public class DbStorage implements UrlStorage {

    private Context context;

    public DbStorage(Context context) {
        this.context = context;
    }

    @Override
    public void save(RealmObject object) {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            realm.insertOrUpdate(object);
            realm.commitTransaction();
        } catch (Throwable e) {
            e.printStackTrace();
            realm.cancelTransaction();
        }
        realm.close();

    }

    @Override
    public <T extends RealmObject> void saveList(List<RealmObject> objects) {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            realm.insertOrUpdate(objects);
            realm.commitTransaction();
        } catch (Throwable e) {
            e.printStackTrace();
            realm.cancelTransaction();
        }
        realm.close();
    }


    @Override
    public void delete(RealmObject object) {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            //TODO rethink how this should be designed
//            RealmResults<RealmModel> result = realm.where(object.getClass()).equalTo(object)

        } catch (Throwable e) {
            e.printStackTrace();
            realm.cancelTransaction();
        }
        realm.close();

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

}
