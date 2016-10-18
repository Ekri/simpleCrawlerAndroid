package com.maciega.bartosz.crawl.storage;

import android.content.Context;

import com.maciega.bartosz.crawl.model.Url;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import io.realm.RealmObject;

/**
 * Created by Bartosz on 2016-10-18.
 */

public class DbStorage implements UrlStorage {

    private ThreadPoolExecutor executor;
    private Context context;

    public DbStorage(Context context){
        this.context = context;
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
    }

    @Override
    public void save(RealmObject object) {

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
    public List<Url> get(String primaryKey) {
        return null;
    }
}
