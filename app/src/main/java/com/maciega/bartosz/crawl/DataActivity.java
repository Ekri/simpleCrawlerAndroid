package com.maciega.bartosz.crawl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

/**
 * Created by bartoszmaciega on 17/10/16.
 */

public class DataActivity extends AppCompatActivity {

    ListView listView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_activity);
        listView = (ListView) findViewById(R.id.list);
    }
}
