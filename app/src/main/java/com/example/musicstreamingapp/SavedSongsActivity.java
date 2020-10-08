package com.example.musicstreamingapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicstreamingapp.adapter.SavedSongAdapter;
import com.example.musicstreamingapp.db.DB;

import java.util.ArrayList;

public class SavedSongsActivity extends AppCompatActivity implements SavedSongAdapter.SavedSongsInteraction {
    RecyclerView savedSongsRView;

    SavedSongAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_songs);

        savedSongsRView = findViewById(R.id.list);

        initListView();
    }

    private void initListView() {
        // todo get all songs from database
        adapter = new SavedSongAdapter(new ArrayList<>(DB.getSaved()));
        adapter.setListener(this);
        savedSongsRView.setAdapter(adapter);
    }

    @Override
    public void deleteSong(int id) {
        // todo delete from database song with passed id
    }

    @Override
    public void playSong(int position) {
        Intent intent = new Intent(SavedSongsActivity.this, MusicPlayerActivity.class);
        intent.putExtra("song_id", position);
        intent.putExtra("mode", "saved");
        startActivity(intent);
    }

//    @Override
//    public void playSong(int id, int position) {
//        intent.putExtra("songid", id);
//        intent.putExtra("mode", "saved");
//        intent.putExtra("position", position);
//    }
}
