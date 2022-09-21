package com.example.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    private String[] localDataSet;
    private String[] imgSrc;
    private String[] ids;
    private Context context;
    private String keyword;

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
                    Log.d("SearchResultActivity", "Element " + getAdapterPosition() + " clicked.");
                    Intent myIntent = new Intent(context, ArtistActivity.class);
                    myIntent.putExtra("name",localDataSet[getAdapterPosition()]);
                    myIntent.putExtra("id",ids[getAdapterPosition()]);
                    myIntent.putExtra("keyword", keyword);
                    context.startActivity(myIntent);
                }
            });

            textView = (TextView) view.findViewById(R.id.searchResultItemArtistName);
            imageView = view.findViewById(R.id.searchResultItemImage);
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
    public SearchResultAdapter(String[] dataSet, String[] imgSrc, String[] ids, Context context, String keyword) {

        this.localDataSet = dataSet;
        this.imgSrc = imgSrc;
        this.ids = ids;
        this.context = context;
        this.keyword = keyword;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.searchresult_recyclerview_item, viewGroup, false);

        return new ViewHolder(view);
    }

    public static class ImageTransformation{
        static Transformation getTransformation(final ImageView imageView){
            return new Transformation() {
                @Override
                public Bitmap transform(Bitmap source) {
                    int targetWidth = imageView.getWidth();
                    double aspectRatio = (double)source.getHeight()*1.6 / (double)source.getWidth();
                    int targetHeight = (int)(targetWidth*aspectRatio);
                    Log.d("SearchResultAdapter", targetWidth+" * "+targetHeight);
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
        viewHolder.getTextView().setText(localDataSet[position].replace(' ', '\n'));
        Picasso.with(this.context).load(imgSrc[position]).transform(ImageTransformation.getTransformation(viewHolder.getImageView())).into(viewHolder.getImageView());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
//            Log.d("SearchResultActivity", ""+localDataSet.length);
        return localDataSet.length;
    }
}