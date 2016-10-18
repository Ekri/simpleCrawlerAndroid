package com.maciega.bartosz.crawl.model;

import java.util.List;

import io.realm.RealmObject;

/**
 * Created by Bartosz on 2016-10-18.
 */

public interface SpiderServantActions {
    void saveUrl(Url url);
    void saveUrls(List<Url> list);
    void deleteUrl(Url url);
    void deleteAll(Url url);
    void clearDb();
    void  getUrls();
    void getUrl(String primaryKey);
}
