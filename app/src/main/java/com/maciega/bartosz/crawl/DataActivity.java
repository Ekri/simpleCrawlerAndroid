package com.maciega.bartosz.crawl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.maciega.bartosz.crawl.adapter.DataAdapter;
import com.maciega.bartosz.crawl.model.Url;
import com.maciega.bartosz.crawl.presenter.DataPresenter;

import java.util.List;

/**
 * Created by bartoszmaciega on 17/10/16.
 */

public class DataActivity extends AppCompatActivity implements DataPresenter.DataView {

    ListView listView;
    DataAdapter dataAdapter;
    private DataPresenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_activity);
        listView = (ListView) findViewById(R.id.list);
        presenter = new DataPresenter(this, this);
        dataAdapter = new DataAdapter(this);
        listView.setAdapter(dataAdapter);
        initToolbar();
    }


    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        load();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void load() {
        presenter.loadData();
    }


    @Override
    public void showUrls(List<Url> urls) {
        dataAdapter.setData(urls);
    }
}
