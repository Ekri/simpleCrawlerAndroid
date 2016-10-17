package com.maciega.bartosz.crawl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.maciega.bartosz.crawl.model.CrawlListener;
import com.maciega.bartosz.crawl.model.Spider;

/**
 * Created by bartoszmaciega on 17/10/16.
 */

public class CrawlActivity extends AppCompatActivity implements CrawlListener {

    EditText startSiteEt;
    EditText countEt;
    ListView crawlLogLv;
    CrawlLogAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crawl_activity);
        startSiteEt = (EditText) findViewById(R.id.site_et);
        countEt = (EditText) findViewById(R.id.max_count);
        crawlLogLv = (ListView) findViewById(R.id.crawl_log_lv);
        setLog();
        findViewById(R.id.crawl_start_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crawl();
            }
        });
    }


    private void crawl() {
        adapter.clear();
        String url = startSiteEt.getText().toString();
        if (url == null || url.isEmpty()) {
            startSiteEt.setError("Cannot be null");
            return;
        }

        Spider spider = new Spider(this, Integer.valueOf(countEt.getText().toString().isEmpty() ? "0" :
                countEt.getText().toString()), this);
        spider.search(url);
    }


    private void setLog() {
        adapter = new CrawlLogAdapter(this);
        crawlLogLv.setAdapter(adapter);
    }


    @Override
    public void onCrawled(String pageUrl) {
        Log.d("crawled: ", pageUrl);
        adapter.addData(pageUrl);
        crawlLogLv.post(new Runnable() {
            @Override
            public void run() {
                crawlLogLv.setSelection(adapter.getCount() - 1);
            }
        });
    }
}
