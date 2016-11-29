package com.maciega.bartosz.crawl.model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.maciega.bartosz.crawl.storage.DbStorage;
import com.maciega.bartosz.crawl.storage.DbTransactionListener;
import com.maciega.bartosz.crawl.storage.Storage;
import com.maciega.bartosz.crawl.storage.StorageTransactionResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import rx.Observable;

/**
 * Created by bartoszmaciega on 17/10/16.
 */

public class Spider implements DbTransactionListener {
    private int max_pages = 20;
    private Set<String> visitedPages = new HashSet<>();
    private List<String> pagesToVisit = new LinkedList<>();
    private Context context;
    private CrawlListener crawlListener;


    public Spider(Context context, int pagesToSearch, CrawlListener crawlListener) {
        this.max_pages = pagesToSearch > 0 ? pagesToSearch : 20;
        this.context = context;
        this.crawlListener = crawlListener;
    }


    private synchronized String nextUrl() {
        String nextUrl;

        synchronized (pagesToVisit) {
            do {
                nextUrl = pagesToVisit.remove(0);
            } while (this.visitedPages.contains(nextUrl));
        }

        this.visitedPages.add(nextUrl);
        return nextUrl;
    }


    public void search(String url) {

        SpiderAsync spiderAsync = new SpiderAsync();
        spiderAsync.execute(url);
    }

    @Override
    public void onResult(StorageTransactionResult result) {

    }


    class SpiderAsync extends AsyncTask<String, String, Void> implements CrawlListener, DbTransactionListener {


        @Override
        protected Void doInBackground(String... params) {
            String url = params[0];
            pagesToVisit.add(url);

            while (visitedPages.size() < max_pages && !pagesToVisit.isEmpty()) {
                try {
                    String currentUrl;
                    CrawlerItself crawler = new CrawlerItself(this);
                    currentUrl = nextUrl();
                    crawler.crawl(currentUrl);
                    publishProgress(currentUrl);

                    pagesToVisit.addAll(crawler.getLinks());
                } catch (Throwable e) {
                    return null;
                }

            }


            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            crawlListener.onCrawled(values[0]);
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Storage storage = new DbStorage(context);
            storage.saveList(UrlsMapper.INSTANCE.convert(visitedPages));
            Toast.makeText(context, "searching finished", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCrawled(String pageUrl) {

        }

        @Override
        public void onResult(StorageTransactionResult result) {
            if (result.isSuccess()) {
                Log.d("success", "success");
            }
        }
    }


}
