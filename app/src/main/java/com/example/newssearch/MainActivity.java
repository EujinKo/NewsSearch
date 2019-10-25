/**
 * This app provides list of news from the keyword entered from the user(with button click)
 *
 * Also, used api from "https://newsapi.org/"
 *
 * @author Eujin Ko
 */

package com.example.newssearch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    // https://newsapi.org/v2/everything?sortBy=publishedAt&q=SEARCH_TERM&from=START_DATE&apiKey=API_KEY

    // After url_01, provide keyword
    // After url_02, provide date(YYYY-MM-DD), represents one week prior to the present day
    // After url_03, provide api key
    String url_01="https://newsapi.org/v2/everything?sortBy=publishedAt&q=";
    String url_02="&from=";
    String url_03="&apiKey=";

    // Values to put in the url

    //TODO: Empty the keyword & date after testing
    static String keyword="nintendo";
    static String date="2019-10-24";
    String api_key = "2fb38e92e5b2494cbb6bf9ce57bd1bc9";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickSearch(View view){
        new fetchNewsTask().execute();
    }

    // AsyncTask that performs fetching the news articles from the keyword enetered
    private class fetchNewsTask extends AsyncTask<Object, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(Object[] objects){
            String json="";
            String line;

            try{
                URL url = new URL(url_01+keyword+url_02+date+url_03+api_key);

                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                while((line = in.readLine()) != null){
                    System.out.println("JSON LINE"+line);
                    json += line;
                }
                in.close();

                JSONObject jsonObject = new JSONObject(json);

                return jsonObject;

            }catch(Exception e){
                System.out.println("Error on URL");
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject json){
            try{

            }catch(Exception e){
                System.out.println("Not enough information to show on screen");
            }

        }


    }

}
