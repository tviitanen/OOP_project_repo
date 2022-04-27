package com.example.finnkinoapp.ui.Movie;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.finnkinoapp.R;


import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<Movie> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;

    // data is passed into the constructor
    public RecyclerViewAdapter(Context context, ArrayList<Movie> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.text_row_item, parent, false);
        /* mInflater.inflate(R.layout.text_row_item, parent, false); */
        /* R.layout.activity_main, parent, false */
        mContext = this.mInflater.getContext();
        return new ViewHolder(view);
    }


    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // muodostetaan näytöskohtainen teksti
        Movie movie = mData.get(position);
        String show = movie.getTitle() + "\n"
                + movie.getShowStart() + "\n";

        holder.myTextView.setText(show);

    }

    // total number of rows
    //@Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.textView1 );
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            Movie movie = mData.get(getAdapterPosition());

            // When clicking an item in Recyclerview, start new activity.
            System.out.println("Clicked: " + movie.getTitle());
            Intent intent = new Intent(mContext, IMDBActivity.class);
            intent.putExtra("movie", movie);
            mContext.startActivity(intent);

        }
    }
    /*
    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    */
    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
