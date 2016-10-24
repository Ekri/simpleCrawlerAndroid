package com.maciega.bartosz.crawl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.maciega.bartosz.crawl.R;
import com.maciega.bartosz.crawl.model.Url;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bartoszmaciega on 24/10/16.
 */

public class DataAdapter extends BaseAdapter {

    private List<Url> urls;
    private Context context;
    private LayoutInflater inflater;


    public DataAdapter(Context context) {
        this.context = context;
        urls = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }


    public void setData(List<Url> list) {
        this.urls = list;
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
        Url item = (Url) getItem(position);
        View view = inflater.inflate(R.layout.data_item, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.url);
        textView.setText(item.getUrl() != null ? item.getUrl() : "empty record");
        return view;
    }
}
