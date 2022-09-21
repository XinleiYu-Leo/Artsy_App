package com.example.app;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ArtworkAdapter extends RecyclerView.Adapter<ArtworkAdapter.ViewHolder> {

    private String[] localDataSet;
    private String[] imgSrc;
    private String[] ids;
    private Context context;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View.
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog);

                    RequestQueue queue = Volley.newRequestQueue(context);
                    String url ="https://homework9-113322.wl.r.appspot.com/genes/" + ids[getAdapterPosition()];

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // Display the first 500 characters of the response string.
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        if (jsonArray.length() > 0){
                                            dialog.findViewById(R.id.noCategory).setVisibility(View.GONE);
                                            JSONObject jsonObject = (JSONObject) jsonArray.get(0);
                                            TextView desc = dialog.findViewById(R.id.dialogDesc);
                                            desc.setText(jsonObject.getString("description"));
                                            TextView name = dialog.findViewById(R.id.categoryName);
                                            name.setText(jsonObject.getString("name"));
                                            ImageView img = dialog.findViewById(R.id.categoryImage);
                                            Picasso.with(context).load(jsonObject.getString("img")).transform(ImageTransformation.getTransformation(img)).into(img);
//                                            imgSrc[i] = jsonObject.getString("img");
//                                            ids[i] = jsonObject.getString("id");
                                        }else{
                                            dialog.findViewById(R.id.dialogLayout1).setVisibility(View.GONE);
                                            dialog.findViewById(R.id.dialogLayout2).setVisibility(View.GONE);
                                        }
                                    }catch(JSONException e){
                                        Toast.makeText(context, "Failed to parse JSON string.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("SearhResultActivity", error.getMessage());
                            Toast.makeText(context, "Cannot make internet request.", Toast.LENGTH_LONG).show();
                        }
                    });

                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);

                    dialog.show();
                }
            });

            textView = (TextView) view.findViewById(R.id.artworkItemArtistName);
            imageView = view.findViewById(R.id.artworkItemImage);
        }

        public TextView getTextView() {
            return textView;
        }
        public ImageView getImageView(){ return imageView; }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public ArtworkAdapter(String[] dataSet, String[] imgSrc, String[] ids, Context context) {

        this.localDataSet = dataSet;
        this.imgSrc = imgSrc;
        this.ids = ids;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.artwork_recyclerview_item, viewGroup, false);

        return new ViewHolder(view);
    }

    public static class ImageTransformation{
        static Transformation getTransformation(final ImageView imageView){
            return new Transformation() {
                @Override
                public Bitmap transform(Bitmap source) {
                    int targetWidth = imageView.getWidth();
                    double aspectRatio = (double)source.getHeight() / (double)source.getWidth();
                    int targetHeight = (int)(targetWidth*aspectRatio);
                    Log.d("ArtworkAdapter", targetWidth+" * "+targetHeight);
                    if(targetWidth == 0){
                        return source;
                    }
                    Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                    if(result != source){
                        source.recycle();
                    }
                    return result;
                }

                @Override
                public String key() {
                    return "";
                }
            };
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
//        Log.d("SearchResultActivity", localDataSet[position]);
        viewHolder.getTextView().setText(localDataSet[position]);
        Picasso.with(this.context).load(imgSrc[position]).transform(ImageTransformation.getTransformation(viewHolder.getImageView())).into(viewHolder.getImageView());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
//            Log.d("SearchResultActivity", ""+localDataSet.length);
        return localDataSet.length;
    }
}