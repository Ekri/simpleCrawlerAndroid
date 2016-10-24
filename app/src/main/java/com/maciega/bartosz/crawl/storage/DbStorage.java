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

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

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
            realm.delete(object.getClass());
            realm.commitTransaction();

        } catch (Throwable e) {
            e.printStackTrace();
            realm.cancelTransaction();
        }
        realm.close();

    }

    @Override
    public void deleteAllObjects(RealmObject object) {
        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<RealmObject> result = (RealmResults<RealmObject>) realm.where(object.getClass()).findAll();
            realm.beginTransaction();
            result.deleteAllFromRealm();
            realm.commitTransaction();

        } catch (Throwable e) {
            e.printStackTrace();
            realm.cancelTransaction();
        }

        realm.close();
    }

    @Override
    public void clearDatabase() {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            realm.deleteAll();
            realm.commitTransaction();
        } catch (Throwable e) {
            e.printStackTrace();
            realm.cancelTransaction();
        }

        realm.close();
    }

    @Override
    public <T extends RealmObject> List<T> getList(Class<T> classToGet) {
        Realm realm = Realm.getDefaultInstance();
        List<T> result = realm.where(classToGet).findAll();
        List<T> copy = realm.copyFromRealm(result);
        return copy;
    }

    @Override
    public Url get(String primaryKey) {
        Realm realm = Realm.getDefaultInstance();
        Url result = realm.where(Url.class).equalTo("url",primaryKey).findFirst();
        return result;
    }

}
