package com.example.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class SearchResultActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Intent intent = getIntent();
        keyword = intent.getStringExtra("keyword");
        getSupportActionBar().setTitle(keyword.toUpperCase(Locale.ROOT));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.noResultsFound).setVisibility(View.INVISIBLE);

        recyclerView = findViewById(R.id.search_result_recyclerview);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://homework9-113322.wl.r.appspot.com/search/" + keyword;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            findViewById(R.id.searchResultProgressBar).setVisibility(View.GONE);
                            findViewById(R.id.searchResultProgressBarLabel).setVisibility(View.GONE);
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() > 0){
                                findViewById(R.id.noResultsFound).setVisibility(View.INVISIBLE);
                            }else{
                                findViewById(R.id.noResultsFound).setVisibility(View.VISIBLE);
                            }
                            String[] artistNames = new String[jsonArray.length()];
                            String[] imgSrc = new String[jsonArray.length()];
                            String[] ids = new String[jsonArray.length()];
                            for(int i = 0;i < jsonArray.length();i++){
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                if(jsonObject.getString("img").equals("/assets/shared/missing_image.png")){
                                    imgSrc[i] = "https://iconape.com/wp-content/files/wt/144863/png/artsy-logo.png";
                                    Log.d("CREATION", "img == null reached" );
                                }
                                else{
                                    imgSrc[i] = jsonObject.getString("img");
                                }
                                artistNames[i] = jsonObject.getString("name");
                                //imgSrc[i] = jsonObject.getString("img");
                                ids[i] = jsonObject.getString("id");
//                                Log.i("SearhResultActivity", jsonObject.getString("name"));
                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(SearchResultActivity.this));
                            recyclerView.setAdapter(new SearchResultAdapter(artistNames, imgSrc, ids, SearchResultActivity.this, keyword));
                        }catch(JSONException e){
                            Toast.makeText(SearchResultActivity.this, "Failed to parse JSON string.", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("SearhResultActivity", error.getMessage());
                Toast.makeText(SearchResultActivity.this, "Cannot make internet request.", Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            Intent myIntent = new Intent(SearchResultActivity.this, MainActivity.class);
            startActivity(myIntent);
        }

        return super.onOptionsItemSelected(item);
    }

}