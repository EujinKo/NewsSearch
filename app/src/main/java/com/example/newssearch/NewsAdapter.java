package com.example.newssearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<WebSource> {
    private Context context;
    private List<WebSource> webSources;
    int textViewResourceId;

    public NewsAdapter(Context context, int textViewResourceId, List<WebSource> webSources){
        super(context,textViewResourceId,webSources);
        this.context = context;
        this.textViewResourceId = textViewResourceId;
        this.webSources = webSources;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row_view = convertView;
        if(row_view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row_view = inflater.inflate(this.textViewResourceId,null);
        }
        TextView website_view = (TextView) row_view.findViewById(R.id.news_row_website);
        TextView author_view = (TextView) row_view.findViewById(R.id.news_row_author);

        if(website_view!=null){
            WebSource curr = webSources.get(position);
            website_view.setText(curr.getWebsite());
            author_view.setText("("+curr.getAuthor()+")");
        }

        return row_view;
    }



}
