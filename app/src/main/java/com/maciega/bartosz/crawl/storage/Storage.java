package com.maciega.bartosz.crawl.storage;

import java.util.List;

import io.realm.RealmObject;

/**
 * Created by Bartosz on 2016-10-18.
 */

public interface Storage {
    void save(RealmObject object);
    <T extends  RealmObject> void saveList(List<RealmObject> objects);
    void delete(RealmObject object);
    void deleteAllObjects(RealmObject object);
    void clearDatabase();
    <T extends  RealmObject>List<T> getList(Class<T> classToGet);
}
