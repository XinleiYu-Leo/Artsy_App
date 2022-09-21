package com.example.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setTheme(R.style.Theme_App);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView todayText = this.findViewById(R.id.todayText);
        LocalDate nowDate = LocalDate.now();
        String [] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        String nowDateString = nowDate.getDayOfMonth() + " " + months[nowDate.getMonthValue() - 1] + " " + nowDate.getYear();
        todayText.setText(nowDateString);

        TextView artsyText = this.findViewById(R.id.artsyText);
        artsyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.artsy.net/"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.activity_main_actionbar);
        //getSupportActionBar().setElevation(0);
        View view = getSupportActionBar().getCustomView();
        ImageButton searchButton = view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().setCustomView(R.layout.activity_main_searchbar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                View view = getSupportActionBar().getCustomView();
                EditText searchWord = view.findViewById(R.id.searchWord);
                searchWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        Intent myIntent = new Intent(MainActivity.this, SearchResultActivity.class);
                        myIntent.putExtra("keyword",v.getText().toString());
                        startActivity(myIntent);
                        return false;
                    }
                });
//                Toast.makeText(MainActivity.this, "You have clicked tittle", Toast.LENGTH_LONG).show();
            }
        });

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Favorites", 0);

        RecyclerView recycler = findViewById(R.id.favorite_recyclerview);

        String[] fids;
        String[] fnames;
        String[] fbirths;
        String[] fnations;
        if(pref.contains("ids")){
            String ids = pref.getString("ids", null);
            Log.d("ArtistActivity", ids);
            String[] idss = ids.split("\\|");
            fids = new String[idss.length];
            fnames = new String[idss.length];
            fbirths = new String[idss.length];
            fnations = new String[idss.length];
            for(int i=0; i<idss.length; i++) {
                fids[i] = idss[i];
                fnames[i] = pref.getString(idss[i]+"_name", null);
                fbirths[i] = pref.getString(idss[i]+"_birthday", null);
                fnations[i] = pref.getString(idss[i]+"_nationality", null);
            }
            recycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            recycler.setAdapter(new FavoritesAdapter(fids, fnames, fbirths, fnations, MainActivity.this));
        }
        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        hideProgressBar();
                    }
                }
        , 1000);
    }

    private void hideProgressBar(){
        findViewById(R.id.mainProgressBar).setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setCustomView(R.layout.activity_main_actionbar);
            View view = getSupportActionBar().getCustomView();
            ImageButton searchButton = view.findViewById(R.id.searchButton);
            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getSupportActionBar().setCustomView(R.layout.activity_main_searchbar);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    View view = getSupportActionBar().getCustomView();
                    EditText searchWord = view.findViewById(R.id.searchWord);
                    searchWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            Intent myIntent = new Intent(MainActivity.this, SearchResultActivity.class);
                            myIntent.putExtra("keyword",v.getText().toString());
                            startActivity(myIntent);
                            return false;
                        }
                    });
//                Toast.makeText(MainActivity.this, "You have clicked tittle", Toast.LENGTH_LONG).show();
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }
}