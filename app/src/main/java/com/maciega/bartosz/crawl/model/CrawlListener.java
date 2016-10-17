package com.maciega.bartosz.crawl.model;

/**
 * Created by bartoszmaciega on 17/10/16.
 */

public interface CrawlListener {
    void onCrawled(String pageUrl);
}
