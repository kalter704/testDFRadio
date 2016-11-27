package com.example.vasiliy.testdfradio.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vasiliy.testdfradio.Activityes.PlayActivity;
import com.example.vasiliy.testdfradio.DataClasses.RadioChannels;
import com.example.vasiliy.testdfradio.R;


public class AllRadioFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mNameDadio;
        public TextView mLocation;
        public ImageView mImgArrow;
        public ImageView mImgEqualizer;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_radio_list, parent, false));
            mNameDadio = (TextView) itemView.findViewById(R.id.tvRadioName);
            mLocation = (TextView) itemView.findViewById(R.id.tvLocation);
            mImgArrow = (ImageView) itemView.findViewById(R.id.ivArrow);
            mImgEqualizer = (ImageView) itemView.findViewById(R.id.ivEqualizer);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, PlayActivity.class);
                    intent.putExtra(PlayActivity.EXTRA_POSITION, getAdapterPosition());
                    context.startActivity(intent);
                }
            });
        }
    }

    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {

        private static int LENGTH = 0;
        RadioChannels radioChannels;

        public ContentAdapter() {
            radioChannels = RadioChannels.getInstance();
            LENGTH = radioChannels.mIds.length;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mNameDadio.setText(radioChannels.mRadioNames[position]);
            holder.mLocation.setText(radioChannels.mLocations[position]);
            /*
            AnimationDrawable animation = null;
            animation = (AnimationDrawable) holder.mImg.getBackground();
            if(animation != null) {
                animation.start();
            }
            */
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }

}
