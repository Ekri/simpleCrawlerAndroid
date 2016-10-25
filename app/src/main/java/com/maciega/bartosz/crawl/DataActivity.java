package com.maciega.bartosz.crawl;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;

import com.maciega.bartosz.crawl.adapter.DataAdapter;
import com.maciega.bartosz.crawl.model.Url;
import com.maciega.bartosz.crawl.presenter.DataPresenter;

import java.util.List;

/**
 * Created by bartoszmaciega on 17/10/16.
 */

public class DataActivity extends AppCompatActivity implements DataPresenter.DataView, SearchView.OnQueryTextListener {

    ListView listView;
    DataAdapter dataAdapter;
    private DataPresenter presenter;
    private SearchView searchView;


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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        this.searchView = searchView;
        MenuItemCompat.setOnActionExpandListener(searchItem, searchExpandListener);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void load() {
        presenter.loadData();
    }


    private MenuItemCompat.OnActionExpandListener searchExpandListener = new MenuItemCompat.OnActionExpandListener() {
        @Override
        public boolean onMenuItemActionExpand(MenuItem item) {
            return true;
        }

        @Override
        public boolean onMenuItemActionCollapse(MenuItem item) {
            presenter.finishQuery();
            return true;
        }
    };


    @Override
    public void showUrls(List<Url> urls) {
        dataAdapter.setData(urls);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        presenter.submitQuery(query);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        return false;
    }
}
