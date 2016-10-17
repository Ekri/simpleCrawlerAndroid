package com.maciega.bartosz.crawl.model;

import android.os.AsyncTask;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bartoszmaciega on 17/10/16.
 */

public class CrawlerItself {
    private List<String> links = new ArrayList<>();
    private Document htmlDocument;
    private CrawlListener crawlListener;
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";


    public CrawlerItself(CrawlListener crawlListener) {
        this.crawlListener = crawlListener;
    }


    public boolean crawl(String nextUrl) {
        Connection connection = Jsoup.connect(nextUrl).userAgent(USER_AGENT);
        try {
            Document htmlDocument = connection.get();
            if (htmlDocument == null)
                return false;
            if (connection.response().statusCode() == 200) {
                if (!connection.response().contentType().contains("text/html")) {
                    return false;
                }
                Elements linksOnPage = htmlDocument.select("a[href]");

                for (Element element : linksOnPage) {
                    links.add(element.absUrl("href"));
                }

                return true;

            }

            return false;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getLinks() {
        return links;
    }

}
