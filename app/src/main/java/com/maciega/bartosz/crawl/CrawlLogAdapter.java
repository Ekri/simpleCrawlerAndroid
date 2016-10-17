package com.maciega.bartosz.crawl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bartoszmaciega on 17/10/16.
 */

public class CrawlLogAdapter extends BaseAdapter {

    private Context context;
    private List<String> urls = new ArrayList<>();


    public CrawlLogAdapter(Context context) {
        this.context = context;
    }


    public void setData(List<String> urls) {
        this.urls = urls;
        notifyDataSetChanged();
    }

    public void clear(){
        this.urls.clear();
        notifyDataSetChanged();
    }


    public void addData(String url) {
        this.urls.add(url);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public Object getItem(int position) {
        return urls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String item = (String) getItem(position);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.crawl_log_item,parent,false);
        TextView url = (TextView) view.findViewById(R.id.url);
        url.setText(item != null? item : "empty");
        return view;
    }
}
