package com.example.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ArtistActivity extends AppCompatActivity {

    private String name;
    private String id;
    private String keyword;
    private TabLayout tabs;
    private ViewPager pager;
    private boolean favorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        id = intent.getStringExtra("id");
        try {
            keyword = intent.getStringExtra("keyword");
        }catch (Exception e){
            keyword = "";
        }

        ArtistInfoFragment aif = ArtistInfoFragment.newInstance(name, id);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Favorites", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        if(pref.contains("ids")){
            String ids = pref.getString("ids", null);
            Log.d("ArtistActivity", ids);
            String[] idss = ids.split("\\|");
            for(int i=0; i<idss.length; i++) {
                if(idss[i].equals(id)){
                    favorite = true;
                }
            }
        }

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.activity_artist_actionbar);
        //getSupportActionBar().setElevation(0);
        View view = getSupportActionBar().getCustomView();
        ImageButton favoriteButton = view.findViewById(R.id.favoriteButton);
        if(favorite){
            favoriteButton.setImageResource(R.drawable.star);
        }
        TextView artistName = view.findViewById(R.id.artistName);
        artistName.setText(name);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favorite){
                    String ids = pref.getString("ids", null);
                    String[] idss = ids.split("\\|");
                    String newids = "";
                    for(int i=0; i<idss.length; i++) {
                        if(idss[i].equals(id)){

                        }else{
                            newids = newids + idss[i]+"|";
                        }
                    }
                    editor.putString("ids", newids);
                    editor.remove(id + "_name");
                    editor.remove(id + "_birthday");
                    editor.remove(id + "_nationality");
                    editor.commit();
                    favoriteButton.setImageResource(R.drawable.star_outline);

                    Toast.makeText(ArtistActivity.this, name + " is removed from favorites", Toast.LENGTH_LONG).show();
                }else {
                    if (pref.contains("ids")) {
                        editor.putString("ids", pref.getString("ids", null) + id + "|");
                    } else {
                        editor.putString("ids", id + "|");
                    }
                    editor.putString(id + "_name", name);
                    editor.putString(id + "_birthday", aif.getBirthday());
                    editor.putString(id + "_nationality", aif.getNationality());
                    editor.commit();
                    favoriteButton.setImageResource(R.drawable.star);

                    Toast.makeText(ArtistActivity.this, name + " is added to favorites", Toast.LENGTH_LONG).show();
                }
            }
        });

        tabs = findViewById(R.id.tabs);
        pager = findViewById(R.id.view_pager);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(aif);
        fragments.add(ArtworkFragment.newInstance(name, id));
        ViewPagerAdapter adapterFragment = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        pager.setAdapter(adapterFragment);
        tabs.setupWithViewPager(pager);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(ArtistActivity.this, R.color.tabIconColor);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(ArtistActivity.this, R.color.tabIconColor);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(ArtistActivity.this, R.color.tabIconColor);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }
        });
        int tabIconColor = ContextCompat.getColor(ArtistActivity.this, R.color.tabIconColor);
        tabs.getTabAt(0).setText("DETAILS");
        tabs.getTabAt(0).setIcon(R.drawable.alert_circle_outline);
        tabs.getTabAt(0).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        tabs.getTabAt(1).setText("ARTWORK");
        tabs.getTabAt(1).setIcon(R.drawable.palette_outline);
        tabs.getTabAt(1).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            if(this.keyword == null){
                Intent myIntent = new Intent(ArtistActivity.this, MainActivity.class);
                startActivity(myIntent);
            }else {
                Intent myIntent = new Intent(ArtistActivity.this, SearchResultActivity.class);
                myIntent.putExtra("keyword", keyword);
                startActivity(myIntent);
            }
        }

        return super.onOptionsItemSelected(item);
    }
}