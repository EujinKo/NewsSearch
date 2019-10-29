package com.example.newssearch;


import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebFragment extends Fragment {

    public Activity containerActivity = null;

    public WebFragment() {
        // Required empty public constructor
    }

    public void setContainerActivity(Activity containerActivity){
        this.containerActivity = containerActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_web, container, false);

        String url = this.getArguments().getString("URL");
        WebView webView = view.findViewById(R.id.web_view);
        webView.loadUrl(url);

        return view;
    }

}
