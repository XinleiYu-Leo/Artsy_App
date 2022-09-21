package com.example.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class
FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private String[] fids;
    private String[] fnames;
    private String[] fbirths;
    private String[] fnations;
    private Context context;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView nameTextView;
        public final TextView birthTextView;
        public final TextView nationTextView;
        public final ImageButton button;

        public ViewHolder(View view) {
            super(view);

            nameTextView = (TextView) view.findViewById(R.id.favName);
            birthTextView = (TextView) view.findViewById(R.id.favBirthday);
            nationTextView = (TextView) view.findViewById(R.id.favNation);
            button = view.findViewById(R.id.gotofavoriteButton);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("MainActivity", "Element " + getAdapterPosition() + " clicked.");
                    Intent myIntent = new Intent(context, ArtistActivity.class);
                    myIntent.putExtra("name",fnames[getAdapterPosition()]);
                    myIntent.putExtra("id",fids[getAdapterPosition()]);
                    context.startActivity(myIntent);
                }
            });
        }
    }

    public FavoritesAdapter(String[] fids, String[] fnames, String[] fbirths, String[] fnations, Context context) {

        this.fids = fids;
        this.fnames = fnames;
        this.fbirths = fbirths;
        this.fnations = fnations;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.favorite_recyclerview_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
//        Log.d("SearchResultActivity", localDataSet[position]);
        viewHolder.nameTextView.setText(fnames[position]);
        viewHolder.birthTextView.setText(fbirths[position]);
        viewHolder.nationTextView.setText(fnations[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
//            Log.d("SearchResultActivity", ""+localDataSet.length);
        return fids.length;
    }
}