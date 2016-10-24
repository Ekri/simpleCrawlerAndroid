package com.maciega.bartosz.crawl.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.realm.RealmObject;

/**
 * Created by Bartosz on 2016-10-18.
 */

public class UrlsMapper {

    public static List<Url> convert(List<String> list) {
        List<Url> urlsList = new ArrayList<>();
        for (String urlString : list) {
            Url url = new Url();
            url.setUrl(urlString);
            urlsList.add(url);
        }

        return urlsList;
    }

    public static List<RealmObject> convert(Set<String> urls){
        List<RealmObject> urlList = new ArrayList<>();
        for(String url : urls){
            Url urlInstance = new Url();
            urlInstance.setUrl(url);
            urlList.add(urlInstance);
        }
        return urlList;
    }

}
