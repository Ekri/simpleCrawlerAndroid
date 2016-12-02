package com.maciega.bartosz.crawl

import android.content.Context
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.ListView

import com.maciega.bartosz.crawl.adapter.DataAdapter
import com.maciega.bartosz.crawl.model.Url
import com.maciega.bartosz.crawl.presenter.DataPresenter

/**
 * Created by bartoszmaciega on 17/10/16.
 */

class DataActivity : AppCompatActivity(), DataPresenter.DataView, SearchView.OnQueryTextListener {

    internal var listView: ListView? = null
    internal var dataAdapter: DataAdapter? = null
    private var presenter: DataPresenter? = null
    private var searchView: SearchView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.data_activity)
        presenter = DataPresenter(this, this)
        dataAdapter = DataAdapter(this)
        listView = findViewById(R.id.list) as ListView
        listView!!.adapter = dataAdapter
        initToolbar()
    }


    private fun initToolbar() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        load()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.search)
        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView
        searchView.setOnQueryTextListener(this)
        this.searchView = searchView
        MenuItemCompat.setOnActionExpandListener(searchItem, searchExpandListener)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            super.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun load() {
        presenter!!.loadData()
    }


    private val searchExpandListener = object : MenuItemCompat.OnActionExpandListener {
        override fun onMenuItemActionExpand(item: MenuItem): Boolean {
            return true
        }

        override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
            presenter!!.finishQuery()
            dataAdapter?.resetQuery()
            return true
        }
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        presenter!!.submitQuery(query)
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchView!!.windowToken, 0)
        dataAdapter?.setQuery(query)
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {

        return false
    }

    override fun showUrls(urls: List<Url>) {
        dataAdapter?.setData(urls)
    }
}
