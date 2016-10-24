package com.maciega.bartosz.crawl;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by bartoszmaciega on 24/10/16.
 */

public class CrawlApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
