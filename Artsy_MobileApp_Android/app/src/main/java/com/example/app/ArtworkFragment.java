package com.example.app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Use the {@link ArtworkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArtworkFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String name;
    private String id;

    public ArtworkFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ArtworkFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ArtworkFragment newInstance(String param1, String param2) {
        ArtworkFragment fragment = new ArtworkFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_PARAM1);
            id = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_artwork, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.artwork_recyclerview);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="https://homework9-113322.wl.r.appspot.com/artworks/" + id;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {

                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() > 0){
                                view.findViewById(R.id.noArtwork).setVisibility(View.INVISIBLE);
                            }else{
                                view.findViewById(R.id.noArtwork).setVisibility(View.VISIBLE);
                            }
                            String[] artistNames = new String[jsonArray.length()];
                            String[] imgSrc = new String[jsonArray.length()];
                            String[] ids = new String[jsonArray.length()];
                            for(int i = 0;i < jsonArray.length();i++){
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                artistNames[i] = jsonObject.getString("name");
                                imgSrc[i] = jsonObject.getString("img");
                                ids[i] = jsonObject.getString("id");
//                                Log.i("SearhResultActivity", jsonObject.getString("name"));
                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerView.setAdapter(new ArtworkAdapter(artistNames, imgSrc, ids, getActivity()));
                            view.findViewById(R.id.artworkProgressBar).setVisibility(View.GONE);
                            view.findViewById(R.id.artworkProgressBarLabel).setVisibility(View.GONE);
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

        return view;
    }
}