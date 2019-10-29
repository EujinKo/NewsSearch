/**
 * This app provides list of news from the keyword entered from the user(with button click)
 *
 * Also, used api from "https://newsapi.org/"
 *
 * @author Eujin Ko
 */

package com.example.newssearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    static String keyword="";
    static String date="2019-10-24";
    String api_key = "2fb38e92e5b2494cbb6bf9ce57bd1bc9";

    public static NewsFragment currFrag = null;
    public static int position;
    Activity itself = this;
    public static JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    //This function sets the current date
    public void setCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7 );

        Date date = calendar.getTime();

        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");
        this.date = formatter.format(date);
        System.out.println("Current Date(-7 days): "+this.date);
    }

    //This function returns array of articles from JSONObject
    public JSONArray returnArticleArray(JSONObject object){
        JSONArray jsonArray = null;
        try {
            jsonArray = object.getJSONArray("articles");
        } catch (Exception e) {
            System.out.println("Article array not returned");
        } finally {
            return jsonArray;
        }
    }

    //This function performs search when button clicked with proper detail
    public void onClickSearch(View view){
        EditText editText = (EditText) findViewById(R.id.edit_text);
        keyword = editText.getText().toString();

        System.out.println("KEYWORD: "+keyword);
        if(keyword.equals("Enter keyword") || keyword.trim().equals("")){
            Toast.makeText(getApplicationContext(),"No Keyword Entered!", Toast.LENGTH_SHORT).show();
            return;
        }

        setCurrentDate();
        new fetchNewsTask().execute();
    }

    //This function gives details on the news when clicked
    public void onClickNews(View view){
        ListView listView = (ListView) view.getParent();
        this.position = listView.getPositionForView(view);

        System.out.println("id: "+position);
        //TODO: set up fragment with the proper value

        try{

            //TODO: Create Bundle to store JSONObject(in position)
            JSONObject json = jsonArray.getJSONObject(position);
            String json_string = json.toString();
            Bundle bundle = new Bundle();
            bundle.putString("DETAILS",json_string);

            //TODO: Create fragment and store bundle in extra
            PreviewFragment detail_frag = new PreviewFragment();
            detail_frag.setContainerActivity(itself);
            detail_frag.setArguments(bundle);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.news_detail_layout,detail_frag);

            transaction.addToBackStack(null);
            transaction.commit();



        }catch(Exception e){
            System.out.println("JSONObject not found in position "+position);
        }
    }

    //This function opens a web browser when clicked
    public void onClickRead(View view){

        try{

            //TODO: Create Bundle to store JSONObject(in position)
            JSONObject json = jsonArray.getJSONObject(position);
            String urlForBrowser = json.getString("url");

            Bundle bundle = new Bundle();
            bundle.putString("URL",urlForBrowser);

            WebFragment webFragment = new WebFragment();
            webFragment.setArguments(bundle);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.web_layout,webFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }catch(Exception e){
            System.out.println("JSONObject not found in position "+position);
        }
    }

    // AsyncTask that performs fetching the news articles from the keyword enetered
    private class fetchNewsTask extends AsyncTask<Object, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(Object[] objects){
            String json="";
            String line;

            try{
                String web = url_01+keyword+url_02+date+url_03+api_key;
                System.out.println(web);
                URL url = new URL(web);

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
                if(currFrag!=null){
                    System.out.println(currFrag.toString());
                    FragmentManager manager = currFrag.getFragmentManager();
                    FragmentTransaction trans = manager.beginTransaction();
                    trans.remove(currFrag);
                    trans.commit();
                }
                //TODO: Create Bundle to store JSONArray

                Bundle bundle = new Bundle();
                jsonArray = returnArticleArray(json);
                bundle.putString("JSON_ARRAY",jsonArray.toString());

                //TODO: Create Fragment & attach the bundle

                currFrag = new NewsFragment();
                currFrag.setContainerActivity(itself);
                currFrag.setArguments(bundle);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.news_layout,currFrag);

                transaction.addToBackStack(null);
                transaction.commit();

            }catch(Exception e){
                System.out.println("Not enough information to show on screen");
            }

        }


    }

}
