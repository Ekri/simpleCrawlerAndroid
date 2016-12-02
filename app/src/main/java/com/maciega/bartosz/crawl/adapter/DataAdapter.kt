package com.maciega.bartosz.crawl.adapter

import android.content.Context
import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

import com.maciega.bartosz.crawl.R
import com.maciega.bartosz.crawl.model.Url

import java.util.ArrayList

/**
 * Created by bartoszmaciega on 24/10/16.
 */

class DataAdapter(private val context: Context) : BaseAdapter() {

    private var urls: List<Url>? = null
    private val inflater: LayoutInflater
    private var query = ""


    init {
        urls = ArrayList<Url>()
        inflater = LayoutInflater.from(context)
    }


    fun setData(list: List<Url>) {
        this.urls = list
        notifyDataSetChanged()
    }


    fun setQuery(query: String) {
        this.query = query
    }


    fun resetQuery() {
        query = ""
    }

    override fun getCount(): Int {
        return urls!!.size
    }

    override fun getItem(position: Int): Any {
        return urls!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        val item = getItem(position) as Url
        val view = inflater.inflate(R.layout.data_item, parent, false)
        val textView = view.findViewById(R.id.url) as TextView
        textView.text = if (item.url != null) item.url else "empty record"
        if (!query.isEmpty())
            higlightWithQuery(textView)
        return view
    }


    private fun higlightWithQuery(textView: TextView) {
        val text = textView.text.toString()
        val startIndex = text.indexOf(query)
        val endIndex = startIndex + query.length
        if (startIndex > 0 && endIndex > startIndex) {
            val builder = SpannableStringBuilder(text)
            builder.setSpan(BackgroundColorSpan(Color.YELLOW), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
            textView.text = builder
        }

    }
}
