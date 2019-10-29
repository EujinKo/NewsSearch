package com.example.newssearch;


import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class PreviewFragment extends Fragment {

    public Activity containerActivity = null;

    public PreviewFragment() {
        // Required empty public constructor
    }

    public void setContainerActivity(Activity containerActivity){
        this.containerActivity = containerActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_preview, container, false);
        String title = "";
        String content = "";
        String   url = "";
        try{
            String json_string = this.getArguments().getString("DETAILS");
            System.out.println(json_string);


            JSONObject json = new JSONObject(json_string);

            title = json.getJSONObject("source").getString("name");
            content = json.getString("content");
            url = json.getString("url");

        }catch(Exception e){
            System.out.println("No such details in JSONObject");
        }

        TextView title_view = (TextView) view.findViewById(R.id.frag_title);
        TextView description_view = (TextView) view.findViewById(R.id.frag_details);

        title_view.setText(title);
        description_view.setText(content);

        //TODO: launch webView with url


        return view;
    }

}
