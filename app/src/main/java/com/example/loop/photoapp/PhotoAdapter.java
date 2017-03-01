package com.example.loop.photoapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by loop on 25/02/17.
 */

public class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ArrayList<Photo> list = new ArrayList<>();
    private Context context = null;
    private PhotoListener photoListener;

    public PhotoAdapter(Context context, PhotoListener photoListener) {
        this.context = context;
        this.photoListener = photoListener;
    }

    interface PhotoListener {
        void onPhotoClicked(Photo photo);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotoHolder(LayoutInflater.from(context).inflate(R.layout.photo_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PhotoHolder photoHolder = (PhotoHolder) holder;
        final Photo photo = list.get(position);
        photoHolder.bind(photo);
        photoHolder.onClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoListener.onPhotoClicked(photo);
            }
        });
    }

    public void addPhoto(Photo photo) {
        list.add(0, photo);
        notifyItemInserted(0);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class PhotoHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public PhotoHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.bitmap);
            textView = (TextView) itemView.findViewById(R.id.desc);
        }

        public void bind(Photo photo) {
            Picasso.with(imageView.getContext()).load(photo.getUri()).fit().centerInside().into(imageView);
            textView.setText(photo.getDescription());
        }

        public void onClick(View.OnClickListener listener) {
            itemView.setOnClickListener(listener);
        }
    }
}
