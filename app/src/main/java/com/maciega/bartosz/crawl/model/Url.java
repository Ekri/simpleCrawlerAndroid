package com.maciega.bartosz.crawl.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Bartosz on 2016-10-18.
 */

public class Url extends RealmObject {

    @PrimaryKey
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
