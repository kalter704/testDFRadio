package com.example.vasiliy.testdfradio.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vasiliy.testdfradio.Activityes.PlayActivity;
import com.example.vasiliy.testdfradio.Classes.RadioState;
import com.example.vasiliy.testdfradio.DataClasses.RadioChannels;
import com.example.vasiliy.testdfradio.Interfaces.OnRadioListener;
import com.example.vasiliy.testdfradio.R;


import static android.widget.Toast.LENGTH_SHORT;


public class AllRadioFragment extends Fragment implements OnRadioListener {

    private ContentAdapter adapter;

    public Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RadioState.addRadioListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
        adapter = new ContentAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RadioState.removeRadioListener(this);
    }

    @Override
    public void onRadioStarted() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //TODO Do UI works here.
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onRadioPaused() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //TODO Do UI works here.
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onRadioStopped() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //TODO Do UI works here.
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onRadioLoading() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //TODO Do UI works here.
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onRadioMetadata(String s, String s2) {

    }

    @Override
    public void onRadioError() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //TODO Do UI works here.
                adapter.notifyDataSetChanged();
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mNameRadio;
        public TextView mLocation;
        public ImageView mImgArrow;
        public ImageView mImgEqualizer;

        private RadioChannels radioChannels = RadioChannels.getInstance();

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_radio_list, parent, false));
            mNameRadio = (TextView) itemView.findViewById(R.id.tvRadioName);
            mLocation = (TextView) itemView.findViewById(R.id.tvLocation);
            mImgArrow = (ImageView) itemView.findViewById(R.id.ivArrow);
            mImgEqualizer = (ImageView) itemView.findViewById(R.id.ivEqualizer);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, PlayActivity.class);
                    intent.putExtra(PlayActivity.EXTRA_POSITION, radioChannels.mIds.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }

    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {

        private static int LENGTH = 0;
        RadioChannels radioChannels;
        Context context;

        public ContentAdapter(Context context) {
            radioChannels = RadioChannels.getInstance();
            LENGTH = radioChannels.mIds.size();
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mNameRadio.setText(radioChannels.mRadioNames[position]);
            holder.mLocation.setText(radioChannels.mLocations[position]);
            AnimationDrawable animation = null;
            animation = (AnimationDrawable) holder.mImgEqualizer.getBackground();
            if ((radioChannels.mIds.get(position) == radioChannels.mPlayRadioWithId)) {
                if (RadioState.isPlaying()) {
                    holder.mImgArrow.setVisibility(View.INVISIBLE);
                    holder.mImgEqualizer.setVisibility(View.VISIBLE);
                    if (animation != null) {
                        animation.stop();
                        animation.start();
                    }
                } else {
                    holder.mImgArrow.setVisibility(View.VISIBLE);
                    holder.mImgEqualizer.setVisibility(View.INVISIBLE);
                    if (animation != null) {
                        animation.stop();
                    }
                }
            } else {
                holder.mImgArrow.setVisibility(View.VISIBLE);
                holder.mImgEqualizer.setVisibility(View.INVISIBLE);
                if (animation != null) {
                    animation.stop();
                }
            }
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }

}
