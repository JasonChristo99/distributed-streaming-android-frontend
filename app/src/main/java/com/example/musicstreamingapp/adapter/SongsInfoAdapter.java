package com.example.musicstreamingapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicstreamingapp.R;

import java.util.ArrayList;

import shared.SongInfo;

public class SongsInfoAdapter extends RecyclerView.Adapter<SongsInfoAdapter.SongsInfoHolder> {
    private ArrayList<SongInfo> songInfos;
    private SongsInfoInteraction listener;

    public SongsInfoAdapter(ArrayList<SongInfo> songInfos) {
        this.songInfos = songInfos;
    }

    public void setListener(SongsInfoInteraction listener) {
        this.listener = listener;
    }

    @Override
    public SongsInfoAdapter.SongsInfoHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.song_info_item, parent, false);
        return new SongsInfoHolder(v);
    }

    @Override
    public void onBindViewHolder(SongsInfoHolder holder, int position) {
        final SongInfo currentSong = songInfos.get(position);

        holder.artistTextView.setText(currentSong.getArtistName());
        if (!currentSong.getSongTitle().isEmpty())
            holder.titleTextView.setText(currentSong.getSongTitle());
        else
            holder.titleTextView.setText("Untitled");

        holder.playIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.playSong(position);
            }
        });
        holder.saveIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.saveSong(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songInfos.size();
    }

    public class SongsInfoHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout root;
        public TextView titleTextView, artistTextView;
        public ImageView playIv, saveIv;

        public SongsInfoHolder(View itemView) {
            super(itemView);

            root = itemView.findViewById(R.id.root);
            titleTextView = itemView.findViewById(R.id.songInfoTitle);
            artistTextView = itemView.findViewById(R.id.songInfoArtist);
            playIv = itemView.findViewById(R.id.songInfoPlay);
            saveIv = itemView.findViewById(R.id.songInfoSave);

        }
    }

    public interface SongsInfoInteraction {
        void playSong(int id);
        void saveSong(int id);
    }
}
