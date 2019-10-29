package com.example.newssearch;


import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

    public Activity containerActivty = null;
    ListView newsListView;

    public NewsFragment() {
        // Required empty public constructor
    }

    public void setContainerActivity(Activity containerActivity){
        this.containerActivty = containerActivity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news,container, false);

        //TODO: Retrieve bundle(Save name and author to the list)
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(this.getArguments().getString("JSON_ARRAY"));
        } catch (Exception e) {
            System.out.println("Weren't able to create json array");
        }
        System.out.println("Retrieved Bundle: "+jsonArray.toString());

        List<WebSource> webSources = new ArrayList<>();


        try {
            int index = 0;
            String website, author;

            JSONObject curr = jsonArray.getJSONObject(index);
            while (curr != null) {
                website = curr.getJSONObject("source").getString("name");
                author = curr.getString("author");
                webSources.add(new WebSource(website, author));

                curr = jsonArray.getJSONObject(++index);
            }
        }catch(Exception e){
            System.out.println("Cannot get next object");
        }

        NewsAdapter newsAdapter = new NewsAdapter(containerActivty, R.layout.news_row, webSources);
        newsListView = v.findViewById(R.id.news_list_frag);
        newsListView.setAdapter(newsAdapter);

        return v;
    }

}
