package com.example.app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArtistInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArtistInfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String name;
    private String id;
    private String birthday;
    private String nationality;

    public ArtistInfoFragment() {
        birthday = "";
        nationality = "";
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ArtistInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ArtistInfoFragment newInstance(String param1, String param2) {
        ArtistInfoFragment fragment = new ArtistInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getNationality() {
        return nationality;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_PARAM1);
            id = getArguments().getString(ARG_PARAM2);
        }

        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="https://homework9-113322.wl.r.appspot.com/artists/" + id;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i = 0;i < jsonArray.length();i++){
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                                getActivity().findViewById(R.id.nameLayout).setVisibility(View.GONE);
                                getActivity().findViewById(R.id.nationalityLayout).setVisibility(View.GONE);
                                getActivity().findViewById(R.id.birthdayLayout).setVisibility(View.GONE);
                                getActivity().findViewById(R.id.deathdayLayout).setVisibility(View.GONE);
                                getActivity().findViewById(R.id.biographyLayout).setVisibility(View.GONE);
                                try {
                                    TextView nameText = getActivity().findViewById(R.id.nameText);
                                    nameText.setText(jsonObject.getString("name"));

                                    if( !jsonObject.getString("name").equals("")){
                                        getActivity().findViewById(R.id.nameLayout).setVisibility(View.VISIBLE);
                                    }else{
                                        getActivity().findViewById(R.id.nameTextTemp).setVisibility(View.GONE);
                                    }
                                }catch (Exception e){
                                    getActivity().findViewById(R.id.nameLayout).setVisibility(View.GONE);
                                }
                                try {
                                    TextView nationText = getActivity().findViewById(R.id.nationalityText);
                                    nationText.setText(jsonObject.getString("nationality"));
                                    nationality = jsonObject.getString("nationality");

                                    if(!jsonObject.getString("nationality").equals("")){
                                        getActivity().findViewById(R.id.nationalityLayout).setVisibility(View.VISIBLE);
                                    }else{
                                        getActivity().findViewById(R.id.nationalityTextTemp).setVisibility(View.GONE);
                                    }
                                }catch(Exception e){
                                    getActivity().findViewById(R.id.nationalityText).setVisibility(View.GONE);
                                }
                                try {
                                    TextView birthText = getActivity().findViewById(R.id.birthdayText);
                                    birthText.setText(jsonObject.getString("birthday"));
                                    birthday = jsonObject.getString("birthday");

                                    if(!jsonObject.getString("birthday").equals("")){
                                        getActivity().findViewById(R.id.birthdayLayout).setVisibility(View.VISIBLE);
                                    }else{
                                        getActivity().findViewById(R.id.birthdayTextTemp).setVisibility(View.GONE);
                                    }
                                }catch(Exception e){
                                    getActivity().findViewById(R.id.birthdayLayout).setVisibility(View.GONE);
                                }
                                try {
                                    TextView deathText = getActivity().findViewById(R.id.deathdayText);
                                    deathText.setText(jsonObject.getString("deathday"));

                                    if(!jsonObject.getString("deathday").equals("")){
                                        getActivity().findViewById(R.id.deathdayLayout).setVisibility(View.VISIBLE);
                                    }else{
                                        getActivity().findViewById(R.id.deathdayTextTemp).setVisibility(View.GONE);
                                    }
                                }catch(Exception e){
                                    getActivity().findViewById(R.id.deathdayLayout).setVisibility(View.GONE);
                                }
                                try {
                                    TextView bioText = getActivity().findViewById(R.id.biographyText);
                                    bioText.setText(jsonObject.getString("biography"));

                                    if(!jsonObject.getString("biography").equals("")){
                                        getActivity().findViewById(R.id.biographyLayout).setVisibility(View.VISIBLE);
                                    }else{
                                        getActivity().findViewById(R.id.biographyTextTemp).setVisibility(View.GONE);
                                    }
                                }catch(Exception e){
                                    getActivity().findViewById(R.id.biographyLayout).setVisibility(View.GONE);
                                }
//                                Log.i("SearhResultActivity", jsonObject.getString("name"));
                            }
                            getActivity().findViewById(R.id.artistProgressBar).setVisibility(View.GONE);
                            getActivity().findViewById(R.id.artistProgressBarLabel).setVisibility(View.GONE);
                            getActivity().findViewById(R.id.artistProgressBarLabelSpacer).setVisibility(View.GONE);
                        }catch(JSONException e){
                            Toast.makeText(getActivity(), "Failed to parse JSON string.", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("SearhResultActivity", error.getMessage());
                Toast.makeText(getActivity(), "Cannot make internet request.", Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artist_info, container, false);
    }
}