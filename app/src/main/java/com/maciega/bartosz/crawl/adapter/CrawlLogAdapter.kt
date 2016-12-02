package com.maciega.bartosz.crawl.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

import com.maciega.bartosz.crawl.R

import java.util.ArrayList

/**
 * Created by bartoszmaciega on 17/10/16.
 */

class CrawlLogAdapter(private val context: Context) : BaseAdapter() {
    private var urls: MutableList<String> = ArrayList()


    fun setData(urls: MutableList<String>) {
        this.urls = urls
        notifyDataSetChanged()
    }

    fun clear() {
        this.urls.clear()
        notifyDataSetChanged()
    }


    fun addData(url: String) {
        this.urls.add(url)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return urls.size
    }

    override fun getItem(position: Int): Any {
        return urls[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        val item = getItem(position) as String?
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.crawl_log_item, parent, false)
        val url = view.findViewById(R.id.url) as TextView
        url.text = item ?: "empty"
        return view
    }
}
