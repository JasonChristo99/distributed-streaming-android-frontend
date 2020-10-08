package com.example.musicstreamingapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musicstreamingapp.R;
import com.example.musicstreamingapp.model.Song;

import java.util.ArrayList;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import shared.SongInfo;

public class SavedSongAdapter extends RecyclerView.Adapter<SavedSongAdapter.SavedSongHolder> {
    private ArrayList<SongInfo> songs;
    private SavedSongsInteraction listener;

    public SavedSongAdapter(ArrayList<SongInfo> songs) {
        this.songs = songs;
    }

    public void setListener(SavedSongsInteraction listener) {
        this.listener = listener;
    }

    @Override
    public SavedSongAdapter.SavedSongHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.saved_item, parent, false);
        return new SavedSongHolder(v);
    }

    @Override
    public void onBindViewHolder(SavedSongHolder holder, int position) {
        final SongInfo currentSong = songs.get(position);

        holder.artistTextView.setText(currentSong.getArtistName());
        if (!currentSong.getSongTitle().isEmpty())
            holder.titleTextView.setText(currentSong.getSongTitle());
        else
            holder.titleTextView.setText("Untitled");

//        holder.deleteIv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.deleteSong(currentSong.getId());
//            }
//        });

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.playSong(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class SavedSongHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout root;
        public TextView titleTextView;
        public TextView artistTextView;
        public ImageView deleteIv;

        public SavedSongHolder(View itemView) {
            super(itemView);

            root = itemView.findViewById(R.id.root);
            titleTextView = itemView.findViewById(R.id.song_title);
            artistTextView = itemView.findViewById(R.id.song_artist);
            deleteIv = itemView.findViewById(R.id.delete);
        }
    }

    public interface SavedSongsInteraction {
        void deleteSong(int id);

        void playSong(int position);
    }
}
