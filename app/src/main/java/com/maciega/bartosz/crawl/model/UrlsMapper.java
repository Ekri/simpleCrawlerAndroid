package com.maciega.bartosz.crawl.model;

import java.util.ArrayList;
import java.util.List;

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

}
