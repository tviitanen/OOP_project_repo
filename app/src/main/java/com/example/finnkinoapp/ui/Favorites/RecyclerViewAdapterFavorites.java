package com.example.finnkinoapp.ui.Favorites;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.finnkinoapp.R;
import com.example.finnkinoapp.ui.Movie.Movie;

import java.util.ArrayList;

public class RecyclerViewAdapterFavorites extends RecyclerView.Adapter<RecyclerViewAdapterFavorites.ViewHolder> {

    private ArrayList<Favorites> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public RecyclerViewAdapterFavorites(Context context, ArrayList<Favorites> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.text_row_item, parent, false);
        /* mInflater.inflate(R.layout.text_row_item, parent, false); */
        /* R.layout.activity_main, parent, false */
        return new ViewHolder(view);
    }


    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // creating a layout specific text
        Favorites fav = mData.get(position);
        String show = fav.getUser() + "\n"
                + "1. " + fav.getFavorite1() + "\n"
                + "2. " + fav.getFavorite2() + "\n"
                + "3. " + fav.getFavorite3() + "\n"
                + "4. " + fav.getFavorite4() + "\n"
                + "5. " + fav.getFavorite5() + "\n"
                + "-------------------------\n";

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
